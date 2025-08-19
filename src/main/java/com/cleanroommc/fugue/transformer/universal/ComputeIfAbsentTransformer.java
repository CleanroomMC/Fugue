package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import top.outlands.foundation.IExplicitTransformer;

public class ComputeIfAbsentTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(0);
        CV cv = new CV(writer);
        reader.accept(cv, 0);
        return writer.toByteArray();
    }

    private class CV extends ClassVisitor {

        public CV(ClassVisitor cv) {
            super(Opcodes.ASM9, cv);
        }

        @Override
        public MethodVisitor visitMethod(
                final int access,
                final String name,
                final String descriptor,
                final String signature,
                final String[] exceptions) {
            MethodVisitor mv;
            mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
            if (mv != null) {
                mv = new MV(mv);
            }
            return mv;
        }
    }

    private static class MV extends MethodVisitor {
        public MV(MethodVisitor mv) {
            super(Opcodes.ASM9, mv);
        }

        public void visitMethodInsn(
                final int opcode,
                final String owner,
                final String name,
                final String descriptor,
                final boolean isInterface) {
            if (mv != null) {
                if (owner.equals("java/util/Map")
                        && name.equals("computeIfAbsent")
                        && descriptor.equals("(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;")) {
                    mv.visitMethodInsn(
                            Opcodes.INVOKESTATIC,
                            "com/cleanroommc/fugue/helper/HookHelper",
                            name,
                            "(Ljava/util/Map;Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;",
                            false);
                } else {
                    mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            }
        }
    }
}