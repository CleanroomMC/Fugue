package com.cleanroommc.fugue.transformer.invtweaks;

import org.objectweb.asm.ClassReader;

import org.objectweb.asm.ClassWriter;

import org.objectweb.asm.tree.*;

import top.outlands.foundation.IExplicitTransformer;

public class InvTweaksHandlerShortcutsTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {

        ClassNode classNode = new ClassNode();

        ClassReader classReader = new ClassReader(bytes);

        classReader.accept(classNode, 0);

        if (classNode.methods != null) {

            out:
            for (MethodNode methodNode : classNode.methods) {

                if (methodNode.name.equals("handleShortcut")) {

                    InsnList instructions = methodNode.instructions;

                    if (instructions != null) {

                        for (AbstractInsnNode insnNode : instructions) {

                            if (insnNode instanceof MethodInsnNode methodInsnNode) {

                                switch (methodInsnNode.name) {
                                    case "destroy", "create", "setCursorPosition":
                                        methodInsnNode.owner = "com/cleanroommc/fugue/helper/Mouse";
                                }
                            }
                        }
                    }
                }
            }
        }

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        classNode.accept(classWriter);

        return classWriter.toByteArray();
    }
}