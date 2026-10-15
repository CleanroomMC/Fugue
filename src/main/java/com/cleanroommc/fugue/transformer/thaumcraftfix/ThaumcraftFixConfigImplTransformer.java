package com.cleanroommc.fugue.transformer.thaumcraftfix;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import top.outlands.foundation.IExplicitTransformer;

public class ThaumcraftFixConfigImplTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(0);
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(version, access | Opcodes.ACC_PUBLIC, name, signature, superName, interfaces);
            }
        }, 0);
        return writer.toByteArray();
    }
}
