package com.cleanroommc.fugue.transformer.screenshot_viewer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

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
            // Overwrite -- Actual Redirect
            if ("onKeyInput".equals(method.name)) {
                for (AbstractInsnNode node : method.instructions) {
                    if (node instanceof MethodInsnNode mi) {
                        if ("getEventKeyState".equals(mi.name)) {
                            mi.owner = "net/minecraft/client/settings/KeyBinding";
                            mi.name = "func_151468_f";
                            method.instructions.insertBefore(mi, new VarInsnNode(Opcodes.ALOAD, 3));
                        }
                    }
                }

            }

        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    @Override
    public int getPriority() {
        return 0;
    }
}