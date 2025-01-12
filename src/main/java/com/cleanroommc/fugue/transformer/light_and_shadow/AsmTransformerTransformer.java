package com.cleanroommc.fugue.transformer.light_and_shadow;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import top.outlands.foundation.IExplicitTransformer;

/**
 * Targets :
 *  kpan.light_and_shadow.asm.core.AsmTransformer
 */
public class AsmTransformerTransformer implements IExplicitTransformer{

    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        /*
            @SuppressWarnings("unused")
            public static void rearrange() {}
            public int getPriority(){return -1;}
        */
        for (MethodNode methodNode : classNode.methods) {
            if ("rearrange".equals(methodNode.name)) {
                clearMethod(methodNode);
                methodNode.visitInsn(Opcodes.RETURN);
                methodNode.visitMaxs(0, 0);
            }
        }
        MethodNode methodNode = new MethodNode(Opcodes.ACC_PUBLIC, "getPriority", "()I", "getPriority()I", null);
        methodNode.visitInsn(Opcodes.ICONST_M1);
        methodNode.visitInsn(Opcodes.IRETURN);
        methodNode.visitMaxs(1, 1);
        classNode.methods.add(methodNode);

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
