package com.cleanroommc.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class OpenDisksUnpackTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(String s1, byte[] bytes) {
        if (bytes == null)
        {
            return null;
        }


        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean modified = false;
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("load")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && insnNode instanceof MethodInsnNode methodInsnNode)
                            {
                                if (methodInsnNode.name.equals("getPath") && methodInsnNode.owner.equals("java/net/URI"))
                                {
                                    instructions.insert(methodInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/cleanroommc/helper/HookHelper", "byGetResource", "()Ljava/lang/String;", false));
                                    instructions.insert(methodInsnNode, new InsnNode(Opcodes.POP));
                                    modified = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (modified)
        {
            ClassWriter classWriter = new ClassWriter(0);

            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        return bytes;
    }
}
