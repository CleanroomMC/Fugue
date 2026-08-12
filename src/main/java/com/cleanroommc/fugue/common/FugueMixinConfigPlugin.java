package com.cleanroommc.fugue.common;

import com.cleanroommc.fugue.config.FugueConfig;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class FugueMixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return switch (mixinClassName.split("\\.")[4]) {
            case "charset" -> FugueConfig.modPatchConfig.enableCharset;
            case "xaeroplus" -> FugueConfig.modPatchConfig.enableXP;
            case "codechickenlib" ->
                Launch.classLoader.isClassExist("codechicken.lib.CodeChickenLib")
                        && !Launch.classLoader.isClassExist("codechicken.lib.Reference")
                        && FugueConfig.modPatchConfig.enableCCL;
            case "minecraftmultipartcbe" -> FugueConfig.modPatchConfig.enableMultiPart;
            case "projectred_core" -> FugueConfig.modPatchConfig.enablePR;
            case "solarflux" -> FugueConfig.modPatchConfig.enableSolarFlux;
            case "custommainmenu" -> FugueConfig.modPatchConfig.enableCMM;
            case "hammercore" -> FugueConfig.modPatchConfig.enableHammerCore;
            case "mcjty" -> FugueConfig.modPatchConfig.enableMcjty;
            case "howlingmoon" -> FugueConfig.modPatchConfig.enableHowlingMoon;
            case "customnpcs" -> FugueConfig.modPatchConfig.enableCustomNPC;
            case "waterpower" -> FugueConfig.modPatchConfig.enableWaterPower;
            case "subaquatic" -> FugueConfig.modPatchConfig.enableSubaquatic;
            case "armourers_workshop" -> FugueConfig.modPatchConfig.enableArmourersWorkshop;
            case "mage" -> FugueConfig.modPatchConfig.enableMage;
            case "extrautils2" -> FugueConfig.modPatchConfig.enableExtraUtilities;
            case "refinedstorage" ->
                FugueConfig.modPatchConfig.enableMoreRefinedStorage && Fugue.isModNewerThan("refinedstorage", "2.0.0");
            case "carryon" -> FugueConfig.modPatchConfig.enableCarryon;
            case "betterrecords" -> FugueConfig.modPatchConfig.enableBetterRecords;
            case "unilib" -> FugueConfig.modPatchConfig.enableUnilib;
            case "funkylocomotion" -> FugueConfig.modPatchConfig.enableFunkyLocomotion;
            case "patchouli" -> FugueConfig.modPatchConfig.enablePatchouli;
            case "reccomplex" ->
                FugueConfig.modPatchConfig.enableRecurrentComplex
                        && (!mixinClassName.equals("com.cleanroommc.fugue.mixin.reccomplex.ParametersMixin")
                                || SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_22));
            case "lockdown" -> FugueConfig.modPatchConfig.enableLockdown;
            case "dissolution" -> FugueConfig.modPatchConfig.enableDissolution;
            case "glibysphysics" -> FugueConfig.modPatchConfig.enableGlibysPhysics;
            case "darktheme" -> FugueConfig.modPatchConfig.enableDarkTheme;
            case "creativecore" -> FugueConfig.modPatchConfig.enableCreativeCore;
            case "enderutils" -> FugueConfig.modPatchConfig.enableEnderUtilities;
            default -> true;
        };
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
