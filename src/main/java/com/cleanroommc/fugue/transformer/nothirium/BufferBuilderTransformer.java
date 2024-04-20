package com.cleanroommc.fugue.transformer.nothirium;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import net.minecraft.launchwrapper.Launch;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class BufferBuilderTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        if (!Launch.classLoader.isClassExist("meldexun.nothirium.mc.asm.NothiriumPlugin")) {
            try {
                CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
                cc.getDeclaredMethod("func_181670_b").insertAfter("$0.address = meldexun.matrixutil.MemoryUtil.getAddress($0.field_179001_a);");
                bytes = cc.toBytecode();
            } catch (Throwable t) {
                Fugue.LOGGER.error(t);
            }
        }
        return bytes;
    }
}
