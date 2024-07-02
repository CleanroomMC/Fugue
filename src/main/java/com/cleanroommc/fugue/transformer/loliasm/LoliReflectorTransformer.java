package com.cleanroommc.fugue.transformer.loliasm;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class LoliReflectorTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            var cp = ClassPool.getDefault();
            CtClass cc = cp.makeClass(new ByteArrayInputStream(bytes));
            cp.importPackage("java.lang.invoke");
            cp.importPackage("zone.rong.loliasm.api.datastructures");

            cc.getClassInitializer().setBody(
                    """
                    {
                        LOOKUP = MethodHandles.lookup();
                        classLoader$DefineClass = resolveMethod(ClassLoader.class, "defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
                    }
                    """);
            cp.importPackage("net.minecraft.launchwrapper");
            cc.getDeclaredMethod("removeTransformerExclusion").setBody(
                    """
                    {
                        Launch#classLoader.removeTransformerExclusion($1);
                    }
                    """);
            cc.getDeclaredMethod("addTransformerExclusion").setBody(
                    """
                    {
                        Launch#classLoader.addTransformerExclusion($1);
                    }
                    """);
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}