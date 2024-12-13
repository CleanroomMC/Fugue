package com.cleanroommc.fugue.transformer.essential;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.util.ListIterator;

//Target: [
//      gg.essential.loader.stage2.relaunch.Relaunch
// ]
public class EssentialRelaunchTransformer implements IExplicitTransformer{

    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (var method : classNode.methods) {
            if ("relaunch".equals(method.name)) {
                ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() instanceof LdcInsnNode ldcInsnNode) {
                        if (ldcInsnNode.cst instanceof Type type) {
                            if ("net/minecraft/launchwrapper/Launch".equals(type.getInternalName())) {
                                if (ldcInsnNode.getNext() instanceof MethodInsnNode methodInsnNode) {
                                    if ("getClassLoader".equals(methodInsnNode.name)
                                        && "java/lang/Class".equals(methodInsnNode.owner)
                                        && "()Ljava/lang/ClassLoader;".equals(methodInsnNode.desc)) {
                                        method.instructions.insertBefore(methodInsnNode,
                                                new FieldInsnNode(Opcodes.GETSTATIC, "net/minecraft/launchwrapper/Launch", "classLoader", "Lnet/minecraft/launchwrapper/LaunchClassLoader;"));
                                        method.instructions.remove(methodInsnNode);
                                        iterator.remove();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    @Override
    public int getPriority() {
        return 0;
    }
}