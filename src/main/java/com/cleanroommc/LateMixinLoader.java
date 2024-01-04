package com.cleanroommc;

import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.List;

public class LateMixinLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Arrays.asList(
                "fugue.mixin.charset.json", 
                "fugue.mixin.xaeroplus.json",
                "fugue.mixin.codechickenlib.json",
                "fugue.mixin.minecraftmultipartcbe.json",
                "fugue.mixin.projectred-core.json"
        );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        switch (mixinConfig) {
            case "fugue.mixin.charset.json":
                return Loader.isModLoaded("charset");
            case "fugue.mixin.xaeroplus.json":
                return Loader.isModLoaded("xaeroplus");
            case "fugue.mixin.codechickenlib.json":
                return Loader.isModLoaded("codechickenlib");
            case "fugue.mixin.minecraftmultipartcbe.json":
                return Loader.isModLoaded("minecraftmultipartcbe");
            default:
                return ILateMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
                
        }
    }
    
}
