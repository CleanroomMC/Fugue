package com.cleanroommc.transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class EnderCoreTransformerTransformer implements IClassTransformer {
    private static boolean hit = false;
    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (bytes == null)
        {
            return null;
        }

        if (hit || !s1.equals("com.enderio.core.common.transform.EnderCoreTransformer"))
        {
            return bytes;
        }


        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean modified = false;
        AbstractInsnNode prevLine = null;
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("transform")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.INVOKESPECIAL && insnNode instanceof MethodInsnNode methodInsnNode)
                            {
                                if ("org/objectweb/asm/ClassWriter".equals(methodInsnNode.owner) && "<init>".equals(methodInsnNode.name))
                                {
                                    prevLine = instructions.get(instructions.indexOf(methodInsnNode) + 1);
                                    continue;
                                }
                            }
                            if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && insnNode instanceof MethodInsnNode methodInsnNode) 
                            {
                                if ("org/objectweb/asm/ClassWriter".equals(methodInsnNode.owner) && "visitMethod".equals(methodInsnNode.name))
                                {
                                    if (prevLine == null) return bytes;
                                    InsnList toInsert = new InsnList();
                                    toInsert.add(new VarInsnNode(Opcodes.ALOAD, 6));
                                    toInsert.add(new IntInsnNode(Opcodes.BIPUSH, 52));
                                    toInsert.add(new InsnNode(Opcodes.ICONST_1));
                                    toInsert.add(new LdcInsnNode("net/minecraft/entity/player/EntityPlayer"));
                                    toInsert.add(new InsnNode(Opcodes.ACONST_NULL));
                                    toInsert.add(new LdcInsnNode("net/minecraft/entity/EntityLivingBase"));
                                    toInsert.add(new InsnNode(Opcodes.ACONST_NULL));
                                    toInsert.add(new MethodInsnNode(
                                            Opcodes.INVOKEVIRTUAL, 
                                            "org/objectweb/asm/ClassWriter", 
                                            "visit", 
                                            "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V"));
                                    instructions.insert(prevLine, toInsert);
                                    AbstractInsnNode loadNull = instructions.get(instructions.indexOf(methodInsnNode) - 2);
                                    if (loadNull instanceof InsnNode nullNode) {
                                        instructions.insertBefore(nullNode, new LdcInsnNode("B"));
                                        instructions.remove(nullNode);
                                    } else {
                                        return bytes;
                                    }
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
            hit = true;
            ClassWriter classWriter = new ClassWriter(0);

            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        return bytes;
    }
}
