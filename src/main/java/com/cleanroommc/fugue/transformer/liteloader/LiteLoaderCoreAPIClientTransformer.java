package com.cleanroommc.fugue.transformer.liteloader;

import com.cleanroommc.fugue.common.Fugue;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.NewArray;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class LiteLoaderCoreAPIClientTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getClassInitializer().instrument(new ExprEditor(){
                @Override
                public void edit(NewArray a) throws CannotCompileException {
                    if (a.getLineNumber() == 48) {
                        a.where().setBody(
                                """
                                {
                                requiredTransformers = new String[] { "com.mumfrey.liteloader.transformers.event.EventProxyTransformer", "com.mumfrey.liteloader.launch.LiteLoaderTransformer", "com.mumfrey.liteloader.client.transformers.CrashReportTransformer" };
                                requiredDownstreamTransformers = new String[] { "com.mumfrey.liteloader.common.transformers.LiteLoaderPacketTransformer", "com.mumfrey.liteloader.transformers.event.json.ModEventInjectionTransformer" };
                                clientMixinConfigs = new String[] { "mixins.liteloader.client.json", "mixins.liteloader.client.optional.json" };
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
