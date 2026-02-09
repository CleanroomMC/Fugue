package com.cleanroommc.fugue.transformer.heavy_fallings;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class AsmTransformerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            cc.getDeclaredMethod("rearrangeThisAfterDeobfuscation").setBody("return $1;");
            cc.addMethod(CtMethod.make("public int getPriority() {return 500;}", cc));
            bytes = cc.toBytecode();
            cc.defrost();
        } catch (Throwable t) {
            Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
        }
        return bytes;
    }
}
