package com.cleanroommc.fugue.transformer.celeritas;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class CeleritasVintageMixinPluginTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null) {
            for (MethodNode methodNode : classNode.methods) {
                if (methodNode.name.equals("onLoad")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null) {
                        for (AbstractInsnNode insnNode : instructions) {
                            if (insnNode instanceof MethodInsnNode methodInsnNode) {
                                if (methodInsnNode.getOpcode() == Opcodes.INVOKESTATIC) {
                                    if (
                                            methodInsnNode.owner.equals("com/gtnewhorizons/retrofuturabootstrap/SharedConfig") &&
                                            methodInsnNode.name.equals("getRfbTransformers") &&
                                            methodInsnNode.desc.equals("()Ljava/util/List;")
                                    ) {
                                        instructions.insertBefore(methodInsnNode, new InsnNode(Opcodes.RETURN));
                                    }
                                }
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
