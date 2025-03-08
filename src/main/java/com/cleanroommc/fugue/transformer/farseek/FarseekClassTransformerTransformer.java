package com.cleanroommc.fugue.transformer.farseek;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class FarseekClassTransformerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getDeclaredMethod("moveAfterSponge").setBody("{}");
            cc.addMethod(CtMethod.make("public int getPriority() {return 2000;}", cc));
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
