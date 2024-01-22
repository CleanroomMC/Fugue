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
                "fugue.mixin.projectred-core.json",
                "fugue.mixin.solarflux.json",
                "fugue.mixin.custommainmenu.json",
                "fugue.mixin.hammercore.json",
                "fugue.mixin.gregtech.json"
        );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return switch (mixinConfig) {
            case "fugue.mixin.charset.json" -> Loader.isModLoaded("charset");
            case "fugue.mixin.xaeroplus.json" -> Loader.isModLoaded("xaeroplus");
            case "fugue.mixin.codechickenlib.json" -> Loader.isModLoaded("codechickenlib");
            case "fugue.mixin.minecraftmultipartcbe.json" -> Loader.isModLoaded("minecraftmultipartcbe");
            case "fugue.mixin.projectred-core.json" -> Loader.isModLoaded("projectred-core");
            case "fugue.mixin.solarflux.json" -> Loader.isModLoaded("solarflux");
            case "fugue.mixin.custommainmenu.json" -> Loader.isModLoaded("custommainmenu");
            case "fugue.mixin.hammercore.json" -> Loader.isModLoaded("hammercore");
            case "fugue.mixin.gregtech.json" -> Loader.isModLoaded("gregtech");
            default -> ILateMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
        };
    }
    
}
