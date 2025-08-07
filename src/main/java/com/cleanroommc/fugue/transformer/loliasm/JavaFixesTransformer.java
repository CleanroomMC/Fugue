package com.cleanroommc.fugue.transformer.loliasm;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;
import zone.rong.loliasm.core.LoliLoadingPlugin;

import java.io.ByteArrayInputStream;

public class JavaFixesTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        if (Integer.parseInt(LoliLoadingPlugin.VERSION.split("\\.")[1]) > 28) {
            return bytes;
        }
        try {
            var cp = ClassPool.getDefault();
            CtClass cc = cp.makeClass(new ByteArrayInputStream(bytes));
            cc.getDeclaredMethod("run").setBody("{}");
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
