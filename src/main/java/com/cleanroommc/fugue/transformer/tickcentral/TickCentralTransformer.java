package com.cleanroommc.fugue.transformer.tickcentral;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class TickCentralTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getDeclaredMethod("getASMTransformerClass").insertAfter("{com.cleanroommc.fugue.helper.HookHelper#TickCentralPreLoad();}");
            bytes = cc.toBytecode();
        }catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
