package com.cleanroommc.fugue.transformer.futuremc;

import com.cleanroommc.fugue.common.Fugue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodNode;
import top.outlands.foundation.IExplicitTransformer;

public class CoreTransformerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null) {
            out:
            for (MethodNode methodNode : classNode.methods) {
                if (methodNode.name.equals("patchEntityLivingBase")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null) {
                        for (AbstractInsnNode insnNode : instructions) {
                            if (insnNode.getOpcode() == Opcodes.BIPUSH
                                    && insnNode instanceof IntInsnNode intInsnNode
                                    && insnNode.getPrevious().getOpcode() == Opcodes.IFEQ) {
                                intInsnNode.operand = 9;
                                break out;
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
