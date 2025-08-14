package com.cleanroommc.fugue.config;

import net.minecraftforge.common.config.Config;

public class ModPatchConfig {
    @Config.Name("Enable Ender Core Patch")
    public boolean enableEnderCore = true;
    @Config.Comment({
            "This patch is only for Advanced Rocketry by zmaster587.",
            "Advanced Rocketry - Reworked by MarvinEckhardt doesn't need this!"
    })
    @Config.Name("Enable Advanced Rocketry Patch")
    public boolean enableAR = true;
    @Config.Name("Enable Shoulder Surfing Reloaded Patch")
    public boolean enableShoulderSurfing = true;
    @Config.Name("Enable Splash Animation Patch")
    public boolean enableSA = true;
    @Config.Name("Enable TickCentral Patch")
    public boolean enableTickCentral = true;
    @Config.Name("Enable Logistics Pipes Patch")
    public boolean enableLP = true;
    @Config.Name("Enable Enchantment Control Patch")
    public boolean enableEC = true;
    @Config.Name("Enable Charset lib Patch")
    public boolean enableCharset = true;
    @Config.Name("Enable Code Chicken Lib Patch")
    public boolean enableCCL = true;
    @Config.Name("Enable Custom Main Menu Patch")
    public boolean enableCMM = true;
    @Config.Comment("This gtceu patch is temporary. An official fix is pending.")
    @Config.Name("Enable GregTechCE Unofficial Patch")
    public boolean enableGTCEU = true;
    @Config.Comment("From author of Fugue: I hate this mod.")
    @Config.Name("Enable HammerCore Patch")
    public boolean enableHammerCore = true;
    @Config.Comment({
            "This patch is for CB Multipart (previously ForgeMultiPart) from covers1624, MrTJP and ChickenBones.",
            "MCMultiPart is another different mod!"
    })
    @Config.Name("Enable CB Multipart Patch")
    public boolean enableMultiPart = true;
    @Config.Name("Enable Project Red Patch")
    public boolean enablePR = true;
    @Config.Name("Enable Solar Flux Reborn Patch")
    public boolean enableSolarFlux = true;
    @Config.Name("Enable XaeroPlus Patch")
    public boolean enableXP = true;
    @Config.Name("Enable TFC Medical Patch")
    public boolean enableTFCMedical = true;
    @Config.Name("Enable Censored ASM Patch")
    public boolean enableTheASM = true;
    @Config.Name("Enable mcjtylib Patch")
    public boolean enableMcjty = true;
    @Config.Name("Enable ZeroCore (used by ExtremeReactor) Patch")
    public boolean enableZeroCore = true;
    @Config.Name("Enable SmoothFont Patch")
    @Config.Comment("It failed to patch FontRenderer for no fxxking reason.")
    public boolean enableSmoothFont = true;
    @Config.Name("Enable Custom Skin Loader Patch")
    public boolean enableCSL = true;
    @Config.Name("Enable XNet Patch")
    public boolean enableXNet = true;
    @Config.Name("Enable Howling Moon Patch")
    public boolean enableHowlingMoon = true;
    @Config.Name("Enable Custom NPCs Patch")
    @Config.Comment("Also patches Custom NPCs Unofficial, will log harmless errors")
    public boolean enableCustomNPC = true;
    @Config.Name("Enabel Survivial Inc. Patch")
    public boolean enableSurvivialInc = true;
    @Config.Name("Enable Water Power Patch")
    public boolean enableWaterPower = true;
    @Config.Name("Enable Subaquatic Patch")
    public boolean enableSubaquatic = true;
    @Config.Name("Enable Nothirium Patch")
    public boolean enableNothirium = true;
    @Config.Name("Enable GroovyScript Patch")
    public boolean enableGroovyScript = true;
    @Config.Name("Enable IC2C Extra Patch")
    public boolean enableIC2CE = true;
    @Config.Name("Enable SimplyHotSpring Patch")
    public boolean enableSimplyHotSpring = true;
    @Config.Name("Enable OpenModsLib Patch")
    public boolean enableOpenModsLib = true;
    @Config.Name("Enable ReplayMod Patch")
    public boolean enableReplayMod = true;
    @Config.Name("Fix Thaumic Speedup Loader")
    public boolean fixTahumicSpeedup = true;
    @Config.Name("Enable Armourer's Workshop Patch")
    public boolean enableArmourersWorkshop = true;
    @Config.Name("Enable Vampirism Patch")
    public boolean enableVampirism = true;
    @Config.Name("Enable MAGE (Graphical Tweaks) Patch")
    public boolean enableMage = true;
    @Config.Name("Enable Extra Utilities Patch")
    public boolean enableExtraUtilities = true;
    @Config.Name("Enable More Refined Storage Patch")
    public boolean enableMoreRefinedStorage = true;
    @Config.Name("Enable HEI Patch (temporary)")
    public boolean enableHEI = true;
    @Config.Name("Enable Better Formatting Code Patch")
    public boolean enableBetterFC = true;
    @Config.Name("Enable 5zig Patch")
    public boolean enable5zig = true;
    @Config.Name("Enable Ears Patch")
    @Config.Comment("This mod is packing a copy of ASM itself, wtf")
    public boolean enableEars = true;
    @Config.Name("Enable Colytra Patch")
    public boolean enableColytra = true;
    @Config.Name("Enable Crossbow(jbredwards) Patch")
    public boolean enableCrossbow = true;
    @Config.Name("Enable Patch to PolyForst mods")
    public boolean enablePolyForst = true;
    @Config.Name("Enable Dropt Patch")
    public boolean enableDropt = true;
    @Config.Name("Enable Carryon Patch")
    public boolean enableCarryon = true;
    @Config.Name("Enable Better Records Patch")
    public boolean enableBetterRecords = true;
    @Config.Name("Enable Aqua Acrobatics Patch")
    public boolean enableAquaAcrobatics = true;
    @Config.Name("Enable Integrated Proxy Patch")
    public boolean enableIntegratedProxyPatch = true;
    @Config.Name("Enable Thaumic Fixes Patch")
    public boolean enableThaumicFixesPatch = true;
    @Config.Name("Enable Erebus Fix Patch")
    public boolean enableErebusFixPatch = true;
    @Config.Name("Enable Uncrafting Blacklist Patch")
    public boolean enableUncraftingBlacklist = true;
    @Config.Name("Enable Calculator Patch")
    public boolean enableCalculator = true;
    @Config.Comment("This patches the CraftPresence spam log")
    @Config.Name("Enable Unilib Patch")
    public boolean enableUnilib = true;
    @Config.Name("Enable BetterPortals Patch")
    public boolean enableBetterPortals = true;
    @Config.Name("Enable Essential Patch")
    public boolean enableEssential = true;
    @Config.Comment("Only use with dj2addons 1.2.1.1 or lower")
    @Config.Name("Enable Divine Journey 2 Addons Patch")
    public boolean enableDivineJourney2Addons = true;
    @Config.Name("Enable LightAndShadow Patch")
    public boolean enableLightAndShadow = true;
    @Config.Name("Enable JourneyMap Patch")
    public boolean enableJourneyMap = true;
    @Config.Name("Enable OfflineSkins Patch")
    public boolean enableOfflineSkins = true;
    @Config.Name("Enable Techgun Patch")
    public boolean enableTechgun = true;
    @Config.Name("Enable Corpse Patch")
    public boolean enableCorpse = true;
    @Config.Name("Enable ScreenshotViewer Patch")
    public boolean enableScreenshotViewer = true;
    @Config.Name("Enable Worse Hurt Time Patch")
    @Config.Comment("Should be compatible with better hurt time")
    public boolean enableWorseHurtTime = true;
    @Config.Name("Enable Inventory Tweaks Patch")
    public boolean enableInvTweaks = true;
    @Config.Name("Enable Sound Device Options / More Sound Config Patch")
    public boolean enableMoreSoundConfig = true;
    @Config.Name("Enable NBT Peripheral Patch")
    public boolean enableNBTPeripheral = true;
    @Config.Name("Enable Farseek Patch")
    public boolean enableFarseek = true;
    @Config.Name("Enable KubeJS Patch")
    public boolean enableKubeJS = true;
    @Config.Name("Enable Funky Locomotion Patch")
    public boolean enableFunkyLocomotion = true;
    @Config.Name("Enable Patchouli Patch")
    public boolean enablePatchouli = true;
    @Config.Name("Enable Recurrent Complex Patch")
    public boolean enableRecurrentComplex = true;
    @Config.Name("Enable Lockdown Patch")
    public boolean enableLockdown = true;
    @Config.Name("Enable SAO UI Patch")
    public boolean enableSaoUI = true;
    @Config.Name("Enable Forge Endertech Patch")
    public boolean enableForgeEndertech = true;
    @Config.Name("Enable More Player Model Patch")
    public boolean enableMorePlayerModel = true;
    @Config.Name("Enable Dissolution Patch")
    public boolean enableDissolution = true;
    @Config.Name("Enable Unvalkyried Heavens Patch")
    public boolean enableUnvalkyriedHeavens = true;
    @Config.Name("Enable Mo'Bends Patch")
    public boolean enableMoBends = true;
    @Config.Name("Enable Aether II Patch")
    @Config.Comment("Using Apache http4 for game analytics. The URL is shutdown now so just removed it.")
    public boolean enableAetherII = true;
}
