package com.cleanroommc.fugue.transformer.nothirium;

import com.cleanroommc.fugue.common.Fugue;
import it.unimi.dsi.fastutil.objects.AbstractObject2ObjectMap;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class FreeSectorManagerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.instrument(new ExprEditor(){
                @Override
                public void edit(NewExpr e) throws CannotCompileException {
                    if (e.getClassName().equals("meldexun.reflectionutil.ReflectionField")) {
                        if (e.getLineNumber() == 97 || e.getLineNumber() == 62) {
                            e.replace("$_ = $proceed(\"it.unimi.dsi.fastutil.objects.AbstractObject2ObjectMap$BasicEntry\", \"key\", \"key\");");
                        }
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
