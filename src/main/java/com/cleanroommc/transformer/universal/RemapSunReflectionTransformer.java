package com.cleanroommc.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import top.outlands.foundation.IExplicitTransformer;

public class RemapSunReflectionTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, 0);
        classNode.methods.forEach(methodNode -> methodNode.instructions.forEach(abstractInsnNode -> {
            if (abstractInsnNode.getOpcode() == Opcodes.INVOKESTATIC && abstractInsnNode instanceof MethodInsnNode methodInsnNode) {
                if (methodInsnNode.owner.equals("sun/reflect/Reflection")) {
                    methodInsnNode.owner = "com/cleanroommc/hackery/Reflection";
                }
            }
        }));
        ClassWriter writer = new ClassWriter(0);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
