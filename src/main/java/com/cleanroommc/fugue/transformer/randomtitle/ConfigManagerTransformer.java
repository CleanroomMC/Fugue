package com.cleanroommc.fugue.transformer.randomtitle;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
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
        
        private static int counter = 0;
        
        private static final List<Pair<String, String>> replaceMap = new ArrayList<>(){{
            add(Pair.of("org/apache/http/client/methods/HttpGet", "org/apache/hc/client5/http/classic/methods/HttpGet"));
            add(Pair.of("org/apache/http/impl/client/CloseableHttpClient", "org/apache/hc/client5/http/impl/classic/CloseableHttpClient"));
            add(Pair.of("org/apache/http/client/methods/CloseableHttpResponse", "org/apache/hc/client5/http/impl/classic/CloseableHttpResponse"));
            add(Pair.of("org/apache/http/impl/client/HttpClients", "org/apache/hc/client5/http/impl/classic/HttpClients"));
            add(Pair.of("org/apache/http/util/EntityUtils", "org/apache/hc/core5/http/io/entity/EntityUtils"));
            add(Pair.of("org/apache/http/HttpEntity", "org/apache/hc/core5/http/HttpEntity"));
            add(Pair.of("org/apache/http/client/methods/HttpUriRequest", "org/apache/hc/core5/http/ClassicHttpRequest"));
        }};
        
        public MV(MethodVisitor mv) {
            super(Opcodes.ASM9, mv);
        }

        public void visitTypeInsn(final int opcode, final String type) {
            if (mv != null) {
                if (opcode == Opcodes.NEW && type.startsWith("org/apache/http")) {
                    mv.visitTypeInsn(opcode, "org/apache/hc/client5/http/classic/methods/HttpGet");
                } else {
                    mv.visitTypeInsn(opcode, type);
                }
            }
        }

        public void visitMethodInsn(
                final int opcode,
                final String owner,
                final String name,
                final String descriptor,
                final boolean isInterface) {
            if (mv != null) {
                if (counter < 5 && owner.startsWith("org/apache/http")) {
                    String newOwner = owner;
                    String newDescriptor = descriptor;
                    int newOpcode = opcode;
                    if (newOpcode == Opcodes.INVOKEINTERFACE) {
                        newOpcode = Opcodes.INVOKEVIRTUAL;
                    }
                    for (Pair<String, String> pair : replaceMap) {
                        newOwner = newOwner.replace(pair.getLeft(), pair.getRight());
                        newDescriptor = newDescriptor.replace(pair.getLeft(), pair.getRight());
                    }
                    mv.visitMethodInsn(newOpcode, newOwner, name, newDescriptor, false);
                    counter++;
                } else {
                    mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
                
            }
        }

    }
}