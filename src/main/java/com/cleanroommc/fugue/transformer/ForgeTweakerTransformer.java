package com.cleanroommc.fugue.transformer;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class ForgeTweakerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getConstructors()[0].setBody(
                    """
                    {customskinloader.forge.platform.IFMLPlatform$FMLPlatformInitializer#initFMLPlatform();}
                    """);
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
