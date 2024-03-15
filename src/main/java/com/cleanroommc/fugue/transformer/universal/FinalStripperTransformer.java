package com.cleanroommc.fugue.transformer.universal;

import com.cleanroommc.fugue.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

public class FinalStripperTransformer implements IExplicitTransformer {
    private final Map<String, String> targets;
    public FinalStripperTransformer(Map<String, String> targets) {
        this.targets = targets;
    }
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
            String[] fields = targets.get(cc.getName()).split("\\|");
            Arrays.stream(cc.getFields()).filter(ctField ->
                    Arrays.stream(fields).anyMatch(f ->
                            ctField.getName().equals(f)
                    )
            ).forEach(ctField2 ->
            {
                ctField2.setModifiers(ctField2.getModifiers() & ~Modifier.FINAL);
                Fugue.LOGGER.debug("Stripping final modifier of {} from {}", ctField2.getName(), ctField2.getDeclaringClass().getName());
            });
            bytes = cc.toBytecode();
        } catch (Throwable t) {
            Fugue.LOGGER.error(t);
        }
        return bytes;
    }
}
