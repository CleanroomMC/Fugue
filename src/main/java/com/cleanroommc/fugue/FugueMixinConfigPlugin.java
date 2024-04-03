package com.cleanroommc.fugue;

import com.cleanroommc.fugue.config.FugueConfig;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.Config;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FugueMixinConfigPlugin implements IMixinConfigPlugin {
    private Config config;
    @Override
    public void onLoad(String mixinPackage) {
        
    }

    @Override
    public void injectConfig(Config config) {
        this.config = config;
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return switch (mixinClassName.split("\\.")[4]) {
            case "charset" -> Loader.isModLoaded("charset") && FugueConfig.modPatchConfig.enableCharset;
            case "xaeroplus" -> Loader.isModLoaded("xaeroplus") && FugueConfig.modPatchConfig.enableXP;
            case "codechickenlib" -> Loader.isModLoaded("codechickenlib") && FugueConfig.modPatchConfig.enableCCL;
            case "minecraftmultipartcbe" -> Loader.isModLoaded("minecraftmultipartcbe") && FugueConfig.modPatchConfig.enableMultiPart;
            case "projectred_core" -> Loader.isModLoaded("projectred-core") && FugueConfig.modPatchConfig.enablePR;
            case "solarflux" -> Loader.isModLoaded("solarflux") && FugueConfig.modPatchConfig.enableSolarFlux;
            case "custommainmenu" -> Loader.isModLoaded("custommainmenu") && FugueConfig.modPatchConfig.enableCMM;
            case "hammercore" -> Loader.isModLoaded("hammercore") && FugueConfig.modPatchConfig.enableHammerCore;
            case "gregtech" -> Loader.isModLoaded("gregtech") && FugueConfig.modPatchConfig.enableGTCEU;
            case "mcjty" -> Loader.isModLoaded("mcjtylib_ng") && FugueConfig.modPatchConfig.enableMcjty;
            case "theasm" -> Launch.classLoader.isClassExist("zone.rong.loliasm.common.crashes.ModIdentifier") && FugueConfig.modPatchConfig.enableTheASM;
            case "howlingmoon" -> Loader.isModLoaded("howlingmoon") && FugueConfig.modPatchConfig.enableHowlingMoon;
            default -> true;
        };
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
