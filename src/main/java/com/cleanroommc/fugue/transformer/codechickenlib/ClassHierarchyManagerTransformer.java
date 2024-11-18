package com.cleanroommc.fugue.transformer.codechickenlib;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class ClassHierarchyManagerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("getOrCreateCache")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode instanceof MethodInsnNode methodInsnNode)
                            {
                                if (methodInsnNode.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                                    instructions.insert(methodInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/cleanroommc/fugue/helper/HookHelper", "computeIfAbsent", "(Ljava/util/Map;Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;", false));
                                    instructions.remove(methodInsnNode);
                                }
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
