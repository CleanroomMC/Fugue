package com.cleanroommc.fugue.transformer.universal;

import com.cleanroommc.fugue.common.Fugue;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class ITweakerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            ExprEditor editor = new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("toURI")) {
                        m.replace(
                                """
                                        {
                                            $_ = com.cleanroommc.fugue.helper.HookHelper#toURI($0);
                                        }
                                        """
                        );
                    }
                }
            };
            cc.instrument(editor);
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
