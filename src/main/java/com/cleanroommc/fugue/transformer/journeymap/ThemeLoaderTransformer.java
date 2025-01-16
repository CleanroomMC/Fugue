package com.cleanroommc.fugue.transformer.journeymap;

import com.cleanroommc.fugue.common.Fugue;
import com.google.common.io.Files;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class ThemeLoaderTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getDeclaredMethod("preloadCurrentTheme").instrument(new ExprEditor(){
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("fileTreeTraverser")) {
                        m.where().setBody("$_ = com.google.common.io.Files#fileTraverser();");
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
