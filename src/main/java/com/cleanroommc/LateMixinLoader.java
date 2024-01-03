package com.cleanroommc;

import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LateMixinLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Arrays.asList("fugue.mixin.charset.json", "xaeroplus.mixin.charset.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        switch (mixinConfig) {
            case "fugue.mixin.charset.json":
                return Loader.isModLoaded("charset");
            case "xaeroplus.mixin.charset.json":
                return Loader.isModLoaded("xaeroplus");
            default:
                return ILateMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
                
        }
    }
    
}
