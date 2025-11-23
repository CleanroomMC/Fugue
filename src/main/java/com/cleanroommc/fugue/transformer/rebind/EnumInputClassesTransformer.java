package com.cleanroommc.fugue.transformer.rebind;

import com.cleanroommc.fugue.common.Fugue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import top.outlands.foundation.IExplicitTransformer;

public class EnumInputClassesTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        out:
        if (classNode.methods != null) {
            for (MethodNode methodNode : classNode.methods) {
                if (methodNode.name.equals("patchMCMinecraft")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null) {
                        for (AbstractInsnNode insnNode : instructions) {
                            if (insnNode instanceof MethodInsnNode methodInsnNode
                                    && methodInsnNode.name.equals("getNext")
                                    && insnNode.getNext() instanceof MethodInsnNode methodInsnNode1
                                    && methodInsnNode1.name.equals("getNext")) {

                                Fugue.LOGGER.info("REBIND FOUND");
                                instructions.insert(
                                        insnNode,
                                        new MethodInsnNode(
                                                Opcodes.INVOKEVIRTUAL,
                                                "org/objectweb/asm/tree/AbstractInsnNode",
                                                "getNext",
                                                "()Lorg/objectweb/asm/tree/AbstractInsnNode;",
                                                false));
                                instructions.insert(
                                        insnNode,
                                        new MethodInsnNode(
                                                Opcodes.INVOKEVIRTUAL,
                                                "org/objectweb/asm/tree/AbstractInsnNode",
                                                "getNext",
                                                "()Lorg/objectweb/asm/tree/AbstractInsnNode;",
                                                false));
                                break out;
                            }
                        }
                    }
                }
            }
        }
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
