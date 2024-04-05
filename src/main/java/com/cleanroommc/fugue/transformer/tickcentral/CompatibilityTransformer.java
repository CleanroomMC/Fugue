package com.cleanroommc.fugue.transformer.tickcentral;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class CompatibilityTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getDeclaredMethod("FixTransformerOrdering").setBody("{}");
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
