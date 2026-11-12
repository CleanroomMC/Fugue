package com.cleanroommc.fugue.transformer.blockbuster;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import net.minecraft.launchwrapper.Launch;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;

public class BlockbusterTransformer implements IExplicitTransformer {

    private static final String CALLBACK = "lambda$new$3";
    private static final String DESCRIPTOR =
            "(Lnet/minecraft/util/ResourceLocation;)V";

    @Override
    public byte[] transform(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        CtClass cc = null;

        try {
            ClassPool pool = new ClassPool(true);
            pool.appendClassPath(
                    new LoaderClassPath(Launch.classLoader)
            );

            cc = pool.makeClass(new ByteArrayInputStream(bytes));

            CtMethod callback = cc.getDeclaredMethod(CALLBACK);

            if (!DESCRIPTOR.equals(
                    callback.getMethodInfo2().getDescriptor())) {
                Fugue.LOGGER.warn(
                        "Skipping Blockbuster patch: unexpected descriptor {}",
                        callback.getMethodInfo2().getDescriptor()
                );
                return bytes;
            }

            callback.insertBefore(
                    "{ if (this.scheme == null) return; }"
            );

            byte[] patched = cc.toBytecode();
            Fugue.LOGGER.info(
                    "Applied Blockbuster Snowstorm workaround (#741)"
            );
            return patched;

        } catch (Throwable t) {
            Fugue.LOGGER.error(
                    "Failed to patch Blockbuster Snowstorm (#741)", t
            );
            return bytes;

        } finally {
            if (cc != null) {
                cc.detach();
            }
        }
    }
}
