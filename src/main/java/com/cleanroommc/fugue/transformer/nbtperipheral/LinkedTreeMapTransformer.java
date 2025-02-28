package com.cleanroommc.fugue.transformer.nbtperipheral;

import com.cleanroommc.fugue.common.Fugue;
import javassist.*;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class LinkedTreeMapTransformer implements IExplicitTransformer {
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
                        if (insnNode instanceof MethodInsnNode methodInsnNode
                                && methodInsnNode.owner.equals("com/google/gson/internal/LinkedTreeMap")
                                && methodInsnNode.name.equals("<init>")
                        )
                        {
                            methodInsnNode.desc = "(Ljava/util/Comparator;Z)V";
                            instructions.insertBefore(methodInsnNode, new InsnNode(Opcodes.ICONST_0));
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
