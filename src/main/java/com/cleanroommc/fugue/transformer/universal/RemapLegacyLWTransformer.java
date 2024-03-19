package com.cleanroommc.fugue.transformer.universal;

import com.cleanroommc.fugue.Fugue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import top.outlands.foundation.IExplicitTransformer;
import top.outlands.foundation.boot.ActualClassLoader;

public class RemapLegacyLWTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, 0);
        classNode.methods.forEach(methodNode -> methodNode.instructions.forEach(abstractInsnNode -> {
            if (abstractInsnNode instanceof MethodInsnNode methodInsnNode) {
                if (methodInsnNode.owner.equals("net/minecraft/launchwrapper/LaunchClassLoader")) {
                    if (methodInsnNode.name.equals("getTransformers")) {
                        methodNode.instructions.insert(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "top/outlands/foundation/TransformerDelegate", "getTransformers", "()Ljava/util/List;"));
                        methodNode.instructions.insert(abstractInsnNode, new InsnNode(Opcodes.POP));
                        methodNode.instructions.remove(abstractInsnNode);
                    } else {
                        methodNode.instructions.insert(abstractInsnNode, new MethodInsnNode(methodInsnNode.getOpcode(), "top/outlands/foundation/boot/ActualClassLoader", methodInsnNode.name, methodInsnNode.desc));
                        methodNode.instructions.remove(abstractInsnNode);
                    }
                } else if (methodInsnNode.getOpcode() == Opcodes.INVOKEVIRTUAL &&
                        methodInsnNode.owner.equals("java/lang/Class") && (methodInsnNode.name.equals("getDeclaredField") || methodInsnNode.name.equals("getField"))) {

                    methodNode.instructions.insert(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/cleanroommc/fugue/helper/HookHelper", "getField", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;"));
                    methodNode.instructions.remove(abstractInsnNode);
                }
            } else if (abstractInsnNode instanceof LdcInsnNode ldcInsnNode) {
                if (ldcInsnNode.cst instanceof Type && ((Type) ldcInsnNode.cst).getClassName().equals("net.minecraft.launchwrapper.LaunchClassLoader")) {
                    ldcInsnNode.cst = Type.getType(ActualClassLoader.class);
                }
            }
        }));
        ClassWriter writer = new ClassWriter(0);
        classNode.accept(writer);
        return writer.toByteArray();
    }

}
