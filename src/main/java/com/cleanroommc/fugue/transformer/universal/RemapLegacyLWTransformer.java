package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import top.outlands.foundation.IExplicitTransformer;
import top.outlands.foundation.boot.ActualClassLoader;

public class RemapLegacyLWTransformer implements IExplicitTransformer {

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

        public void visit(
                final int version,
                final int access,
                final String name,
                final String signature,
                final String superName,
                final String[] interfaces) {
            if (cv != null) {
                cv.visit(version, access, name, signature, superName, interfaces);
            }
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
        public void visitMethodInsn(
                final int opcode,
                final String owner,
                final String name,
                final String descriptor,
                final boolean isInterface) {
            if (mv != null) {
                if (owner.equals("net/minecraft/launchwrapper/LaunchClassLoader")) {
                    mv.visitMethodInsn(opcode, "com/cleanroommc/hackery/Reflection", name, descriptor, isInterface);
                } else if (owner.equals("java/lang/Class") && (name.equals("getDeclaredField") || name.equals("getField"))) {
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/cleanroommc/fugue/helper/HookHelper", "getField", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;", false);
                } else {
                    mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            }
        }

        @Override
        public void visitLdcInsn(final Object value) {
            if (mv != null) {
                if (value instanceof Type type && type.getClassName().equals("net.minecraft.launchwrapper.LaunchClassLoader")) {
                    mv.visitLdcInsn(Type.getType(ActualClassLoader.class));
                } else {
                    mv.visitLdcInsn(value);
                }
            }
        }
    }

}
