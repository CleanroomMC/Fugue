package com.cleanroommc.fugue.transformer.exu;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class FieldSetterTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getClassInitializer().setBody("{}");
            cc.getConstructors()[0].setBody(
                    """
                    {
                        this.field = net.minecraftforge.fml.relauncher.ReflectionHelper#findField($$);
                        this.field.setAccessible(true);
                    }
                    """);
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
