package com.cleanroommc.fugue.transformer.corpse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class MessageOpenHistoryTransformer implements IExplicitTransformer {
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
                if (methodNode.name.equals("onMessage")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && insnNode instanceof MethodInsnNode methodInsnNode)
                            {
                                if (methodInsnNode.name.equals("func_147108_a"))
                                {
                                    instructions.insertBefore(methodInsnNode, new InsnNode(Opcodes.SWAP));
                                    instructions.insertBefore(methodInsnNode, new InsnNode(Opcodes.POP));
                                    instructions.insertBefore(methodInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/cleanroommc/fugue/transformer/corpse/MessageOpenHistoryTransformer", "displayGuiScreen", "(Lnet/minecraft/client/gui/GuiScreen;)V"));
                                    instructions.remove(methodInsnNode);
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

    public static void displayGuiScreen(GuiScreen guiScreen) {
        Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(guiScreen));
    }
}
