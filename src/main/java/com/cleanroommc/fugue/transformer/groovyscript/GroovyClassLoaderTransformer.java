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
            cc.getMethod("loadClass", "(Ljava/lang/String;ZZZ)Ljava/lang/Class;").instrument(new ExprEditor(){
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    //Fugue.LOGGER.info("Transforming Groovy class method: {}", m.getMethodName());
                    if (m.getMethodName().equals("loadClass")) {
                        m.replace("""
                                Class c = com.cleanroommc.fugue.helper.HookHelper#loadClass($1);
                                if (c != null) {
                                    $_ = c;
                                } else {
                                    $_ = $proceed($$);
                                }
                                """);
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
