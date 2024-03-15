package com.cleanroommc.fugue.transformer.tickcentral;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class InitializerTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {

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
                                    toInsert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/cleanroommc/fugue/helper/HookHelper", "isInterface", "(I)Z"));
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
            ClassWriter classWriter = new ClassWriter(0);

            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        return bytes;
    }
}
