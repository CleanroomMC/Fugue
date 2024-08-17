package com.cleanroommc.fugue.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class EntityLivingBaseTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null)
        {
            out:
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("func_70636_d")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && insnNode instanceof MethodInsnNode methodInsnNode)
                            {
                                if (methodInsnNode.name.equals("func_184616_r"))
                                {
                                    InsnList toInject = new InsnList();
                                    toInject.add(new VarInsnNode(Opcodes.ALOAD, 0));
                                    toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "c4/colytra/asm/ASMHooks", "updateColytra", "(Lnet/minecraft/entity/EntityLivingBase;)V"));
                                    methodNode.instructions.insert(methodInsnNode, toInject);
                                    break out;
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
