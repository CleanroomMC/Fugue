package com.cleanroommc.config;

import com.cleanroommc.Reference;
import net.minecraftforge.common.config.Config;

@Config(modid = Reference.MOD_ID, name = Reference.MOD_ID)
@Config.RequiresMcRestart
public class FugueConfig {
    @Config.Name("Enable Ender Core Patch")
    public static boolean enableEnderCore = true;
    @Config.Name("Enable Advanced Rocketry Patch")
    public static boolean enableAR = true;
    @Config.Name("Enable Shoulder Surfing Reloaded Patch")
    public static boolean enableShoulderSurfing = true;
    @Config.Name("Enable Splash Animation Patch")
    public static boolean enableSA = true;
    @Config.Name("Enable TickCentral Patch")
    public static boolean enableTickCentral = true;
    @Config.Name("Enable Logistics Pipes Patch")
    public static boolean enableLP = true;
    @Config.Name("Enable Patch For OpenFM/OpenDisks/OpenSecurity")
    public static boolean enableOpenAddons = true;
    @Config.Name("Enable Enchantment Control Patch")
    public static boolean enableEC = true;
    @Config.Name("Enable Charset lib Patch")
    public static boolean enableCharset = true;
    @Config.Name("Enable Code Chicken Lib Patch")
    public static boolean enableCCL = true;
    @Config.Name("Enable Custom Main Menu Patch")
    public static boolean enableCMM = true;
    @Config.Comment("This gtceu patch is temporary. An official fix is pending.")
    @Config.Name("Enable GregTechCE Unofficial Patch")
    public static boolean enableGTCEU = true;
    @Config.Comment("From author of Fugue: I hate this mod.")
    @Config.Name("Enable HammerCore Patch")
    public static boolean enableHammerCore = true;
    @Config.Comment({
            "This patch is for CB Multipart (previously ForgeMultiPart) from covers1624, MrTJP and ChickenBones.",
            "MCMultiPart is another different mod!"
    })
    @Config.Name("Enable CB Multipart Patch")
    public static boolean enableMultiPart = true;
    @Config.Name("Enable Project Red Patch")
    public static boolean enablePR = true;
    @Config.Name("Enable Solar Flux Reborn Patch")
    public static boolean enableSolarFlux = true;
    @Config.Name("Enable XaeroPlus Patch")
    public static boolean enableXP = true;
    @Config.Name("Enable TFC Medical Patch")
    public static boolean enableTFCMedical = true;

    @Config.Comment("""
            Field.set() and Field.get() may throw exceptions in newer Java when trying to handle final fields.
            The few options remain are Unsafe or JNI.
            Classes in this list will be used as transform targets.
            Any Field related reflection calls will be redirected to Unsafe, so it wouldn't crash anymore."""
    )
    @Config.Name("Reflection Patch Target List")
    public static String[] reflectionPatchTargets = new String[] {
            "epicsquid.mysticallib.hax.Hax",
            "vazkii.quark.world.feature.TreeVariants",
            "vazkii.quark.base.handler.OverrideRegistryHandler",
            "codechicken.lib.reflect.ReflectionManager",
            "com.tmtravlr.potioncore.PotionCoreEffects",
            "lumien.randomthings.recipes.ModRecipes",
            "net.malisis.core.renderer.font.MinecraftFont",
    };

    @Config.Comment(
            """
            Mods like Apotheosis is casting AppClassLoader to URLClassLoader for getting its URLs.
            This will crash in newer Java, because AppClassLoader is no longer a URLClassLoader.
            Targets class here will be patched to new method we provide."""
    )
    @Config.Name("Get URL Patch Target List")
    public static String[] getURLPatchTargets = new String[] {
            "shadows.CustomClassWriter",
            "lumien.randomthings.asm.CustomClassWriter",
            "shadows.squeezer.CustomClassWriter",
            "com.elytradev.wings.asm.RemappingClassWriter",
    };

    @Config.Comment(
            """
            ScriptEngine from javax has changed a lot in past Java versions.
            Many old code will end up getting an null in newer Java.
            Target classes here will be patched to use a helper method we provide."""
    )
    @Config.Name("New Script Engine Patch Target List")
    public static String[] scriptEngineTargets = new String[] {
            "tk.zeitheron.solarflux.api.SolarScriptEngine",
            "com.github.tartaricacid.touhoulittlemaid.proxy.CommonProxy",
    };

    @Config.Comment(
            """
            Java 8's UUID creation if flawed. It allow invalid UUIDs to be created.
            This was fixed in later Java, but old mods still need a solution.
            Target classes here will be patched to use a helper method we provide."""
    )
    @Config.Name("UUID Patch Target List")
    public static String[] UUIDTargets = new String[] {
            "com.Shultrea.Rin.Utility_Sector.HurtPatchHandler",
    };
    @Config.Comment(
            """
            Javax (Java EE) & sun.misc.Reflection redirect targets.
            They are gone in newer Java, so we are redirecting them to a replacement.""")
    public static String[] remapTargets = new String[] {
            "quaternary.botaniatweaks.modules.botania.config.BotaniaConfig",
            "quaternary.botaniatweaks.modules.shared.lib.GeneratingFlowers",
            "quaternary.botaniatweaks.modules.shared.lib.NiceTryMap",
            "com.ldtteam.structurize.util.StructureUtils",
    };

    @Config.Comment(
            """
            Non-Update was gone with Security Manager.
            As a workaround, These targets will be banned from making connections.""")
    public static String[] nonUpdateTargets = new String[] {
            "xxrexraptorxx.customizeddungeonloot.util.UpdateChecker$1",
    };

}
