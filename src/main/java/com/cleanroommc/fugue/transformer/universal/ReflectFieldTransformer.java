package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.util.HashMap;
import java.util.Map;

public class ReflectFieldTransformer implements IExplicitTransformer {

    private static final String OUR_REFLECTION_CLASS = "com/cleanroommc/hackery/ReflectionHackery";

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
        private final Map<String, String> primitiveMap = new HashMap<>() {
            {
                put("Boolean", "Z");
                put("Byte", "B");
                put("Char", "C");
                put("Short", "S");
                put("Int", "I");
                put("Long", "J");
                put("Float", "F");
                put("Double", "D");
            }
        };

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
                if (owner.equals("java/lang/reflect/Field")
                        && (name.startsWith("set") || name.startsWith("get"))
                        && (name.length() == 3 || primitiveMap.containsKey(name.substring(3)))) {
                    String newName = name + "Field";
                    String newDesc;
                    if (name.length() == 3) {
                        newDesc = "Ljava/lang/Object;";
                    } else {
                        newDesc = primitiveMap.get(name.substring(3));
                    }
                    if (name.startsWith("s")) {
                        newDesc = "(Ljava/lang/reflect/Field;Ljava/lang/Object;" + newDesc + ")V";
                    } else {
                        newDesc = "(Ljava/lang/reflect/Field;Ljava/lang/Object;)" + newDesc;
                    }
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, OUR_REFLECTION_CLASS, newName, newDesc, false);
                } else {
                    mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            }
        }
    }
}
