package com.cleanroommc.fugue.transformer.offlineskins;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

/**
 * Targets :
 *      lain.mods.skins.init.forge.asm.ObfHelper
 */
public class ObfHelperTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        for (MethodNode methodNode : classNode.methods) {
            if ("transform".equals(methodNode.name)) {
                SetupTransformer.clearMethod(methodNode);
                methodNode.visitInsn(Opcodes.RETURN);
                methodNode.visitMaxs(2, 2);
            }
        }
        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}