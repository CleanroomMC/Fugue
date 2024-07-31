package com.cleanroommc.fugue.transformer.groovyscript;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class RealGroovyClassLoaderTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            /*cc.getDeclaredMethod("createClass").instrument(new ExprEditor(){
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    //Fugue.LOGGER.info("Transforming Groovy class method: {}", m.getMethodName());
                    if (m.getMethodName().equals("access$400")) {
                        m.replace("$_ = com.cleanroommc.fugue.helper.HookHelper#defineClass($2, $3, $4, $5, $6);");
                    }
                }
            });*/
            cc.getConstructor("(Ljava/lang/ClassLoader;Lorg/codehaus/groovy/control/CompilerConfiguration;Z)V").insertAfter("com.cleanroommc.fugue.helper.HookHelper#logCL($1);");

            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
