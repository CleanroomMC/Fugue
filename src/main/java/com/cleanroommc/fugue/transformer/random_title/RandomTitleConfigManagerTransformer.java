package com.cleanroommc.fugue.transformer.random_title;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.util.ListIterator;

//Target: [
//      me.PercyDan.RandomTitle.ConfigManager
// ]
public class RandomTitleConfigManagerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] cls) {
        var classReader = new ClassReader(cls);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (var methodNode : classNode.methods) {
            if ("getTitleFromHitokoto".equals(methodNode.name)) {
                boolean started = false;
                AbstractInsnNode endNode = null;
                ListIterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() instanceof MethodInsnNode methodInsnNode) {
                        if ("org/apache/http/impl/client/HttpClients".equals(methodInsnNode.owner) && "createDefault".equals(methodInsnNode.name) && "()Lorg/apache/http/impl/client/CloseableHttpClient;".equals(methodInsnNode.desc)) {
                            started = true;
                        } else if ("org/apache/http/util/EntityUtils".equals(methodInsnNode.owner) && "toString".equals(methodInsnNode.name) && "(Lorg/apache/http/HttpEntity;)Ljava/lang/String;".equals(methodInsnNode.desc)) {
                            endNode = methodInsnNode;
                            break;
                        }
                    }
                    if (started) {
                        iterator.remove();
                    }
                }

                InsnList newCodes = new InsnList();
                newCodes.add(new LdcInsnNode("https://v1.hitokoto.cn/"));
                newCodes.add(new InsnNode(Opcodes.ICONST_0));
                newCodes.add(new TypeInsnNode(Opcodes.ANEWARRAY, "Ljava/lang/String;"));
                newCodes.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/cleanroommc/fugue/helper/ConnectionHelper", "sendGetRequest", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", false));

                methodNode.instructions.insert(endNode, newCodes);
                methodNode.instructions.remove(endNode);
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