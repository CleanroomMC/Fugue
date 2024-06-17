package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import top.outlands.foundation.IExplicitTransformer;

public class DWheelTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, 0);
        classNode.methods.forEach(methodNode -> methodNode.instructions.forEach(abstractInsnNode -> {
            if (abstractInsnNode instanceof MethodInsnNode methodInsnNode) {
                if (methodInsnNode.owner.equals("org/lwjgl/input/Mouse")) {
                    if (methodInsnNode.name.equals("getEventDWheel") || methodInsnNode.name.equals("getDWheel")) {
                        methodInsnNode.owner = "com/cleanroommc/fugue/helper/Mouse";
                    }
                }
            }
        }));
        ClassWriter writer = new ClassWriter(0);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
