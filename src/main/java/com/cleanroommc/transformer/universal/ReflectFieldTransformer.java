package com.cleanroommc.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class ReflectFieldTransformer implements IExplicitTransformer {

    private static final String OUR_REFLECTION_CLASS = "com/cleanroommc/hackery/ReflectionHackery";

    private static void replaceInstruction(InsnList insnList, MethodInsnNode oldNode, String methodName, String methodDescriptor)
    {
        insnList.set(oldNode, new MethodInsnNode(Opcodes.INVOKESTATIC, OUR_REFLECTION_CLASS, methodName, methodDescriptor));
    }


    @Override
    public byte[] transform(byte[] bytes)
    {

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                InsnList instructions = methodNode.instructions;
                if (instructions != null) {
                    for (AbstractInsnNode insnNode : instructions) {
                        if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && insnNode instanceof MethodInsnNode methodInsnNode) {
                            if ("java/lang/reflect/Field".equals(methodInsnNode.owner)) {
                                switch (methodInsnNode.name) {
                                    case "set" ->
                                            replaceInstruction(instructions, methodInsnNode, "setField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;Ljava/lang/Object;)V");
                                    case "get" ->
                                            replaceInstruction(instructions, methodInsnNode, "getField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;)Ljava/lang/Object;");
                                    case "setBoolean" ->
                                            replaceInstruction(instructions, methodInsnNode, "setBooleanField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;Z)V");
                                    case "getBoolean" ->
                                            replaceInstruction(instructions, methodInsnNode, "getBooleanField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;)Z");
                                    case "setByte" ->
                                            replaceInstruction(instructions, methodInsnNode, "setByteField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;B)V");
                                    case "getByte" ->
                                            replaceInstruction(instructions, methodInsnNode, "getByteField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;)B");
                                    case "setChar" ->
                                            replaceInstruction(instructions, methodInsnNode, "setCharField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;C)V");
                                    case "getChar" ->
                                            replaceInstruction(instructions, methodInsnNode, "getCharField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;)C");
                                    case "setShort" ->
                                            replaceInstruction(instructions, methodInsnNode, "setShortField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;S)V");
                                    case "getShort" ->
                                            replaceInstruction(instructions, methodInsnNode, "getShortField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;)S");
                                    case "setInt" ->
                                            replaceInstruction(instructions, methodInsnNode, "setIntField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;I)V");
                                    case "getInt" ->
                                            replaceInstruction(instructions, methodInsnNode, "getIntField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;)I");
                                    case "setLong" ->
                                            replaceInstruction(instructions, methodInsnNode, "setLongField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;J)V");
                                    case "getLong" ->
                                            replaceInstruction(instructions, methodInsnNode, "getLongField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;)J");
                                    case "setFloat" ->
                                            replaceInstruction(instructions, methodInsnNode, "setFloatField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;F)V");
                                    case "getFloat" ->
                                            replaceInstruction(instructions, methodInsnNode, "getFloatField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;)F");
                                    case "setDouble" ->
                                            replaceInstruction(instructions, methodInsnNode, "setDoubleField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;D)V");
                                    case "getDouble" ->
                                            replaceInstruction(instructions, methodInsnNode, "getDoubleField", "(Ljava/lang/reflect/Field;Ljava/lang/Object;)D");
                                }
                            }
                        }
                    }
                }
            }
        }
        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
