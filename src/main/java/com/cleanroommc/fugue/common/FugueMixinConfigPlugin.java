package com.cleanroommc.fugue.common;

import com.cleanroommc.fugue.config.FugueConfig;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.Config;

import java.util.List;
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
            case "codechickenlib" -> Launch.classLoader.isClassExist("codechicken.lib.CodeChickenLib") && !Launch.classLoader.isClassExist("codechicken.lib.Reference") && FugueConfig.modPatchConfig.enableCCL;
            case "minecraftmultipartcbe" -> Loader.isModLoaded("minecraftmultipartcbe") && FugueConfig.modPatchConfig.enableMultiPart;
            case "projectred_core" -> Loader.isModLoaded("projectred-core") && FugueConfig.modPatchConfig.enablePR;
            case "solarflux" -> Loader.isModLoaded("solarflux") && FugueConfig.modPatchConfig.enableSolarFlux;
            case "custommainmenu" -> Loader.isModLoaded("custommainmenu") && FugueConfig.modPatchConfig.enableCMM;
            case "hammercore" -> Loader.isModLoaded("hammercore") && FugueConfig.modPatchConfig.enableHammerCore;
            case "gregtech" -> Loader.isModLoaded("gregtech") && FugueConfig.modPatchConfig.enableGTCEU && !Fugue.isModNewerThan("gregtech", "2.8.7");
            case "mcjty" -> Loader.isModLoaded("mcjtylib_ng") && FugueConfig.modPatchConfig.enableMcjty;
            case "howlingmoon" -> Loader.isModLoaded("howlingmoon") && FugueConfig.modPatchConfig.enableHowlingMoon;
            case "customnpcs" -> Loader.isModLoaded("customnpcs") && FugueConfig.modPatchConfig.enableCustomNPC;
            case "waterpower" -> Loader.isModLoaded("waterpower") && FugueConfig.modPatchConfig.enableWaterPower;
            case "subaquatic" -> Loader.isModLoaded("subaquatic") && FugueConfig.modPatchConfig.enableSubaquatic;
            case "astralsorcery" -> Loader.isModLoaded("astralsorcery") && FugueConfig.modPatchConfig.enableAstralSorcery;
            case "armourers_workshop" -> Loader.isModLoaded("armourers_workshop") && FugueConfig.modPatchConfig.enableArmourersWorkshop;
            case "mage" -> Loader.isModLoaded("mage") && FugueConfig.modPatchConfig.enableMage;
            case "extrautils2" -> Loader.isModLoaded("extrautils2") && FugueConfig.modPatchConfig.enableExtraUtilities;
            case "incontrol" -> Loader.isModLoaded("incontrol") && FugueConfig.modPatchConfig.enableInControl;
            case "refinedstorage" -> Loader.isModLoaded("refinedstorage") && FugueConfig.modPatchConfig.enableMoreRefinedStorage && Fugue.isModNewerThan("refinedstorage", "2.0.0");
            case "jei" -> Loader.isModLoaded("jei") && FugueConfig.modPatchConfig.enableHEI && Fugue.isModNewerThan("jei", "4.17.0");
            case "infinitylib" -> Loader.isModLoaded("infinitylib") && FugueConfig.modPatchConfig.enableInfLib;
            case "carryon" -> Loader.isModLoaded("carryon") && FugueConfig.modPatchConfig.enableCarryon;
            case "litematica" -> Loader.isModLoaded("litematica") && FugueConfig.modPatchConfig.enableLitematica;
            case "betterrecords" -> Loader.isModLoaded("betterrecords") && FugueConfig.modPatchConfig.enableBetterRecords;
            case "refinedrelocation" -> Loader.isModLoaded("refinedrelocation") && FugueConfig.modPatchConfig.enableRefinedRelocation2Patch;
            case "unilib" -> Loader.isModLoaded("unilib") && FugueConfig.modPatchConfig.enableUnilib;
            case "funkylocomotion" -> Loader.isModLoaded("funkylocomotion") && FugueConfig.modPatchConfig.enableFunkyLocomotion;
            case "patchouli" -> Loader.isModLoaded("patchouli") && FugueConfig.modPatchConfig.enablePatchouli;
            case "reccomplex" -> Loader.isModLoaded("reccomplex") && FugueConfig.modPatchConfig.enableRecurrentComplex;
            case "lockdown" -> Loader.isModLoaded("lockdown") && FugueConfig.modPatchConfig.enableLockdown;
            case "dissolution" -> Loader.isModLoaded("dissolution") && FugueConfig.modPatchConfig.enableDissolution;
            case "wirelessutils" -> Loader.isModLoaded("wirelessutils") && FugueConfig.modPatchConfig.enableWirelessUtilities;
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
