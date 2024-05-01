package com.cleanroommc.fugue.transformer.nothirium;

import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class NothiriumClassTransformerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        if (Launch.classLoader.isClassExist("Config")) return bytes;
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
                        if (insnNode.getOpcode() == Opcodes.SIPUSH && insnNode instanceof IntInsnNode intInsnNode && intInsnNode.operand == Opcodes.INVOKESPECIAL)
                        {
                            if (intInsnNode.getNext().getOpcode() == Opcodes.LDC && intInsnNode.getNext() instanceof LdcInsnNode ldcInsnNode && ldcInsnNode.cst.equals("buy"))
                            {
                                intInsnNode.operand = Opcodes.INVOKEVIRTUAL;
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
