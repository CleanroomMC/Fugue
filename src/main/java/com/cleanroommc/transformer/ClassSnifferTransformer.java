package com.cleanroommc.transformer;

import com.cleanroommc.Fugue;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class ClassSnifferTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.instrument(new ExprEditor(){
                @Override
                public void edit(MethodCall call) throws CannotCompileException {
                    if (call.getClassName().equals("org.apache.logging.log4j.Logger") && call.getMethodName().equals("warn")) {
                        call.replace("{}");
                    }
                }
            });
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
