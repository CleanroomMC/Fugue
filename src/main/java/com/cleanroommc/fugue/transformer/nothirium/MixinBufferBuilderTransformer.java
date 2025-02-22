package com.cleanroommc.fugue.transformer.nothirium;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import net.minecraft.launchwrapper.Launch;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class MixinBufferBuilderTransformer implements IExplicitTransformer {

    @Override
    public byte[] transform(byte[] bytes) {
        if (!Launch.classLoader.isClassExist("meldexun.nothirium.mc.asm.NothiriumTweaker")) {
            try {
                CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
                cc.removeMethod(cc.getDeclaredMethod("growBuffer"));
                bytes = cc.toBytecode();
            } catch (Throwable t) {
                Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
            }
        }
        return bytes;
    }
}
