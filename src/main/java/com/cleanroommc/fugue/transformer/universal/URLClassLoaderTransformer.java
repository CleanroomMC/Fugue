package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class URLClassLoaderTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes)
    {

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                InsnList instructions = methodNode.instructions;
                if (instructions != null)
                {
                    for (AbstractInsnNode insnNode : instructions)
                    {
                        if (insnNode.getOpcode() == Opcodes.CHECKCAST && insnNode instanceof TypeInsnNode typeInsnNode)
                        {
                            if ("java/net/URLClassLoader".equals(typeInsnNode.desc))
                            {
                                AbstractInsnNode next = instructions.get(instructions.indexOf(typeInsnNode) + 1);
                                if (next instanceof MethodInsnNode nextInsnNode && next.getOpcode() == Opcodes.INVOKEVIRTUAL)
                                {
                                    if ("java/net/URLClassLoader".equals(nextInsnNode.owner) && "getURLs".equals(nextInsnNode.name))
                                    {
                                        instructions.insertBefore(insnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/cleanroommc/hackery/ReflectionHackery", "getURL", "(Ljava/lang/ClassLoader;)[Ljava/net/URL;"));
                                        instructions.remove(insnNode);
                                        instructions.remove(nextInsnNode);
                                    }
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
