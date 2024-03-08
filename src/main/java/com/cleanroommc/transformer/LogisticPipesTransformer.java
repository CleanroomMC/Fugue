package com.cleanroommc.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class LogisticPipesTransformer implements IExplicitTransformer {
    private int match;
    public LogisticPipesTransformer(int match) {
        this.match = match;
    }
    @Override
    public byte[] transform(byte[] bytes) {
        if (match == 0)
        {
            return bytes;
        }

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("handleClass") || methodNode.name.equals("handleRenderDuctItemsClass")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.ICONST_1 && insnNode instanceof InsnNode iConstNode)
                            {
                                instructions.insert(iConstNode, new InsnNode(Opcodes.ICONST_0));
                                instructions.remove(iConstNode);
                                match--;
                                if (match == 0) {
                                    break;
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
