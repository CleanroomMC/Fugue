package com.cleanroommc.fugue.transformer.groovyscript;

import com.cleanroommc.fugue.common.Fugue;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class GroovyClassLoaderTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getDeclaredMethod("loadClass").instrument(new ExprEditor(){
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    //Fugue.LOGGER.info("Transforming Groovy class method: {}", m.getMethodName());
                    if (m.getMethodName().equals("loadClass")) {
                        m.replace("$_ = net.minecraft.launchwrapper.Launch#classLoader.loadClass($1);");
                    }
                }
            });
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
