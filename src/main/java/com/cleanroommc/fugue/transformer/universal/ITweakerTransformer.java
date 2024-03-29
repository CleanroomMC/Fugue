package com.cleanroommc.fugue.transformer.universal;

import com.cleanroommc.fugue.Fugue;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class ITweakerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            for (CtMethod ctMethod : cc.getDeclaredMethods()) {
                ctMethod.instrument(new ExprEditor() {
                    @Override
                    public void edit(MethodCall m) throws CannotCompileException {
                        if (m.getMethodName().equals("toURI")) {
                            m.replace(
                                    """
                                            {
                                                $_ = com.cleanroommc.fugue.helper.HookHelper#toURI($0);
                                            }
                                            """);
                        }
                    }
                });
            }
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
