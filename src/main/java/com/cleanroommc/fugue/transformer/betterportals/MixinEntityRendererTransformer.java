package com.cleanroommc.fugue.transformer.betterportals;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

//Target: [
//      de.johni0702.minecraft.view.impl.mixin.MixinEntityRenderer_NoOF,
//      de.johni0702.minecraft.view.impl.mixin.MixinEntityRenderer_OF
// ]
public class MixinEntityRendererTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (
                s.equals("de.johni0702.minecraft.view.impl.mixin.MixinEntityRenderer_NoOF") ||
                s.equals("de.johni0702.minecraft.view.impl.mixin.MixinEntityRenderer_OF")
        ) {
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

        return bytes;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
