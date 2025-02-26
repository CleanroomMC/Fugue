package com.cleanroommc.fugue.transformer.betterhurttimer;

import com.cleanroommc.fugue.common.Fugue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.util.Arrays;

public class DamageSpecialArmorMixinTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("storeValues"))
                {
                    if (methodNode.invisibleAnnotableParameterCount != 7) break;
                    AnnotationNode local = methodNode.invisibleParameterAnnotations[5].getFirst();
                    local.values.removeLast();
                    local.values.add(0);
                    break;
                }

            }
        }
        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
