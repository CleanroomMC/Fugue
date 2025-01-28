package com.cleanroommc.fugue.transformer.offlineskins;

import com.cleanroommc.fugue.common.Fugue;
import javassist.ClassPool;
import javassist.CtClass;
import net.minecraft.launchwrapper.Launch;
import top.outlands.foundation.IExplicitTransformer;

import java.io.ByteArrayInputStream;
/**
 * Targets :
 *      lain.mods.skins.init.forge.asm.ObfHelper
 */
public class ObfHelperTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        if (Launch.classLoader.isClassExist("lain.mods.skins.init.forge.asm.ObfHelper")) {
            try {
                CtClass cc = ClassPool.getDefault().makeClass(new ByteArrayInputStream(bytes));
                cc.getDeclaredMethod("transform")
                .setBody("{}");
                bytes = cc.toBytecode();
            } catch (Throwable t) {
                Fugue.LOGGER.error("Exception {} on {}", t, this.getClass().getSimpleName());
            }
        }
        return bytes;
    }
}