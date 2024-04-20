package com.cleanroommc.fugue.config;

import net.minecraftforge.common.config.Config;

public class ModPatchConfig {
    @Config.Name("Enable Ender Core Patch")
    public boolean enableEnderCore = true;
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
    @Config.Name("Enable Patch For OpenFM/OpenDisks/OpenSecurity")
    public boolean enableOpenAddons = true;
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
    public boolean enableCustomNPC = true;
    @Config.Name("Enabel Survivial Inc. Patch")
    public boolean enableSurvivialInc = true;
    @Config.Name("Enable Water Power Patch")
    public boolean enableWaterPower = true;
    @Config.Name("Enable Subaquatic Patch")
    public boolean enableSubaquatic = true;
    @Config.Name("Enable Nothirium Patch")
    public boolean enableNothirium = true;
}
