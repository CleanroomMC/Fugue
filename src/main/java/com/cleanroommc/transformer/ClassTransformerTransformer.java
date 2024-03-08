package com.cleanroommc.transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class ClassTransformerTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(String s1, byte[] bytes) {
        if (bytes == null)
        {
            return null;
        }

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean modified = false;
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("transform")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode instanceof LineNumberNode lineNumberNode)
                            {
                                if (lineNumberNode.line == 693) {
                                    instructions.remove(instructions.get(instructions.indexOf(lineNumberNode) + 1));
                                    instructions.insert(lineNumberNode, new InsnNode(Opcodes.ICONST_0));
                                    modified = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (modified)
        {
            ClassWriter classWriter = new ClassWriter(0);

            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        return bytes;
    }
}
