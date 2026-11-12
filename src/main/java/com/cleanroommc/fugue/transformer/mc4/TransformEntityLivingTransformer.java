package com.cleanroommc.fugue.transformer.mc4;

import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import top.outlands.foundation.IExplicitTransformer;

public class TransformEntityLivingTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        if ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) return  bytes;
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null) {
            for (MethodNode methodNode : classNode.methods) {
                if (methodNode.name.equals("transform")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null) {
                        for (AbstractInsnNode insnNode : instructions) {
                            if (insnNode.getOpcode() == Opcodes.LDC
                                && insnNode instanceof LdcInsnNode ldcInsnNode) {
                                if (ldcInsnNode.cst.equals("tasks")) {
                                    ldcInsnNode.cst = "field_70714_bg";
                                } else if(ldcInsnNode.cst.equals("addTask")) {
                                    ldcInsnNode.cst = "func_75776_a";
                                }
                            }
                        }
                    }
                }
            }
        }
        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
