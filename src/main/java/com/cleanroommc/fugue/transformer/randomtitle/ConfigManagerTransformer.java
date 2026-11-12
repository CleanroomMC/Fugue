package com.cleanroommc.fugue.transformer.randomtitle;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import top.outlands.foundation.IExplicitTransformer;

import java.util.ArrayList;
import java.util.List;

public class ConfigManagerTransformer implements IExplicitTransformer {


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
            if (mv != null && name.equals("getTitleFromHitokoto")) {
                mv = new MV(mv);
            }
            return mv;
        }
    }

    private static class MV extends MethodVisitor {
        
        private static boolean remove = false;
        private static boolean done = false;
        
        public MV(MethodVisitor mv) {
            super(Opcodes.ASM9, mv);
        }

        public void visitTypeInsn(final int opcode, final String type) {
            if (mv != null && !remove) {
                mv.visitTypeInsn(opcode, type);
            }
        }

        public void visitLdcInsn(final Object value) {
            if (mv != null && !remove) {
                mv.visitLdcInsn(value);
            }
        }

        public void visitInsn(final int opcode) {
            if (mv != null && !remove) {
                mv.visitInsn(opcode);
            }
        }
        
        public void visitMethodInsn(
                final int opcode,
                final String owner,
                final String name,
                final String descriptor,
                final boolean isInterface) {
            if (mv != null) {
                if (!done) {
                    if (owner.equals("org/apache/http/util/EntityUtils")) {
                        remove = false;
                        done = true;
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/cleanroommc/fugue/helper/HookHelper", "getTitle", "()Ljava/lang/String;", false);
                        return;
                    }
                    if (remove) {
                        return;
                    }
                    if (owner.equals("org/apache/http/impl/client/HttpClients")) {
                        remove = true;
                        return;
                    }
                }
                mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                
                
            }
        }

    }
}