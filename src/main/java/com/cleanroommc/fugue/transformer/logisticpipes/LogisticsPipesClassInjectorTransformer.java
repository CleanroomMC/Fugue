package com.cleanroommc.fugue.transformer.logisticpipes;

import com.cleanroommc.fugue.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;


public class LogisticsPipesClassInjectorTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.addMethod(CtMethod.make(
                    """
                    public int getPriority() {
                        return -1000;
                    }
                    """, cc));
            bytes = cc.toBytecode();
        }catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
