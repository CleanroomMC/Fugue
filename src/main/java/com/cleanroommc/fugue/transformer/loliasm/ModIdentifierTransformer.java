package com.cleanroommc.fugue.transformer.loliasm;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class ModIdentifierTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            var cp = ClassPool.getDefault();
            CtClass cc = cp.makeClass(new ByteArrayInputStream(bytes));
            var ifc = cc.getMethod("identifyFromClass", "(Ljava/lang/String;Ljava/util/Map;)Ljava/util/Set;");
            ifc.insertAt(64, """
                    {
                        if (url.getProtocol().equals("jrt")) {
                            return java.util.Collections#emptySet();
                        }
                    }
                    """);
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
