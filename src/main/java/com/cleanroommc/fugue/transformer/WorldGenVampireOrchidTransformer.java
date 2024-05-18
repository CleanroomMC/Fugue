package com.cleanroommc.fugue.transformer;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class WorldGenVampireOrchidTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            CtField flower = cc.getField("flower");
            flower.setModifiers((flower.getModifiers() | Modifier.FINAL | Modifier.PUBLIC) - Modifier.PRIVATE);
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
