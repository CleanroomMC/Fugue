package com.cleanroommc.fugue.transformer.journeymap;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;
import java.io.File;

public class FileHandlerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getDeclaredMethod("delete").setBody(
            """
            {
            com.cleanroommc.fugue.helper.HookHelper#logFile($1);
            return $1.delete();
            }
            """);
            bytes = cc.toBytecode();
            cc.defrost();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
