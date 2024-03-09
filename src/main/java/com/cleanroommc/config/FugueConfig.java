package com.cleanroommc.config;

import com.cleanroommc.Reference;
import net.minecraftforge.common.config.Config;

@Config(modid = Reference.MOD_ID)
public class FugueConfig {
    @Config.RequiresMcRestart
    @Config.Name("Enable Ender Core Patch")
    public static boolean enableEnderCore = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable Advanced Rocketry Patch")
    public static boolean enableAR = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable Shoulder Surfing Reloaded Patch")
    public static boolean enableShoulderSurfing = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable Splash Animation Patch")
    public static boolean enableSA = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable TickCentral Patch")
    public static boolean enableTickCentral = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable Logistics Pipes Patch")
    public static boolean enableLP = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable Patch For OpenFM/OpenDisks/OpenSecurity")
    public static boolean enableOpenAddons = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable Enchantment Control Patch")
    public static boolean enableEC = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable Charset lib Patch")
    public static boolean enableCharset = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable Code Chicken Lib Patch")
    public static boolean enableCCL = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable Custom Main Menu Patch")
    public static boolean enableCMM = true;
    @Config.Comment("This gtceu patch is temporary. An official fix is pending.")
    @Config.RequiresMcRestart
    @Config.Name("Enable GregTechCE Unofficial Patch")
    public static boolean enableGTCEU = true;
    @Config.Comment("From author of Fugue: I hate this mod.")
    @Config.RequiresMcRestart
    @Config.Name("Enable HammerCore Patch")
    public static boolean enableHammerCore = true;
    @Config.Comment({
            "This patch is for CB Multipart (previously ForgeMultiPart) from covers1624, MrTJP and ChickenBones.",
            "MCMultiPart is another different mod!"
    })
    @Config.RequiresMcRestart
    @Config.Name("Enable CB Multipart Patch")
    public static boolean enableMultiPart = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable Project Red Patch")
    public static boolean enablePR = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable Solar Flux Reborn Patch")
    public static boolean enableSolarFlux = true;
    @Config.RequiresMcRestart
    @Config.Name("Enable XaeroPlus Patch")
    public static boolean enableXP = true;
    public static class GeneralPatches {

    }
}
