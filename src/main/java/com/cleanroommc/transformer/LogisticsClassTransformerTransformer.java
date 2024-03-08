package com.cleanroommc.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import top.outlands.foundation.IExplicitTransformer;
import top.outlands.foundation.boot.ActualClassLoader;

import java.util.concurrent.atomic.AtomicBoolean;

public class LogisticsClassTransformerTransformer implements IExplicitTransformer {
    private final Class<?> newTarget;
    public LogisticsClassTransformerTransformer(Class<?> newTarget) {
        this.newTarget = newTarget;
    }
    @Override
    public byte[] transform(String s, byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        AtomicBoolean modified = new AtomicBoolean(false);
        classNode.methods.stream().filter(methodNode -> methodNode.name.equals("<init>")).forEach(methodNode -> methodNode.instructions.iterator().forEachRemaining(abstractInsnNode -> {
            if (abstractInsnNode instanceof LdcInsnNode ldcInsnNode) {
                if (ldcInsnNode.cst instanceof Type && ((Type) ldcInsnNode.cst).getDescriptor().equals("Lnet/minecraft/launchwrapper/LaunchClassLoader;")) {
                    ldcInsnNode.cst = Type.getType(newTarget);
                    modified.set(true);
                }
            }
        }));
        if (modified.get())
        {
            ClassWriter classWriter = new ClassWriter(0);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        return bytes;
    }
}
