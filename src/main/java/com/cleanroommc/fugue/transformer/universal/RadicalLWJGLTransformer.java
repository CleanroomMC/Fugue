package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import top.outlands.foundation.IExplicitTransformer;

public class RadicalLWJGLTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(0);
        ClassVisitor cv = new ClassRemapper(writer, new LWJGLXRemapper());
        reader.accept(cv, 0);
        return writer.toByteArray();
    }

    private static class LWJGLXRemapper extends Remapper {
        public LWJGLXRemapper() {
            super(Opcodes.ASM9);
        }

        @Override
        public String map(String typeName) {
            if (typeName == null) {
                return null;
            }
            return typeName.replace("org/lwjgl/", "org/lwjglx/");
        }

        @Override
        public Object mapValue(final Object value) {
            if (value == null) {
                return null;
            }

            if (value instanceof String str) {
                return str.replace("org/lwjgl/", "org/lwjglx/");
            }

            return super.mapValue(value);
        }
    }
}
