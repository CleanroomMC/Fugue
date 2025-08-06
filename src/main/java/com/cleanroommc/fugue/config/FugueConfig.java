package com.cleanroommc.fugue.config;

import com.cleanroommc.fugue.Reference;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Config(modid = Reference.MOD_ID, name = Reference.MOD_ID)
@Config.RequiresMcRestart
public class FugueConfig {
    @Config.Comment(
            """
            Fix and patches for certain mods.
            WARNING: Enable too much patches may lower performance.
            If you are a pack maker, just enable what you need.
            """)
    public static ModPatchConfig modPatchConfig = new ModPatchConfig();

    @Config.Comment(
            """
            About *static final field has no write access*
            Field.set() and Field.get() may throw exceptions in newer Java when trying to handle final fields.
            The few options remain are Unsafe or JNI.
            Classes in this list will be used as transform targets.
            Any Field related reflection calls will be redirected to Unsafe, so it wouldn't crash anymore.""")
    @Config.Name("Reflection Patch Target List")
    public static String[] reflectionPatchTargets = new String[] {
        "quaternary.botaniatweaks.modules.botania.block.BotaniaRegistryReplacements",
        "pl.asie.foamfix.client.deduplicator.Deduplicator",
        "com.fantasticsource.tools.ReflectionTool",
        "lumien.custombackgrounds.CustomBackgrounds",
        "com.fantasticsource.noadvancements.NoAdvancements",
        "com.codetaylor.mc.athenaeum.util.Injector",
        "epicsquid.mysticallib.hax.Hax",
        "epicsquid.gadgetry.core.hax.Hax",
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
        "com.noobanidus.variegated.compat.vanilla.handlers.MansionBiomeTypesHandler",
        "youyihj.zenutils.ZenUtils",
        "com.codetaylor.mc.athenaeum.util.Injector",
        "org.valkyrienskies.mod.common.ValkyrienSkiesMod",
        "com.legacy.lostaether.client.LostClientEvents",
        "com.noobanidus.variegated.compat.bloodmagic.handlers.HellfireSpeed",
        "ic2.core.util.ReflectionUtil",
        "net.arsenalnetwork.betterhud.h",
        "com.github.alexthe666.iceandfire.entity.EntitySnowVillager",
        "betterwithmods.util.ReflectionLib",
        "sedridor.B3M.ClientProxy",
        "com.ferreusveritas.unifine.ThermalDynamicsActive",
        "com.kirdow.itemlocks.util.reflect.ReflectClass",
        "eos.moe.dragoncore.za",
        "com.mcmoddev.lib.init.Items",
        "eos.moe.dragoncore.pn",
        "tragicneko.tragicmc.TragicMC",
        "energon.srpholiday.client.inject.render.SRPHReflect",
        "maxhyper.dynamictreestbl.compat.RegistryReplacements",
        "com.github.alexthe666.iceandfire.event.EventNewMenu",
    };

