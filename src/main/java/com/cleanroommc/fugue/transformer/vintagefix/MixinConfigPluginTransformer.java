package com.cleanroommc.fugue.transformer.vintagefix;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import top.outlands.foundation.IExplicitTransformer;


public class MixinConfigPluginTransformer implements IExplicitTransformer {

    private static final String COLLECT_OWNER = "java/util/stream/Stream";
    private static final String COLLECT_NAME = "collect";
    private static final String COLLECT_DESC = "(Ljava/util/stream/Collector;)Ljava/lang/Object;";

    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(0);
        reader.accept(new CV(writer), 0);
        return writer.toByteArray();
    }

    private static class CV extends ClassVisitor {
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
            MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
            if (mv != null && name.equals("getMixins")) {
                mv = new MV(mv);
            }
            return mv;
        }
    }

    private static class MV extends MethodVisitor {
        public MV(MethodVisitor mv) {
            super(Opcodes.ASM9, mv);
        }

        @Override
        public void visitMethodInsn(
                final int opcode,
                final String owner,
                final String name,
                final String descriptor,
                final boolean isInterface) {
            if (opcode == Opcodes.INVOKEINTERFACE
                    && owner.equals(COLLECT_OWNER)
                    && name.equals(COLLECT_NAME)
                    && descriptor.equals(COLLECT_DESC)) {
                mv.visitMethodInsn(
                        Opcodes.INVOKESTATIC,
                        "com/cleanroommc/fugue/helper/HookHelper",
                        name,
                        "(Ljava/util/stream/Stream;" + descriptor.substring(1),
                        false);
            } else {
                mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }
        }
    }
}
