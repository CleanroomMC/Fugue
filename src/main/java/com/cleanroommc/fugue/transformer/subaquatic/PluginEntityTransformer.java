package com.cleanroommc.fugue.transformer.subaquatic;

import com.cleanroommc.fugue.common.Fugue;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class PluginEntityTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getDeclaredMethod("transform").instrument(new ExprEditor() {
                @Override
                public void edit(NewExpr e) throws CannotCompileException {
                    if (e.getLineNumber() == 58 && e.getClassName().equals("org.objectweb.asm.tree.VarInsnNode")) {
                        e.replace(
                                """
                                $_ = $proceed(25, 26);
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
