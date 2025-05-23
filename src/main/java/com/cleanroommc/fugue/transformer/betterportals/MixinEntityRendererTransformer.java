package com.cleanroommc.fugue.transformer.betterportals;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

//Target: [
//      de.johni0702.minecraft.view.impl.mixin.MixinEntityRenderer_NoOF,
//      de.johni0702.minecraft.view.impl.mixin.MixinEntityRenderer_OF
// ]
public class MixinEntityRendererTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (MethodNode method : classNode.methods) {
            if (method.name.equals("createCamera")) {
                for (AnnotationNode annotation : method.visibleAnnotations) {
                    if (annotation.desc.equals("Lorg/spongepowered/asm/mixin/injection/Redirect;")) {
                        var at = (AnnotationNode)annotation.values.get(3);
                        at.values.add("target");
                        at.values.add("net/minecraft/client/renderer/culling/Frustum");
                    }
                }
            }
        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

}
