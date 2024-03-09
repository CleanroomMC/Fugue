package com.cleanroommc;

import com.cleanroommc.config.FugueConfig;
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
            case "fugue.mixin.charset.json" -> Loader.isModLoaded("charset") && FugueConfig.enableCharset;
            case "fugue.mixin.xaeroplus.json" -> Loader.isModLoaded("xaeroplus") && FugueConfig.enableXP;
            case "fugue.mixin.codechickenlib.json" -> Loader.isModLoaded("codechickenlib") && FugueConfig.enableCCL;
            case "fugue.mixin.minecraftmultipartcbe.json" -> Loader.isModLoaded("minecraftmultipartcbe") && FugueConfig.enableMultiPart;
            case "fugue.mixin.projectred-core.json" -> Loader.isModLoaded("projectred-core") && FugueConfig.enablePR;
            case "fugue.mixin.solarflux.json" -> Loader.isModLoaded("solarflux") && FugueConfig.enableSolarFlux;
            case "fugue.mixin.custommainmenu.json" -> Loader.isModLoaded("custommainmenu") && FugueConfig.enableCMM;
            case "fugue.mixin.hammercore.json" -> Loader.isModLoaded("hammercore") && FugueConfig.enableHammerCore;
            case "fugue.mixin.gregtech.json" -> Loader.isModLoaded("gregtech") && FugueConfig.enableGTCEU;
            default -> ILateMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
        };
    }
    
}
