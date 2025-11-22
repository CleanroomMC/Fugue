package com.cleanroommc.fugue.transformer.sereneseasons;

import com.cleanroommc.fugue.common.Fugue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import top.outlands.foundation.IExplicitTransformer;

public class EntityRendererTransformerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);
        
        for (var method : classNode.methods) {
            if ("transformEntityRenderer".equals(method.name)) {
                for (var insnNode : method.instructions ) {
                    if (insnNode.getOpcode() == Opcodes.ISUB) {
                        if (insnNode.getPrevious().getOpcode() == Opcodes.ICONST_2) {
                            method.instructions.remove(insnNode.getPrevious());
                            method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.ICONST_4));
                        } else if (insnNode.getPrevious().getOpcode() == Opcodes.ICONST_1) {
                            method.instructions.remove(insnNode.getPrevious());
                            method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.ICONST_3));
                        }
                    }
                }
            }
        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
