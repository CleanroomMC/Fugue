package com.cleanroommc.fugue.transformer.openmodlib;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class InjectorMethodVisitorTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("<init>")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        instructions.clear();
                        instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        instructions.add(new FieldInsnNode(Opcodes.PUTFIELD, "openmods/renderer/PlayerRendererHookVisitor$InjectorMethodVisitor", "this$0", "Lopenmods/renderer/PlayerRendererHookVisitor;"));
                        instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        instructions.add(new LdcInsnNode(589824));
                        instructions.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "org/objectweb/asm/MethodVisitor", "<init>", "(ILorg/objectweb/asm/MethodVisitor;)V", false));
                        instructions.add(new InsnNode(Opcodes.RETURN));

                        methodNode.exceptions.clear();
                        methodNode.tryCatchBlocks.clear();
                    }
                }
                if (methodNode.name.equals("visitInsn")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL && insnNode instanceof MethodInsnNode methodInsnNode)
                            {
                                if (methodInsnNode.name.equals("getInternalName"))
                                {
                                    for (int i = 0; i < 6; i++){
                                        instructions.remove(methodInsnNode.getNext());
                                    }
                                    instructions.insert(methodInsnNode, new LdcInsnNode("(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V"));
                                    instructions.insert(methodInsnNode, new LdcInsnNode("post"));
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
