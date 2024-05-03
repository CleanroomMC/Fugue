package com.cleanroommc.fugue.transformer.openmodlib;

import com.cleanroommc.fugue.common.Fugue;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class InjectorMethodVisitorTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getConstructors()[0].setBody(
                    """
                    super(589824, $2);
                    """
            );
            cc.getDeclaredMethod("visitInsn").setBody(
                    """
                    {
                        if ($1 == 177) {
                            visitVarInsn(25, 1);
                            visitVarInsn(23, 4);
                            visitMethodInsn(184, "openmods/renderer/PlayerRendererHookVisitor", "post", "(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V", false);
                            openmods.renderer.PlayerRendererHookVisitor.this.listener.onSuccess();
                        }
                        super.visitInsn($1);
                    }
                    """
            );
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
