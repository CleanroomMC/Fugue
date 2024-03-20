package com.cleanroommc.fugue;

import com.cleanroommc.fugue.config.FugueConfig;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.List;
@SuppressWarnings("deprecation")
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
                "fugue.mixin.gregtech.json",
                "fugue.mixin.mcjty.json"
        );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return switch (mixinConfig) {
            case "fugue.mixin.charset.json" -> Loader.isModLoaded("charset") && FugueConfig.modPatchConfig.enableCharset;
            case "fugue.mixin.xaeroplus.json" -> Loader.isModLoaded("xaeroplus") && FugueConfig.modPatchConfig.enableXP;
            case "fugue.mixin.codechickenlib.json" -> Loader.isModLoaded("codechickenlib") && FugueConfig.modPatchConfig.enableCCL;
            case "fugue.mixin.minecraftmultipartcbe.json" -> Loader.isModLoaded("minecraftmultipartcbe") && FugueConfig.modPatchConfig.enableMultiPart;
            case "fugue.mixin.projectred-core.json" -> Loader.isModLoaded("projectred-core") && FugueConfig.modPatchConfig.enablePR;
            case "fugue.mixin.solarflux.json" -> Loader.isModLoaded("solarflux") && FugueConfig.modPatchConfig.enableSolarFlux;
            case "fugue.mixin.custommainmenu.json" -> Loader.isModLoaded("custommainmenu") && FugueConfig.modPatchConfig.enableCMM;
            case "fugue.mixin.hammercore.json" -> Loader.isModLoaded("hammercore") && FugueConfig.modPatchConfig.enableHammerCore;
            case "fugue.mixin.gregtech.json" -> Loader.isModLoaded("gregtech") && FugueConfig.modPatchConfig.enableGTCEU;
            case "fugue.mixin.mcjty.json" -> Loader.isModLoaded("mcjtylib_ng") && FugueConfig.modPatchConfig.enableMcjty;
            default -> ILateMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
        };
    }
    
}
