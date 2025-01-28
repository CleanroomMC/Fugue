package com.cleanroommc.fugue.transformer.offlineskins;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

/**
 * Targets :
 *      lain.mods.skins.init.forge.asm.Setup
 */
public class SetupTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        for (MethodNode methodNode : classNode.methods) {
            if ("call".equals(methodNode.name)) {
                SetupTransformer.clearMethod(methodNode);
                methodNode.visitInsn(Opcodes.ACONST_NULL);
                methodNode.visitInsn(Opcodes.ARETURN);
                methodNode.visitMaxs(1, 1);
            }
        }
        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    static void clearMethod(MethodNode method) {
        if (method.instructions != null) method.instructions.clear();
        if (method.tryCatchBlocks != null) method.tryCatchBlocks.clear();
        if (method.localVariables != null) method.localVariables.clear();
        if (method.visibleLocalVariableAnnotations != null) method.visibleLocalVariableAnnotations.clear();
        if (method.invisibleLocalVariableAnnotations != null) method.invisibleLocalVariableAnnotations.clear();
    }
}