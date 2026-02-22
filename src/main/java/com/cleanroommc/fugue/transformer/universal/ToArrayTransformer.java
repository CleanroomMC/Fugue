package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import top.outlands.foundation.IExplicitTransformer;

public class ToArrayTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean modified = false;
        if (classNode.methods != null) {
            for (MethodNode methodNode : classNode.methods) {
                InsnList instructions = methodNode.instructions;
                if (instructions != null) {
                    for (AbstractInsnNode insnNode : instructions) {
                        if (insnNode.getOpcode() == Opcodes.CHECKCAST
                                && insnNode instanceof TypeInsnNode typeInsnNode
                                && insnNode.getPrevious() instanceof MethodInsnNode methodInsnNode
                                && methodInsnNode.name.equals("toArray")
                                && methodInsnNode.desc.equals("()[Ljava/lang/Object;")
                                && methodInsnNode.owner.equals("java/util/List")
                                && methodInsnNode.getPrevious().getOpcode() != Opcodes.ANEWARRAY) {
                            String type = typeInsnNode.desc;
                            methodInsnNode.desc = "([Ljava/lang/Object;)[Ljava/lang/Object;";
                            instructions.insertBefore(methodInsnNode, new InsnNode(Opcodes.ICONST_0));
                            instructions.insertBefore(
                                    methodInsnNode,
                                    new TypeInsnNode(Opcodes.ANEWARRAY, type.substring(2, type.length() - 1)));
                            modified = true;
                        }
                    }
                }
            }
        }
        if (modified) {
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        return bytes;
    }
}
