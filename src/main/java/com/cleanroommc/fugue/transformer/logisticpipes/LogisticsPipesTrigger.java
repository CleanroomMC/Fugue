package com.cleanroommc.fugue.transformer.logisticpipes;

import com.cleanroommc.fugue.common.Fugue;
import com.cleanroommc.fugue.config.FugueConfig;
import net.minecraft.launchwrapper.Launch;
import top.outlands.foundation.IExplicitTransformer;

public class LogisticsPipesTrigger implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        Fugue.LOGGER.info("Loading LP classes...");
        try {
            for (String clazz : FugueConfig.LPPreloadConfig.classList) {
                Launch.classLoader.findClass(clazz);
            }
        } catch (ClassNotFoundException ignored) {}
        return bytes;
    }
}
