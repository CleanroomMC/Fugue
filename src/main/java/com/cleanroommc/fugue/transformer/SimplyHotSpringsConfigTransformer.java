package com.cleanroommc.fugue.transformer;

import com.cleanroommc.fugue.common.Fugue;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class SimplyHotSpringsConfigTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getClassInitializer().instrument(new ExprEditor(){
                public void edit(NewExpr e) throws CannotCompileException {
                    if (e.getClassName().equals("it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap")) {
                        e.replace("$_ = $proceed(255, 0.75F);");
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
