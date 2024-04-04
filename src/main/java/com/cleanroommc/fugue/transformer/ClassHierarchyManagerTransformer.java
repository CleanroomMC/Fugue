package com.cleanroommc.fugue.transformer;

import com.cleanroommc.fugue.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class ClassHierarchyManagerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getMethod("getOrCreateCache", "(Ljava/lang/String;)Lcodechicken/asm/ClassHierarchyManager$SuperCache;").setBody(
                    """
                    {
                     SuperCache cache;
                     if (!superclasses.containsKey(name)) {
                         cache = new SuperCache();
                         superclasses.put(name, cache);
                     } else {
                         cache = superclasses.get(name);
                     }
                     return cache;
                    }
                    """);
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
