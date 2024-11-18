package com.cleanroommc.fugue.transformer.fivezig;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class ClassTweakerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("acceptOptions")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.INVOKESTATIC && insnNode instanceof MethodInsnNode methodInsnNode)
                            {
                                switch (methodInsnNode.name) {
                                    case "addConfiguration" -> {
                                        instructions.remove(methodInsnNode.getPrevious());
                                        instructions.remove(methodInsnNode);
                                    }
                                    case "setObfuscationContext", "setSide" -> {
                                        instructions.insert(methodInsnNode, new InsnNode(Opcodes.POP));
                                        instructions.remove(methodInsnNode);
                                    }
                                    case "forName" -> {
                                        if (methodInsnNode.getPrevious() instanceof LdcInsnNode) {
                                            methodInsnNode.name = "isClassExist";
                                            methodInsnNode.owner = "com/cleanroommc/fugue/helper/HookHelper";
                                            methodInsnNode.desc = "(Ljava/lang/String;)Z";
                                        }
                                    }
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