    @Config.Comment(
            """
            Mods like Apotheosis is casting AppClassLoader to URLClassLoader for getting its URLs.
            This will crash in newer Java, because AppClassLoader is no longer a URLClassLoader.
            Targets class here will be patched to new method we provide.""")
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
            Target classes here will be patched to use a helper method we provide.""")
    @Config.Name("New Script Engine Patch Target List")
    public static String[] scriptEngineTargets = new String[] {
        "nc.util.I18nHelper",
        "tk.zeitheron.solarflux.api.SolarScriptEngine",
        "com.github.tartaricacid.touhoulittlemaid.proxy.CommonProxy",
        "tk.zeitheron.expequiv.api.js.JSExpansion",
        "nuparu.sevendaystomine.proxy.CommonProxy",
    };

    @Config.Comment(
            """
            Java 8's UUID creation is flawed. It allow invalid UUIDs to be created.
            This was fixed in later Java, but old mods still need a solution.
            Target classes here will be patched to use a helper method we provide.""")
    @Config.Name("UUID Patch Target List")
    public static String[] UUIDTargets = new String[] {
        "com.Shultrea.Rin.Utility_Sector.HurtPatchHandler",
        "tk.zeitheron.solarflux.items.ItemEfficiencyUpgrade",
        "tk.zeitheron.solarflux.items.ItemTransferRateUpgrade",
        "tk.zeitheron.solarflux.items.ItemCapacityUpgrade",
        "iblis.player.SharedIblisAttributes",
        "com.Shultrea.Rin.Utility_Sector.LivingAttackFixerHandler",
    };

    @Config.Comment(
            """
            Javax (Java EE) redirect targets.
            They are gone in newer Java, so we are redirecting them to a replacement.""")
    @Config.Name("Javax Patch Target List")
    public static String[] remapTargets = new String[] {
        "com.ldtteam.structurize.util.StructureUtils",
        "git.jbredwards.fluidlogged_api.api.asm.IASMPlugin",
        "net.silentchaos512.lib.config.ConfigBaseNew",
        "net.silentchaos512.lib.tile.SyncVariable",
        "appeng.me.GridStorage",
        "net.creeperhost.minetogether.misc.Callbacks",
        "com.matez.wildnature.util.IProxy",
        "com.matez.wildnature.proxy.ClientProxy",
        "com.matez.wildnature.proxy.ServerProxy",
        "vazkii.botania.common.core.helper.StringObfuscator",
        "net.silentchaos512.lib.tile.SyncVariable$Helper",
        "com.vicmatskiv.mw.ModernWarfareMod",
        "com.vicmatskiv.weaponlib.config.EntityEquipment",
        "com.vicmatskiv.weaponlib.config.package-info",
        "com.vicmatskiv.weaponlib.config.Gui",
        "com.vicmatskiv.weaponlib.config.Attachment",
        "com.vicmatskiv.weaponlib.config.ObjectFactory",
        "com.vicmatskiv.weaponlib.config.Explosions",
        "com.vicmatskiv.weaponlib.config.ConfigurationManager",
        "com.vicmatskiv.weaponlib.config.ConfigurationManager$Builder",
        "com.vicmatskiv.weaponlib.config.Ores",
        "com.vicmatskiv.weaponlib.config.Projectiles",
        "com.vicmatskiv.weaponlib.config.AIEntity",
        "com.vicmatskiv.weaponlib.config.Gun",
        "com.vicmatskiv.weaponlib.config.Configuration",
        "com.vicmatskiv.weaponlib.config.Ore",
        "com.vicmatskiv.weaponlib.config.AI",
        "top.seraphjack.simplelogin.server.storage.StorageProviderFile"
    };

    @Config.Comment(
            """
            Non-Update was gone with Security Manager.
            As a workaround, These targets will be banned from making connections with URL.openStream().
            If you don't need a proxy to access github, you could empty this setting.
            The Secret Room entry should be kept - the url now points to an 404 page which will crash the game.
            This may block more connection than update checks, so if anything gone wrong please open an issue.""")
    @Config.Name("Connection Blocking List")
    public static String[] nonUpdateTargets = new String[] {
        "xxrexraptorxx.customizeddungeonloot.util.UpdateChecker$1",
        "com.nekokittygames.mffs.common.Versioninfo",
        "me.ichun.mods.ichunutil.common.thread.ThreadGetResources",
        "com.buuz135.industrial.proxy.CommonProxy",
        "micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore",
        "vazkii.quark.base.client.ContributorRewardHandler$ThreadContributorListLoader",
        "com.wynprice.secretroomsmod.handler.HandlerUpdateChecker",
    };

    @Config.Comment(
            """
            Foundation (the LaunchWrapper under Java 21+) comes with some ABI changes.
            If you got a crash says some methods/fields in LaunchClassLoader not found, that's the remapper you want.
            As a workaround, These targets will be redirected to new API.""")
    @Config.Name("Launch Wrapper API Change Patching List")
    public static String[] remapLWTargets = new String[] {
        "zone.rong.loliasm.common.crashes.ModIdentifier",
        "zone.rong.loliasm.LoliReflector",
        "com.charles445.rltweaker.asm.RLTweakerASM",
        "com.cleanroommc.groovyscript.sandbox.transformer.AsmDecompileHelper",
        "com.cleanroommc.modularui.core.ModularUICore",
        "openeye.logic.ModMetaCollector",
        "com.forgeessentials.core.preloader.asminjector.ASMUtil",
    };

    @Config.Comment(
            """
            sun.reflect.Reflection has moved to jdk.internal, and most of its features have replacements.
            As a workaround, These targets will be redirected to new dummy class.""")
    @Config.Name("sun.misc.Reflection Patching List")
    public static String[] remapReflectionTargets = new String[] {
        "quaternary.botaniatweaks.modules.botania.config.BotaniaConfig",
        "quaternary.botaniatweaks.modules.shared.lib.GeneratingFlowers$FlowerData",
        "quaternary.botaniatweaks.modules.shared.lib.NiceTryMap",
        "thedarkcolour.futuremc.compat.quark.QuarkCompat",
        "thedarkcolour.futuremc.world.gen.feature.BeeNestGenerator",
    };

    @Config.Comment(
            """
            ITweaker classes loaded in LCL will be defined in a different code source like file:jar:.
            This will cause errors like java.lang.IllegalArgumentException: URI is not hierarchical
            Add them to list could redirect their toURI() to a decent jar URL.
            """)
    @Config.Name("getCodeSource() Patching List")
    public static String[] getCodeSourcePatchTargets = new String[] {
        "pm.c7.pmr.tweaker.MixinLoadingTweaker",
        "customskinloader.forge.platform.IFMLPlatform$FMLPlatformInitializer",
        "pcl.opendisks.OpenDisksUnpack",
        "pcl.opensecurity.util.SoundUnpack",
        "pcl.OpenFM.misc.DepLoader",
        "pcl.OpenFM.misc.OFMDepLoader",
        "optifine.OptiFineClassTransformer",
        "snownee.minieffects.core.CoreMod",
        "com.replaymod.core.tweaker.ReplayModTweaker",
        "com.replaymod.core.LoadingPlugin",
        "zone.rong.loliasm.common.crashes.ModIdentifier",
        "online.flowerinsnow.greatscrollabletooltips.tweaker.GreatScrollableTooltipsTweaker",
        "com.wjx.kablade.mixin.KabladeMixinTweak",
        "eos.moe.dragoncore.tweaker.ForgePlugin",
        "advancedshader.core.Core",
        "com.forgeessentials.core.preloader.FELaunchHandler",
        "eos.moe.armourers.tweaker.ForgePlugin",
        "gg.essential.loader.stage2.jvm.ForkedJvm",
        "gg.essential.main.Bootstrap"
    };

    @Config.Comment(
            """
            Used when mouse wheel related operation being weird.
            Classes in this list will get their Mouse.getDWheel() and Mouse.getEventDWheel() redirected.
            Consult Cleanroom developers before using it!
            """)
    @Config.Name("Mouse.getEventDWheel() Patching List")
    public static String[] mouseWheelPatchTargets = new String[] {
        "mekanism.client.ClientTickHandler",
        "journeymap.client.ui.fullscreen.Fullscreen",
        "xaero.map.gui.ScreenBase",
        "xaero.map.gui.GuiMap",
        "betterquesting.api2.client.gui.GuiContainerCanvas",
        "betterquesting.api2.client.gui.GuiScreenCanvas",
        "yalter.mousetweaks.MouseState",
        "yalter.mousetweaks.SimpleMouseState",
        "com.feed_the_beast.ftblib.lib.gui.GuiWrapper",
        "com.feed_the_beast.ftblib.lib.gui.GuiContainerWrapper",
        "com.github.terminatornl.laggoggles.client.gui.GuiProfile",
    };

    @Config.Comment("Use this when you encountered ClassCircularityError.")
    @Config.Name("Extra Transform Exclusion")
    public static String[] extraTransformExclusions = new String[] {
        "org.vivecraft.",
    };

    @Config.Comment(
            "Newer Java now has concurrent check in this method. Should be rare so normally you don't need to modify this.")
    @Config.Name("computeIfAbsent Patching List")
    public static Map<String, String> computeIfAbsentTargets = new HashMap<>() {
        {
            put(
                    "hellfirepvp.astralsorcery.common.constellation.perk.attribute.PerkAttributeType",
                    "onApply|onRemove|hasTypeApplied");
            put("com.rwtema.extrautils2.backend.XUBlockStatic$3", "getQuads");
            put("mcjty.incontrol.rules.RuleCache$CachePerWorld", "count");
            put("com.lordmau5.repack.net.covers1624.model.LayeredTemplateModel$ModelBlockWrapper", "load");
            put("com.infinityraider.infinitylib.render.block.BakedInfBlockModel", "getQuads");
            put("net.blay09.mods.refinedrelocation.block.BlockSortingConnector", "getBoundingBox");
        }
    };

    @Config.Comment(
            "Guava now use a new method with a different desc. Should be rare so normally you don't need to modify this.")
    @Config.Name("computeIfAbsent Patching List")
    public static Map<String, String> addFutureCallbackTargets = new HashMap<>() {
        {
            put("moe.plushie.armourers_workshop.common.library.global.GlobalSkinLibraryUtils", "getUserInfo");
            put("moe.plushie.armourers_workshop.common.library.global.task.GlobalTask", "createTask|createTaskAndRun");
            put("com.rwtema.extrautils2.transfernodes.TileIndexer", "reload");
            put("com.rwtema.extrautils2.transfernodes.TransferNodeEnergy", "update");
            put("fi.dy.masa.litematica.render.schematic.ChunkRenderWorkerLitematica", "processTask");
            put("com.replaymod.simplepathing.gui.GuiPathing", "keyframeRepoButtonPressed|loadEntityTracker");
            put("com.replaymod.simplepathing.gui.GuiPathing$10", "run");

        }
    };

    @Config.Comment(
            """
            Target field's final modifier will be removed. No checks will be preformed before removal.
            All fields with same name will be targeted.
            Format: S:"foo.bar.classname"=field1|filed2""")
    @Config.Name("Final Fields Patching List")
    public static Map<String, String> finalRemovingTargets = new HashMap<>() {
        {
            // VintageFix
            put("net.minecraftforge.event.terraingen.BiomeEvent$BiomeColor", "originalColor");
            // Snow Real Magic
            put("net.minecraft.item.ItemBlock", "field_150939_a"); // block
            // EntityDistance
            put("net.minecraft.client.gui.GuiOptions", "field_146441_g|field_146443_h"); // lastScreen|settings
            put(
                    "net.minecraft.entity.EntityTracker",
                    "field_72793_b|field_72794_c|field_72795_a"); // entries|trackedEntityHashTable|world
            put(
                    "net.minecraft.entity.EntityTrackerEntry",
                    "field_73130_b|field_73132_a|field_187262_f|field_73131_c|field_73143_t"); // range|trackedEntity|maxRange|updateFrequency|sendVelocityUpdates
            put("net.minecraft.world.World", "field_72996_f"); // loadedEntityList
            put("net.minecraft.client.renderer.EntityRenderer", "field_78504_Q");
            put("meldexun.nothirium.api.renderer.chunk.ChunkRenderPass", "ALL"); // For nothirium + gtceu
            put("com.mrcrayfish.vehicle.crafting.VehicleRecipes", "RECIPES");
        }
    };
}
