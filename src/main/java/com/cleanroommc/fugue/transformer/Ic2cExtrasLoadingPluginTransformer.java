package com.cleanroommc.fugue.transformer;

import com.cleanroommc.fugue.common.Fugue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

public class Ic2cExtrasLoadingPluginTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        classNode.visibleAnnotations.removeIf(anno -> anno.desc.equals("Lnet/minecraftforge/fml/relauncher/IFMLLoadingPlugin$TransformerExclusions;"));
        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
