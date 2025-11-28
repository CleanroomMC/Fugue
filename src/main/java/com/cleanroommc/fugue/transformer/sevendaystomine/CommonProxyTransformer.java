package com.cleanroommc.fugue.transformer.sevendaystomine;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import top.outlands.foundation.IExplicitTransformer;

public class CommonProxyTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);
        for (var field : classNode.fields) {
            if (field.desc.equals("Ljavax/script/ScriptEngineManager;")) {
                field.desc = "Lcom/cleanroommc/loader/scripting/CleanroomScriptEngineManager;";
                break;
            }
        }

        for (var method : classNode.methods) {
            if ("preInit".equals(method.name)) {
                for (var insnNode : method.instructions) {
                    if (insnNode instanceof FieldInsnNode fieldInsnNode
                            && fieldInsnNode.desc.equals("Ljavax/script/ScriptEngineManager;")) {
                        fieldInsnNode.desc = "Lcom/cleanroommc/loader/scripting/CleanroomScriptEngineManager;";
                    }
                }
            }
        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
