package com.cleanroommc.fugue.transformer.loliasm;

import com.cleanroommc.fugue.Fugue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;
import top.outlands.foundation.boot.ActualClassLoader;

public class LoliFMLCallHookTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        Fugue.LOGGER.info(reader.getClassName());
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, 0);
        classNode.methods.forEach(methodNode -> methodNode.instructions.forEach(abstractInsnNode -> {
            if (abstractInsnNode instanceof MethodInsnNode methodInsnNode) {
                if (methodInsnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && methodInsnNode.name.equals("invokeExact") && methodInsnNode.desc.equals("(Lnet/minecraft/launchwrapper/LaunchClassLoader;Ljava/util/Map;)V")) {
                    methodNode.instructions.insert(abstractInsnNode, new InsnNode(Opcodes.POP2));
                    methodNode.instructions.insert(abstractInsnNode, new InsnNode(Opcodes.POP));
                    methodNode.instructions.remove(abstractInsnNode);
                }
            }
        }));
        ClassWriter writer = new ClassWriter(0);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
