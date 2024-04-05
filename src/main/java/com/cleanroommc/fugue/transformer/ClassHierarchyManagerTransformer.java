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
            ClassPool pool = ClassPool.getDefault();
            pool.importPackage("codechicken.asm.ClassHierarchyManager.SuperCache");
            pool.importPackage("codechicken.asm.ClassHierarchyManager");
            CtClass cc = pool.makeClass(new ByteArrayInputStream(bytes));
            cc.getMethod("getOrCreateCache", "(Ljava/lang/String;)Lcodechicken/asm/ClassHierarchyManager$SuperCache;").setBody(
                    """
                    {
                     SuperCache cache;
                     if (!superclasses.containsKey($1)) {
                         cache = new SuperCache();
                         superclasses.put($1, cache);
                     } else {
                         cache = superclasses.get($1);
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
