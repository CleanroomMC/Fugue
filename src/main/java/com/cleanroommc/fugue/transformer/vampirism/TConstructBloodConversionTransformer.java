package com.cleanroommc.fugue.transformer.vampirism;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class TConstructBloodConversionTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {

        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.setModifiers(Modifier.setPublic(cc.getModifiers()));
            bytes = cc.toBytecode();
            cc.defrost();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
