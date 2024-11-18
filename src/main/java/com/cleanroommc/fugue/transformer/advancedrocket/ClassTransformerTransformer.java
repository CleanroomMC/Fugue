package com.cleanroommc.fugue.transformer.advancedrocket;

import com.cleanroommc.fugue.common.Fugue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class ClassTransformerTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("transform")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        int fromIndex = 0;
                        int toIndex = 0;
                        for (int i = 0; i < instructions.size(); i++)
                        {
                            AbstractInsnNode insnNode = instructions.get(i);
                            // Find beginning of EntityPlayer patch block
                            if (insnNode.getOpcode() == Opcodes.LDC && ((LdcInsnNode) insnNode).cst.equals("net.minecraft.entity.player.EntityPlayer"))
                            {
                                fromIndex = i;
                            }
                            // Then find start of loop
                            else if (fromIndex > 0 && insnNode instanceof FrameNode)
                            {
                                toIndex = i;
                                break;
                            }
                        }
                        int constCounts = 0;
                        AbstractInsnNode targetNode = null;
                        // Now count number of ICONST opcodes
                        for (; fromIndex < toIndex; fromIndex++)
                        {
                            AbstractInsnNode insnNode = instructions.get(fromIndex);
                            if (insnNode.getOpcode() == Opcodes.ICONST_0 || insnNode.getOpcode() == Opcodes.ICONST_1)
                            {
                                if (targetNode == null)
                                {
                                    targetNode = insnNode;
                                }
                                constCounts++;
                            }
                        }
                        Fugue.LOGGER.debug("Advanced Rocketry: Found {} ICONST invocations", constCounts);
                        // This is regular AR, patch
                        if (constCounts == 2)
                        {
                            instructions.insertBefore(targetNode, new InsnNode(Opcodes.ICONST_0));
                            instructions.remove(targetNode);
                        }
                        break;
                    }
                }
            }
        }
        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
