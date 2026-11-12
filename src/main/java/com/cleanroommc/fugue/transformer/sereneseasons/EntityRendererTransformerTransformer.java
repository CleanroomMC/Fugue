package com.cleanroommc.fugue.transformer.sereneseasons;

import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import top.outlands.foundation.IExplicitTransformer;

public class EntityRendererTransformerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        if (Launch.classLoader.isClassExist("Config")) return bytes;
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (var method : classNode.methods) {
            if ("transformEntityRenderer".equals(method.name)) {
                for (var insnNode : method.instructions) {
                    if (insnNode.getOpcode() == Opcodes.ISUB) {
                        if (insnNode.getPrevious().getOpcode() == Opcodes.ICONST_2) {
                            method.instructions.remove(insnNode.getPrevious());
                            method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.ICONST_4));
                        } else if (insnNode.getPrevious().getOpcode() == Opcodes.ICONST_1) {
                            method.instructions.remove(insnNode.getPrevious());
                            method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.ICONST_3));
                        }
                    } else if (insnNode instanceof MethodInsnNode methodInsnNode
                            && methodInsnNode.name.equals("remove")) {
                        method.instructions.insert(insnNode, new VarInsnNode(Opcodes.ISTORE, 13));
                        method.instructions.insert(
                                insnNode,
                                new MethodInsnNode(
                                        Opcodes.INVOKESTATIC,
                                        "com/cleanroommc/fugue/helper/HookHelper",
                                        "filteredRemove",
                                        "(Lorg/objectweb/asm/tree/InsnList;Lorg/objectweb/asm/tree/AbstractInsnNode;I)I",
                                        false));
                        method.instructions.insert(insnNode, new VarInsnNode(Opcodes.ILOAD, 13));
                        method.instructions.remove(insnNode);
                    }
                }
            }
        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
