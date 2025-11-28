package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import top.outlands.foundation.IExplicitTransformer;

public class ScriptEngineTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(0);
        CV cv = new CV(writer);
        reader.accept(cv, 0);
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

        @Override
        public void visitTypeInsn(final int opcode, final String type) {
            if (mv != null) {
                if (type.equals("javax/script/ScriptEngineManager") && opcode == Opcodes.NEW) {
                    mv.visitTypeInsn(opcode, "com/cleanroommc/loader/scripting/CleanroomScriptEngineManager");
                } else {
                    mv.visitTypeInsn(opcode, type);
                }
            }
        }

        @Override
        public void visitMethodInsn(
                final int opcode,
                final String owner,
                final String name,
                final String descriptor,
                final boolean isInterface) {
            if (mv != null) {
                if ("javax/script/ScriptEngineManager".equals(owner) && "<init>".equals(name)) {
                    mv.visitMethodInsn(
                            opcode,
                            "com/cleanroommc/loader/scripting/CleanroomScriptEngineManager",
                            name,
                            descriptor,
                            isInterface);
                } else {
                    mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            }
        }
    }
}
