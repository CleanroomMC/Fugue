package com.cleanroommc.fugue.config;

import com.cleanroommc.fugue.Reference;
import com.google.common.collect.Maps;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Config.Name("Enable Censored ASM Patch")
    public static boolean enableTheASM = true;

    @Config.Comment("""
            About *static final field has no write access*
            Field.set() and Field.get() may throw exceptions in newer Java when trying to handle final fields.
            The few options remain are Unsafe or JNI.
            Classes in this list will be used as transform targets.
            Any Field related reflection calls will be redirected to Unsafe, so it wouldn't crash anymore."""
    )
    @Config.Name("Reflection Patch Target List")
    public static String[] reflectionPatchTargets = new String[] {
            "com.fantasticsource.tools.ReflectionTool",
            "lumien.custombackgrounds.CustomBackgrounds",
            "com.fantasticsource.noadvancements.NoAdvancements",
            "com.codetaylor.mc.athenaeum.util.Injector",
            "epicsquid.mysticallib.hax.Hax",
            "vazkii.quark.world.feature.TreeVariants",
            "vazkii.quark.base.handler.OverrideRegistryHandler",
            "codechicken.lib.reflect.ReflectionManager",
            "com.tmtravlr.potioncore.PotionCoreEffects",
            "lumien.randomthings.recipes.ModRecipes",
            "net.malisis.core.renderer.font.MinecraftFont",
            "com.latmod.mods.projectex.ProjectEX",
            "org.cyclops.evilcraft.core.helper.obfuscation.ObfuscationHelpers",
            "xyz.phanta.tconevo.integration.IntegrationManager",
            "xyz.phanta.tconevo.util.JReflect",
            "xyz.phanta.tconevo.integration.astralsorcery.AstralHooksImpl",
            "xyz.phanta.tconevo.integration.draconicevolution.client.DraconicShieldHudHandler",
            "com.codetaylor.mc.athenaeum.util.Injector",
            "lumien.custombackgrounds.CustomBackgrounds",
            "com.noobanidus.variegated.compat.vanilla.handlers.MansionBiomeTypesHandler",
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
            "tk.zeitheron.expequiv.api.js.JSExpansion",
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
            "tk.zeitheron.solarflux.init.ItemsSF",
    };
    @Config.Comment(
            """
            Javax (Java EE) redirect targets.
            They are gone in newer Java, so we are redirecting them to a replacement.""")
    public static String[] remapTargets = new String[] {
            "com.ldtteam.structurize.util.StructureUtils",
            "git.jbredwards.fluidlogged_api.api.asm.IASMPlugin",
            "net.silentchaos512.scalinghealth.proxy.ScalingHealthCommonProxy",
            "appeng.me.GridStorage",
    };

    @Config.Comment(
            """
            Non-Update was gone with Security Manager.
            As a workaround, These targets will be banned from making connections with URL.openStream().
            If you don't need a proxy to access github, you could empty this setting. 
            This may block more connection than update checks, so if anything gone wrong please open an issue.""")
    public static String[] nonUpdateTargets = new String[] {
            "xxrexraptorxx.customizeddungeonloot.util.UpdateChecker$1",
            "com.nekokittygames.mffs.common.Versioninfo",
            "me.ichun.mods.ichunutil.common.thread.ThreadGetResources",
            "com.buuz135.industrial.proxy.CommonProxy",
            "micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore",
            "vazkii.quark.base.client.ContributorRewardHandler$ThreadContributorListLoader",
    };

    @Config.Comment(
            """
            Foundation comes with some ABI changes.
            If you got a crash says some methods/fields in LaunchClassLoader not found, that's the remapper you want.
            As a workaround, These targets will be redirected to new API.""")
    public static String[] remapLWTargets = new String[] {
            "zone.rong.loliasm.common.crashes.ModIdentifier",
            "zone.rong.loliasm.LoliReflector",
            "com.github.terminatornl.tickcentral.asm.Compatibility",
            "com.charles445.rltweaker.asm.RLTweakerASM",
            "com.cleanroommc.groovyscript.sandbox.transformer.AsmDecompileHelper",
            "com.cleanroommc.modularui.core.ModularUICore",
    };

    @Config.Comment(
            """
            sun.reflect.Reflection has moved to jdk.internal, and most of its features have replacements.
            As a workaround, These targets will be redirected to new dummy class.""")
    public static String[] remapReflectionTargets = new String[] {
            "quaternary.botaniatweaks.modules.botania.config.BotaniaConfig",
            "quaternary.botaniatweaks.modules.shared.lib.GeneratingFlowers$FlowerData",
            "quaternary.botaniatweaks.modules.shared.lib.NiceTryMap",
            "thedarkcolour.futuremc.compat.quark.QuarkCompat",
    };

    @Config.Comment(
            """
            Target field's final modifier will be removed. No checks will be preformed before removal.
            All fields with same name will be targeted.
            Format: S:"foo.bar.classname"=field1|filed2""")
    public static Map<String, String> finalRemovingTargets = Stream.of(new String[][] {
            //VintageFix
            {"net.minecraftforge.event.terraingen.BiomeEvent$BiomeColor", "originalColor"},
            //Snow Real Magic
            {"net.minecraft.item.ItemBlock", "field_150939_a"}, //block
            //EntityDistance
            {"net.minecraft.client.gui.GuiOptions", "field_146441_g|field_146443_h"}, //lastScreen|settings
            {"net.minecraft.entity.EntityTracker", "field_72793_b|field_72794_c|field_72795_a"}, //entries|trackedEntityHashTable|world
            {"net.minecraft.entity.EntityTrackerEntry", "field_73130_b|field_73132_a|field_187262_f|field_73131_c|field_73143_t"}, //range|trackedEntity|maxRange|updateFrequency|sendVelocityUpdates
            {"net.minecraft.world.World", "field_72996_f",}, //loadedEntityList
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

}
