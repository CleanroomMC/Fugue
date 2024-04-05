package com.cleanroommc.fugue.transformer.loliasm;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class JavaFixesTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            var cp = ClassPool.getDefault();
            CtClass cc = cp.makeClass(new ByteArrayInputStream(bytes));
            cc.getDeclaredMethod("run").setBody("{}");
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
