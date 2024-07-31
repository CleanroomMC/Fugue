package com.cleanroommc.fugue.transformer.groovyscript;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import net.minecraft.launchwrapper.Launch;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class ASTTransformationCollectorCodeVisitorTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            /*cc.getDeclaredMethod("verifyAndAddTransform").insertBefore(
                    """
                    {
                        com.cleanroommc.fugue.helper.HookHelper#verifyAndAddTransform($$);
                    }
                    """);*/
            cc.getDeclaredMethod("loadTransformClass").setBody("{return net.minecraft.launchwrapper.Launch#classLoader.loadClass($1);}");
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
