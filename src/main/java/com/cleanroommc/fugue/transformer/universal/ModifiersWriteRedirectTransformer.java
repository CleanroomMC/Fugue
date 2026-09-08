package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import top.outlands.foundation.IExplicitTransformer;

import java.util.Map;

public class ModifiersWriteRedirectTransformer implements IExplicitTransformer {

    private static final String FIELD_OWNER = "java/lang/reflect/Field";
    private static final String HOOK_OWNER = "com/cleanroommc/fugue/helper/HookHelper";

    private final Map<String, String> targets;

    public ModifiersWriteRedirectTransformer(Map<String, String> targets) {
        this.targets = targets;
    }

    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(0);
        CV cv = new CV(writer, targets);
        reader.accept(cv, 0);
        return writer.toByteArray();
    }

    private static class CV extends ClassVisitor {

        private final Map<String, String> targets;
        private String method;

        public CV(ClassVisitor cv, Map<String, String> targets) {
            super(Opcodes.ASM9, cv);
            this.targets = targets;
        }

        @Override
        public void visit(
                int version, int access, String name, String signature, String superName, String[] interfaces) {
            method = targets.get(name.replace('/', '.'));
            super.visit(version, access, name, signature, superName, interfaces);
        }

        @Override
        public MethodVisitor visitMethod(
                int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
            if (mv != null && method != null && method.equals(name)) {
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
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            if (opcode == Opcodes.INVOKEVIRTUAL
                    && owner.equals(FIELD_OWNER)
                    && (name.equals("set") || name.equals("setInt"))) {
                String hookName = name.length() == 3 ? "setField" : name + "Field";
                String hookDesc = "(Ljava/lang/reflect/Field;" + descriptor.substring(1);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, HOOK_OWNER, hookName, hookDesc, false);
            } else {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }
        }
    }
}
