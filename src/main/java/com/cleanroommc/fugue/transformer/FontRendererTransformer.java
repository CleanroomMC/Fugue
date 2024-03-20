package com.cleanroommc.fugue.transformer;

import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import top.outlands.foundation.IExplicitTransformer;

public class FontRendererTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        if (!Launch.classLoader.isClassLoaded("bre.smoothfont.asm.CorePlugin")) return bytes; // Don't transform if smooth font doesn't exist
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        // Everything below is srg name only! I don't think anyone is installing smooth font in dev
        var m1 = classNode.methods.stream().filter(methodNode -> methodNode.name.equals("func_181559_a")).findFirst().get();
        m1.instructions.forEach(n -> {
            if (n.getOpcode() == Opcodes.INVOKEVIRTUAL && n instanceof MethodInsnNode methodInsnNode) {
                if (methodInsnNode.owner.equals("java/lang/String") && methodInsnNode.name.equals("indexOf")) {
                    m1.instructions.insert(methodInsnNode, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "bre/smoothfont/FontRendererHook", "renderCharGetCharIndexHook", "(C)I", false));
                    m1.instructions.remove(methodInsnNode);
                }
            }
        });
        var m2 = classNode.methods.stream().filter(methodNode -> methodNode.name.equals("func_78255_a")).findFirst().get();
        m2.instructions.forEach(n -> {
            if (n.getOpcode() == Opcodes.INVOKEVIRTUAL && n instanceof MethodInsnNode methodInsnNode && n.getPrevious().getOpcode() != Opcodes.INVOKEVIRTUAL) {
                if (methodInsnNode.owner.equals("java/lang/String") && methodInsnNode.name.equals("indexOf")) {
                    m2.instructions.insert(methodInsnNode, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "bre/smoothfont/FontRendererHook", "renderStringAtPosGetCharIndexHook", "(C)I", false));
                    m2.instructions.remove(methodInsnNode);
                }
            }
        });

        var m3 = classNode.methods.stream().filter(methodNode -> methodNode.name.equals("func_78263_a")).findFirst().get();
        m3.instructions.forEach(n -> {
            if (n.getOpcode() == Opcodes.INVOKEVIRTUAL && n instanceof MethodInsnNode methodInsnNode) {
                if (methodInsnNode.owner.equals("java/lang/String") && methodInsnNode.name.equals("indexOf")) {
                    m3.instructions.insert(methodInsnNode, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "bre/smoothfont/FontRendererHook", "getCharWidthGetCharIndexHook", "(C)I", false));
                    m3.instructions.remove(methodInsnNode);
                }
            }
        });
        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
