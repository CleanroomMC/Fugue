package com.cleanroommc.fugue.transformer.polyfrost;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class LaunchWrapperTweakerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getDeclaredMethod("isInitialized").setBody("{return com.cleanroommc.fugue.helper.HookHelper#isInitialized($$);}");
            cc.getDeclaredMethod("addToClasspath").setBody("{com.cleanroommc.fugue.helper.HookHelper#addToClasspath($$);}");
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
