package com.cleanroommc.fugue.transformer.tickcentral;

import com.cleanroommc.fugue.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class PriorityAppendTransformer implements IExplicitTransformer {
    private static final Map<String, Integer> priority = new HashMap<>(){{
        put("com.github.terminatornl.tickcentral.asm.BlockTransformer", 1100);
        put("com.github.terminatornl.tickcentral.asm.ITickableTransformer", 1200);
        put("com.github.terminatornl.tickcentral.asm.EntityTransformer", 1400);
        put("com.github.terminatornl.tickcentral.asm.HubAPITransformer", 1500);
        put("net.minecraftforge.fml.common.asm.transformers.ModAPITransformer", 1600);
    }};
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            int p = priority.get(cc.getName());
            cc.addMethod(CtMethod.make("public int getPriority() {return " + p + ";}", cc));
            bytes = cc.toBytecode();
        }catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
