package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class ConnectionBlockingTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        classNode.methods.forEach(m -> m.instructions.forEach(n -> {
            if (n.getOpcode() == Opcodes.INVOKEVIRTUAL && n instanceof MethodInsnNode methodInsnNode) {
                if (methodInsnNode.owner.equals("java/net/URL") && methodInsnNode.name.equals("openStream")) {
                    m.instructions.insert(methodInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/cleanroommc/fugue/helper/HookHelper", "open", "(Ljava/net/URL;)Ljava/io/InputStream;", false));
                    m.instructions.remove(methodInsnNode);
                }
            }
        }));
        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
