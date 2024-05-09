package com.cleanroommc.fugue.common;

import com.cleanroommc.fugue.config.FugueConfig;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is made for Thaumic Speedup!
 */
@SuppressWarnings("deprecation")
public class FugueLateMixinLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        if (FugueConfig.modPatchConfig.fixTahumicSpeedup && Loader.isModLoaded("thaumicspeedup")) {
            List<String> configs = new ArrayList<>();
            configs.add("mixins.thaumicspeedup.json");
            if (Loader.isModLoaded("betterwithmods")) {
                configs.add("mixins.thaumicspeedup_bwmcompat.json");
            }
            return configs;
        }
        return List.of();
    }
}
