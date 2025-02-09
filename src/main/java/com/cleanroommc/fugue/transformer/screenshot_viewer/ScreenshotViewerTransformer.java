package com.cleanroommc.fugue.transformer.screenshot_viewer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.util.ListIterator;

//Target: [
//      io.github.lgatodu47.screenshot_viewer.ScreenshotViewer$ScreenshotViewerEvents
// ]
public class ScreenshotViewerTransformer implements IExplicitTransformer, Opcodes {

    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (var method : classNode.methods) {
            /**
             * if (client.world != null 
             * && client.currentScreen == null 
             * - && Keyboard.getEventKeyState() 
             * && openScreenshotsScreenKey != null 
             * - && openScreenshotsScreenKey.getKeyCode() == Keyboard.getEventKey())
             * + openScreenshotsScreenKey.isPressed();
             * 
             * Check Not null ? Should not be null. Here is the minimal modification.
             */
            if ("onKeyInput".equals(method.name)) { 
                ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();
                AbstractInsnNode node;
                while (iterator.hasNext()) {
                    node = iterator.next();
                    if (node.getOpcode() == Opcodes.IF_ICMPNE) {
                        node.setOpcode(Opcodes.IFEQ);
                        MethodInsnNode mn = (MethodInsnNode) node.prev.prev;
                        mn.name = "func_151468_f";
                        mn.desc = "()Z";
                        method.instructions.remove(node.prev);
                    } else if (node instanceof MethodInsnNode mn && "getEventKeyState".equals(mn.name)) {
                        iterator.set(new InsnNode(Opcodes.ICONST_1));
                    }
                }

            }

        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}