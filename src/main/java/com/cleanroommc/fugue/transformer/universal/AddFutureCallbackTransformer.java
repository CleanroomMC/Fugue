package com.cleanroommc.fugue.transformer.universal;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import top.outlands.foundation.IExplicitTransformer;

import java.util.Map;
import java.util.Set;

public class AddFutureCallbackTransformer implements IExplicitTransformer {

    private final Map<String, String> targets;

    public AddFutureCallbackTransformer(Map<String, String> targets) {
        this.targets = targets;
    }

    @Override
    public byte[] transform(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, 0);
        Set<String> methods =
                Set.of(targets.get(classNode.name.replace('/', '.')).split("\\|"));
        classNode.methods.stream()
                .filter(methodNode -> methods.contains(methodNode.name))
                .forEach(methodNode -> methodNode.instructions.forEach(abstractInsnNode -> {
                    if (abstractInsnNode instanceof MethodInsnNode methodInsnNode) {
                        if (methodInsnNode.owner.equals("com/google/common/util/concurrent/Futures")) {
                            if (methodInsnNode.name.equals("addCallback")
                                    && methodInsnNode.desc.equals(
                                            "(Lcom/google/common/util/concurrent/ListenableFuture;Lcom/google/common/util/concurrent/FutureCallback;)V")) {
                                methodInsnNode.owner = "com/cleanroommc/fugue/helper/HookHelper";
                                methodInsnNode.setOpcode(Opcodes.INVOKESTATIC);
                                methodInsnNode.desc =
                                        "(Lcom/google/common/util/concurrent/ListenableFuture;Lcom/google/common/util/concurrent/FutureCallback;)V";
                                methodInsnNode.itf = false;
                            }
                        }
                    }
                }));
        ClassWriter writer = new ClassWriter(0);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
