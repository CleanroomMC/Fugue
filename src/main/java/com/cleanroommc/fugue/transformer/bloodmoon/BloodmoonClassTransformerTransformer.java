package com.cleanroommc.fugue.transformer.bloodmoon;

import top.outlands.foundation.IExplicitTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * Bloodmoon's {@code patchEntityRendererClass} injects its lightmap hooks after the first {@code ISTORE 23} of
 * {@code EntityRenderer.updateLightmap} and reads the light index and the red/green/blue ints from the fixed slots
 * 5/21/22/23 - the slots vanilla's javac assigned. Cleanroom's recompiled {@code EntityRenderer} lays its locals out
 * differently (slot 21 holds a float there), so the class fails verification at game init
 * ({@code VerifyError: Bad local variable type}). The method body is replaced with a call to
 * {@link com.cleanroommc.fugue.helper.BloodmoonHelper#patchEntityRenderer(byte[])}, which finds the slots from the bytecode instead.
 */
public class BloodmoonClassTransformerTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        new ClassReader(bytes).accept(classNode, 0);
        for (var method : classNode.methods) {
            if ("patchEntityRendererClass".equals(method.name) && "([B)[B".equals(method.desc)) {
                method.instructions.clear();
                method.tryCatchBlocks.clear();
                if (method.localVariables != null) {
                    method.localVariables.clear();
                }
                method.instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
                method.instructions.add(new MethodInsnNode(
                    Opcodes.INVOKESTATIC, "com/cleanroommc/fugue/helper/BloodmoonHelper", "patchEntityRenderer",
                    "([B)[B", false
                ));
                method.instructions.add(new InsnNode(Opcodes.ARETURN));
                method.maxStack = 1;
                method.maxLocals = 2;
            }
        }
        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

}
