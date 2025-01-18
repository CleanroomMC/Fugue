package com.cleanroommc.fugue.transformer.journeymap;

import com.google.common.io.MoreFiles;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.io.File;

public class ThemeLoaderTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null)
        {
            out:
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("preloadCurrentTheme")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode instanceof MethodInsnNode methodInsnNode)
                            {
                                if (methodInsnNode.name.equals("fileTreeTraverser"))
                                {
                                    methodInsnNode.owner = "com/google/common/io/MoreFiles";
                                    methodInsnNode.name = "fileTraverser";
                                    methodInsnNode.desc = "()Lcom/google/common/graph/Traverser;";
                                }
                                if (methodInsnNode.name.equals("breadthFirstTraversal"))
                                {
                                    instructions.insertBefore(methodInsnNode, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/File", "toPath", "()Ljava/nio/file/Path;", false));
                                    methodInsnNode.owner = "com/google/common/graph/Traverser";
                                    methodInsnNode.name = "breadthFirst";
                                    methodInsnNode.desc = "(Ljava/lang/Object;)Ljava/lang/Iterable;";
                                }
                                if (methodInsnNode.name.equals("iterator"))
                                {
                                    methodInsnNode.setOpcode(Opcodes.INVOKEINTERFACE);
                                    methodInsnNode.owner = "java/lang/Iterable";
                                    methodInsnNode.itf = true;
                                }
                            }
                            if (insnNode instanceof TypeInsnNode typeInsnNode)
                            {
                                if (typeInsnNode.getOpcode() == Opcodes.CHECKCAST) {
                                    typeInsnNode.desc = "java/nio/file/Path";
                                    instructions.insert(typeInsnNode, new MethodInsnNode(Opcodes.INVOKEINTERFACE, "java/nio/file/Path", "toFile", "()Ljava/io/File;", true));
                                    break out;
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
