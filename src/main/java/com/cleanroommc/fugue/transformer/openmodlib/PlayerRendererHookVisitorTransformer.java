package com.cleanroommc.fugue.transformer.openmodlib;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class PlayerRendererHookVisitorTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, ClassReader.SKIP_DEBUG);
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("<init>")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.INVOKESTATIC && insnNode instanceof MethodInsnNode methodInsnNode)
                            {
                                if (methodInsnNode.name.equals("of") && methodInsnNode.getPrevious() instanceof LdcInsnNode ldcInsnNode && ldcInsnNode.cst instanceof Type)
                                {
                                    ldcInsnNode.cst = "net.minecraft.client.entity.AbstractClientPlayer";
                                    methodInsnNode.desc = "(Ljava/lang/String;)Lopenmods/asm/MappedType;";
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
