package com.cleanroommc.fugue.transformer.betterportals;

import com.cleanroommc.fugue.common.Fugue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Executor;

//Target: [
//      de.johni0702.minecraft.betterportals.impl.transition.common.ExtensionsKt,
//      de.johni0702.minecraft.betterportals.impl.common.ExtensionsKt,
//      de.johni0702.minecraft.view.impl.common.ExtensionsKt
// ]
public class ExtensionKtTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (var method : classNode.methods) {
            var instructions = method.instructions;
            for (var insnNode : method.instructions) {
                if (insnNode instanceof MethodInsnNode methodInsnNode) {
                    if (
                            methodInsnNode.owner.equals("com/google/common/util/concurrent/Futures") &&
                            methodInsnNode.name.equals("addCallback") &&
                            methodInsnNode.desc.equals("(Lcom/google/common/util/concurrent/ListenableFuture;Lcom/google/common/util/concurrent/FutureCallback;)V")
                    ) {
                        instructions.insert(
                                methodInsnNode.getPrevious(),
                                new MethodInsnNode(
                                        Opcodes.INVOKESTATIC,
                                        "com/google/common/util/concurrent/MoreExecutors",
                                        "directExecutor",
                                        "()Ljava/util/concurrent/Executor;"
                                )
                        );

                        methodInsnNode.desc = "(Lcom/google/common/util/concurrent/ListenableFuture;Lcom/google/common/util/concurrent/FutureCallback;Ljava/util/concurrent/Executor;)V";
                    }
                }
            }
        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
