package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class ScriptEngineTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                InsnList instructions = methodNode.instructions;
                if (instructions != null)
                {
                    for (AbstractInsnNode insnNode : instructions)
                    {
                        if (insnNode.getOpcode() == Opcodes.NEW && insnNode instanceof TypeInsnNode typeInsnNode)
                        {
                            if ("javax/script/ScriptEngineManager".equals(typeInsnNode.desc))
                            {
                                typeInsnNode.desc = "com/cleanroommc/loader/scripting/CleanroomScriptEngineManager";
                            }
                        }
                        else if (insnNode.getOpcode() == Opcodes.INVOKESPECIAL && insnNode instanceof MethodInsnNode methodInsnNode)
                        {
                            if ("javax/script/ScriptEngineManager".equals(methodInsnNode.owner))
                            {
                                methodInsnNode.owner = "com/cleanroommc/loader/scripting/CleanroomScriptEngineManager";
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
