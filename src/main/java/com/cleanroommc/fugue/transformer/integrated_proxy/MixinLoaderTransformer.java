package com.cleanroommc.fugue.transformer.integrated_proxy;

import com.cleanroommc.fugue.common.Fugue;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class MixinLoaderTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getConstructors()[0].instrument(new ExprEditor(){
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    Fugue.LOGGER.info("{} {}", m.getMethodName() , m.getLineNumber());
                    if (m.getMethodName().equals("addConfiguration") && m.getLineNumber() == 21) {
                        m.where().setBody("{}");
                    } else if (m.getMethodName().equals("init")) {
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
