package com.cleanroommc.fugue.transformer.logisticpipes;

import com.cleanroommc.fugue.common.Fugue;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import net.minecraft.launchwrapper.Launch;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class LoadClassTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.instrument(new ExprEditor(){
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if(m.getMethodName().equals("loadClass")) {
                        m.replace("$_ = net.minecraft.launchwrapper.Launch#classLoader.loadClass($$);");
                    }
                }
            });

            bytes = cc.toBytecode();
        }catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
