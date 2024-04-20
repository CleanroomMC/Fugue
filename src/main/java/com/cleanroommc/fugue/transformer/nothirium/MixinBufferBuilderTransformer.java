package com.cleanroommc.fugue.transformer.nothirium;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import net.minecraft.launchwrapper.IClassTransformer;

import java.io.ByteArrayInputStream;

public class MixinBufferBuilderTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (s1.equals("meldexun.nothirium.mc.mixin.vertex.MixinBufferBuilder")) {
            try {
                CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
                cc.removeMethod(cc.getDeclaredMethod("growBuffer"));
                bytes = cc.toBytecode();
            } catch (Throwable t) {
                Fugue.LOGGER.error(t);
            }
        }
        return bytes;
    }
}
