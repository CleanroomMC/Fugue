package com.cleanroommc.fugue.transformer.universal;

import com.cleanroommc.fugue.common.Fugue;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class ITweakerTransformer implements IExplicitTransformer {
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
                        if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && insnNode instanceof MethodInsnNode methodInsnNode)
                        {
                            if ("java/net/URL".equals(methodInsnNode.owner) && "toURI".equalsIgnoreCase(methodInsnNode.name))
                            {
                                instructions.insertBefore(methodInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC, "com/cleanroommc/fugue/helper/HookHelper", "toURI", "(Ljava/net/URL;)Ljava/net/URI;", false));
                                instructions.remove(methodInsnNode);
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
