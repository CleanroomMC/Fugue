package com.cleanroommc.fugue.helper;

import com.cleanroommc.fugue.common.Fugue;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * Bloodmoon's {@code EntityRenderer} patch, with the local slots read from the method instead of assumed.
 * <p>
 * {@code updateLightmap} ends each texel with {@code this.lightmapColors[i] = -16777216 | r << 16 | g << 8 | b}. That
 * store names all four values Bloodmoon needs, whatever slots the compiler gave them, so the hooks
 * ({@code r = manipulateRed(i, r)}, then green, then blue - the same calls, in the same order, as Bloodmoon's own
 * patch) are inserted right before it. If the store is not found the class is returned unchanged: the blood moon loses
 * its lightmap tint, but the game starts.
 */
public final class BloodmoonHelper {

    private static final String HANDLER = "lumien/bloodmoon/client/ClientBloodmoonHandler";

    private BloodmoonHelper() { }

    public static byte[] patchEntityRenderer(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        new ClassReader(bytes).accept(classNode, 0);
        boolean patched = false;
        for (MethodNode method : classNode.methods) {
            if (("func_78472_g".equals(method.name) || "updateLightmap".equals(method.name)) &&
                "(F)V".equals(method.desc)) {
                patched |= patchUpdateLightmap(method);
            }
        }
        if (!patched) {
            Fugue.LOGGER.warn(
                "Bloodmoon: the lightmap colour store was not found in EntityRenderer.updateLightmap;" +
                    " the blood moon lightmap tint is disabled"
            );
            return bytes;
        }
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    private static boolean patchUpdateLightmap(MethodNode method) {
        for (AbstractInsnNode node = method.instructions.getFirst(); node != null; node = node.getNext()) {
            if (node.getOpcode() != Opcodes.IASTORE) {
                continue;
            }
            // Walking back from the store: IOR, ILOAD b, IOR, ISHL, BIPUSH 8, ILOAD g, IOR, ISHL, BIPUSH 16, ILOAD r,
            // LDC -16777216, ILOAD i, GETFIELD lightmapColors, ALOAD 0.
            AbstractInsnNode[] seq = new AbstractInsnNode[14];
            AbstractInsnNode cursor = node;
            boolean complete = true;
            for (int k = 0; k < seq.length; k++) {
                cursor = previousReal(cursor);
                if (cursor == null) {
                    complete = false;
                    break;
                }
                seq[k] = cursor;
            }
            if (!complete ||
                seq[0].getOpcode() != Opcodes.IOR ||
                seq[1].getOpcode() != Opcodes.ILOAD ||
                seq[2].getOpcode() != Opcodes.IOR ||
                seq[3].getOpcode() != Opcodes.ISHL ||
                !isInt(seq[4], 8) ||
                seq[5].getOpcode() != Opcodes.ILOAD ||
                seq[6].getOpcode() != Opcodes.IOR ||
                seq[7].getOpcode() != Opcodes.ISHL ||
                !isInt(seq[8], 16) ||
                seq[9].getOpcode() != Opcodes.ILOAD ||
                !(seq[10] instanceof LdcInsnNode ldc && Integer.valueOf(-16777216).equals(ldc.cst)) ||
                seq[11].getOpcode() != Opcodes.ILOAD ||
                !(seq[12] instanceof FieldInsnNode field &&
                    field.getOpcode() == Opcodes.GETFIELD &&
                    "[I".equals(field.desc)) ||
                seq[13].getOpcode() != Opcodes.ALOAD) {
                continue;
            }
            int blue = ((VarInsnNode) seq[1]).var;
            int green = ((VarInsnNode) seq[5]).var;
            int red = ((VarInsnNode) seq[9]).var;
            int index = ((VarInsnNode) seq[11]).var;
            InsnList hooks = new InsnList();
            hook(hooks, "manipulateRed", index, red);
            hook(hooks, "manipulateGreen", index, green);
            hook(hooks, "manipulateBlue", index, blue);
            method.instructions.insertBefore(seq[13], hooks);
            Fugue.LOGGER.info("Bloodmoon: lightmap hooks placed on slots i={} r={} g={} b={}", index, red, green, blue);
            return true;
        }
        return false;
    }

    private static void hook(InsnList list, String name, int index, int slot) {
        list.add(new FieldInsnNode(Opcodes.GETSTATIC, HANDLER, "INSTANCE", "L" + HANDLER + ";"));
        list.add(new VarInsnNode(Opcodes.ILOAD, index));
        list.add(new VarInsnNode(Opcodes.ILOAD, slot));
        list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, HANDLER, name, "(II)I", false));
        list.add(new VarInsnNode(Opcodes.ISTORE, slot));
    }

    private static boolean isInt(AbstractInsnNode node, int value) {
        return node instanceof IntInsnNode push && push.getOpcode() == Opcodes.BIPUSH && push.operand == value;
    }

    private static AbstractInsnNode previousReal(AbstractInsnNode node) {
        AbstractInsnNode previous = node.getPrevious();
        while (previous != null && previous.getOpcode() < 0) {
            previous = previous.getPrevious();
        }
        return previous;
    }

}
