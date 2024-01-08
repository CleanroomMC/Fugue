package com.cleanroommc.transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class InitializerTransformer implements IClassTransformer {
    private static boolean hit = false;
    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        
        if (bytes == null)
        {
            return null;
        }

        if (hit || !s1.equals("com.github.terminatornl.laggoggles.tickcentral.Initializer"))
        {
            return bytes;
        }
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean modified = false;
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("convertTargetInstruction")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.PUTFIELD && insnNode instanceof FieldInsnNode fieldInsnNode)
                            {
                                if (fieldInsnNode.owner.equals("org/objectweb/asm/tree/MethodInsnNode") && fieldInsnNode.name.equals("desc"))
                                {
                                    InsnList toInsert = new InsnList();
                                    toInsert.add(new VarInsnNode(Opcodes.ALOAD, 10));
                                    toInsert.add(new VarInsnNode(Opcodes.ILOAD, 6));
                                    toInsert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/cleanroommc/helper/HookHelper", "isInterface", "(I)Z"));
                                    toInsert.add(new FieldInsnNode(Opcodes.PUTFIELD, "org/objectweb/asm/tree/MethodInsnNode", "itf", "Z"));
                                    instructions.insert(fieldInsnNode, toInsert);
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
