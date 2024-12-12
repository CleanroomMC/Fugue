package com.cleanroommc.fugue.transformer.logisticpipes;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//Target: logisticspipes.utils.StaticResolverUtil
public class StaticResolverUtilTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        //StaticResolverUtil.java
        // 34 -:  data.parallelStream()
        // 34 +:  data.stream()

        classNode.methods.stream()
                .filter(method -> method.name.equals("lambda$findClassesByType$1"))
                .findFirst()
                .ifPresent(method -> {
                    var instructions = method.instructions;
                    for (var insnNode : instructions) {
                        if (
                                insnNode instanceof MethodInsnNode methodInsnNode &&
                                methodInsnNode.getOpcode() == Opcodes.INVOKEINTERFACE &&
                                methodInsnNode.owner.equals("java/util/Set") &&
                                methodInsnNode.name.equals("parallelStream") &&
                                methodInsnNode.desc.equals("()Ljava/util/stream/Stream;")
                        ) {
                            methodInsnNode.name = "stream";
                        }
                    }
                });

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
