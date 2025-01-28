package com.cleanroommc.fugue.transformer.offlineskins;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

/**
 * Targets :
 *      lain.mods.skins.init.forge.asm.ASMTransformer
 */
public class OfflineskinsTransformersTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        MethodNode methodNode = new MethodNode(Opcodes.ACC_PUBLIC, "getPriority", "()I", "getPriority()I", null);
        methodNode.visitInsn(Opcodes.ICONST_M1);
        methodNode.visitInsn(Opcodes.IRETURN);
        methodNode.visitMaxs(1, 1);
        classNode.methods.add(methodNode);

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}