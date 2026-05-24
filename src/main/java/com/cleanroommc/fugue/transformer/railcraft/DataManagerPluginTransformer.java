package com.cleanroommc.fugue.transformer.railcraft;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class DataManagerPluginTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        for (MethodNode methodNode : classNode.methods) {
            if ("create".equals(methodNode.name)) {
                InsnList instructions = methodNode.instructions;

                //Railcraft changed this in recent alpha builds but not in latest release
                //And last update was in 2022
                boolean found = false;
                AbstractInsnNode iconstNode = null;
                MethodInsnNode getCallerNode = null;

                for (AbstractInsnNode insn : instructions) {
                    if (insn.getOpcode() == Opcodes.ICONST_2) {
                        AbstractInsnNode next = insn.getNext();
                        if (next instanceof MethodInsnNode methodInsn) {
                            if ("sun/reflect/Reflection".equals(methodInsn.owner)
                                && "getCallerClass".equals(methodInsn.name)) {
                                //INVOKESTATIC sun/reflect/Reflection getCallerClass (I)Ljava/lang/Class;
                                iconstNode = insn;
                                getCallerNode = methodInsn;
                                found = true;
                                break;
                            }
                        }
                    }
                }

                if (found) {
                    InsnList newInsns = new InsnList();
                    newInsns.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/StackWalker$Option", "RETAIN_CLASS_REFERENCE", "Ljava/lang/StackWalker$Option;"));
                    newInsns.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/StackWalker", "getInstance", "(Ljava/lang/StackWalker$Option;)Ljava/lang/StackWalker;", false));
                    newInsns.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StackWalker", "getCallerClass", "()Ljava/lang/Class;", false));

                    instructions.insertBefore(getCallerNode, newInsns);
                    instructions.remove(iconstNode);
                    instructions.remove(getCallerNode);
                }
            }
        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
