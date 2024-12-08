package com.cleanroommc.fugue.transformer.calculator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class GuiInfoCalculatorTransformer implements IExplicitTransformer {
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
                if (methodNode.name.equals("changeItemInfo")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode instanceof TypeInsnNode typeInsnNode)
                            {
                                if (typeInsnNode.getOpcode() == Opcodes.CHECKCAST) {
                                    AbstractInsnNode aload = typeInsnNode.getNext();
                                    instructions.remove(aload.getNext());
                                    instructions.remove(aload.getNext());
                                    instructions.remove(aload.getNext());
                                    instructions.insert(aload, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/cleanroommc/fugue/helper/HookHelper", "listToArray", "(Ljava/util/List;)[Ljava/lang/String;", false));
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
