package com.cleanroommc.fugue.transformer.replaymod;

import com.cleanroommc.fugue.common.Fugue;
import com.google.common.util.concurrent.Futures;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class FuturesTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            ExprEditor redirector = new ExprEditor(){
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("addCallback")) {
                        m.where().setBody("$_ = com.cleanroommc.fugue.helper.HookHelper#addCallback($1, $2);");
                    }
                }
            };
            cc.getDeclaredMethod("keyframeRepoButtonPressed").instrument(redirector);
            cc.getDeclaredMethod("loadEntityTracker").instrument(redirector);
            bytes = cc.toBytecode();
            cc.defrost();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
