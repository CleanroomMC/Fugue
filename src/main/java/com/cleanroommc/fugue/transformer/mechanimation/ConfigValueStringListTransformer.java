package com.cleanroommc.fugue.transformer.mechanimation;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import top.outlands.foundation.IExplicitTransformer;

public class ConfigValueStringListTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null) {
            out:
            for (MethodNode methodNode : classNode.methods) {
                if (methodNode.name.equals("saveConfig")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null) {
                        for (AbstractInsnNode insnNode : instructions) {
                            if (insnNode instanceof MethodInsnNode methodInsnNode
                                && methodInsnNode.name.equals("toArray")) {
                                instructions.insert(
                                    methodInsnNode,
                                    new MethodInsnNode(
                                        Opcodes.INVOKESTATIC,
                                        "com/cleanroommc/fugue/helper/HookHelper",
                                        "listToArray",
                                        "(Ljava/util/List;)[Ljava/lang/String;",
                                        false));
                                instructions.remove(methodInsnNode);
                                break out;
                            }
                        }
                    }
                }
            }
        }
        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
