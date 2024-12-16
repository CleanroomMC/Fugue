package com.cleanroommc.fugue.transformer.essential;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.util.ListIterator;

//Target: [
//      gg.essential.asm.compat.betterfps.BetterFpsTransformerWrapper
//      gg.essential.asm.compat.PhosphorTransformer
// ]
public class EssentialTransformerClearTransformer implements IExplicitTransformer, Opcodes{

    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (var method : classNode.methods) {
            if ("initialize".equals(method.name) && "()V".equals(method.desc)) {
                EssentialSetupTweakerTransformer.clearMethod(method);
                method.visitInsn(RETURN);
            } else if ("transform".equals(method.name) && "(Ljava/lang/String;Ljava/lang/String;[B)[B".equals(method.desc)) {
                EssentialSetupTweakerTransformer.clearMethod(method);
                // public byte[] transform(String name, String transformedName, byte[] basicClass) { return basicClass; }
                method.visitVarInsn(ALOAD, 3);
                method.visitInsn(ARETURN);
            }
            
        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    @Override
    public int getPriority() {
        return 0;
    }
}