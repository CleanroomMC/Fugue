package com.cleanroommc.fugue.transformer.universal;

import com.cleanroommc.fugue.common.Fugue;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class RemoveMixinInitFromCotrTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getConstructors()[0].instrument(new ExprEditor(){
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("addConfiguration") && m.getMethodName().equals("init")) {
                        m.where().setBody("{}");
                    }
                }
            });
            bytes = cc.toBytecode();
            cc.defrost();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
