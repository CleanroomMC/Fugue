package com.cleanroommc.fugue.transformer.universal;

import com.cleanroommc.fugue.common.Fugue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.util.*;

public class FinalStripperTransformer implements IExplicitTransformer {
    private final Map<String, String> targets;
    public FinalStripperTransformer(Map<String, String> targets) {
        this.targets = targets;
    }
    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(0);
        CV cv = new CV(writer);
        reader.accept(cv, 0);
        return writer.toByteArray();
    }

    private class CV extends ClassVisitor {

        private Set<String> fields;
        private String className;

        public CV(ClassVisitor cv) {
            super(Opcodes.ASM9, cv);
        }

        @Override
        public void visit(
                final int version,
                final int access,
                final String name,
                final String signature,
                final String superName,
                final String[] interfaces) {
            String outerName = name.replace('/', '.');
            if (targets.containsKey(outerName)) {
                fields = Set.of(targets.get(outerName.replace('/', '.')).split("\\|"));
                className = outerName;
            }
            if (cv != null) {
                cv.visit(version, access, name, signature, superName, interfaces);
            }
        }

        @Override
        public FieldVisitor visitField(
                final int access,
                final String name,
                final String descriptor,
                final String signature,
                final Object value) {
            if (cv != null) {
                if (fields.contains(name)) {
                    Fugue.LOGGER.debug("Stripping final modifier of {} from {}", name, className.replace('/', '.'));
                    return cv.visitField(access & ~Opcodes.ACC_FINAL, name, descriptor, signature, value);
                }
                return cv.visitField(access, name, descriptor, signature, value);
            }
            return null;
        }

    }
}
