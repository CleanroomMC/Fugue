package com.cleanroommc.fugue.common;

import com.cleanroommc.fugue.config.FugueConfig;
import com.cleanroommc.fugue.modifiers.DJ2AddonsFixer;
import com.cleanroommc.fugue.modifiers.DJ2PhaseFixer;
import com.cleanroommc.fugue.modifiers.IC2ExtraFixer;
import com.cleanroommc.fugue.transformer.advancedrocket.ClassTransformerTransformer;
import com.cleanroommc.fugue.transformer.betterfc.HK_LoaderTransformer;
import com.cleanroommc.fugue.transformer.betterhurttimer.DamageSpecialArmorMixinTransformer;
import com.cleanroommc.fugue.transformer.betterportals.MixinEntityRendererTransformer;
import com.cleanroommc.fugue.transformer.betterportals.ExtensionKtTransformer;
import com.cleanroommc.fugue.transformer.calculator.GuiInfoCalculatorTransformer;
import com.cleanroommc.fugue.transformer.codechickenlib.ClassHierarchyManagerTransformer;
import com.cleanroommc.fugue.transformer.colytra.EntityLivingBaseTransformer;
import com.cleanroommc.fugue.transformer.corpse.MessageOpenHistoryTransformer;
import com.cleanroommc.fugue.transformer.crossbow.TransformerEntityArrowTransformer;
import com.cleanroommc.fugue.transformer.customskinloader.ForgeTweakerTransformer;
import com.cleanroommc.fugue.transformer.dj2addons.DJ2AddonsCoreTransformer;
import com.cleanroommc.fugue.transformer.dropt.ValidatorAdapterFactoryTransformer;
import com.cleanroommc.fugue.transformer.ears.EarsASMTransformer;
import com.cleanroommc.fugue.transformer.enchantmentcontrol.EnumInputClassTransformer;
import com.cleanroommc.fugue.transformer.endercore.EnderCoreTransformerTransformer;
import com.cleanroommc.fugue.transformer.exu.FieldSetterTransformer;
import com.cleanroommc.fugue.transformer.essential.EssentialSetupTweakerTransformer;
import com.cleanroommc.fugue.transformer.essential.EssentialRelaunchTransformer;
import com.cleanroommc.fugue.transformer.essential.EssentialGlobalMouseOverrideTransformer;
import com.cleanroommc.fugue.transformer.essential.EssentialTransformerClearTransformer;
import com.cleanroommc.fugue.transformer.essential.EssentialTelemetryManagerTransformer;
import com.cleanroommc.fugue.transformer.farseek.FarseekClassTransformerTransformer;
import com.cleanroommc.fugue.transformer.fivezig.ClassTweakerTransformer;
import com.cleanroommc.fugue.transformer.forgeendertech.ForgeMainTransformer;
import com.cleanroommc.fugue.transformer.groovyscript.ASTTransformationCollectorCodeVisitorTransformer;
import com.cleanroommc.fugue.transformer.groovyscript.GroovyClassLoaderTransformer;
import com.cleanroommc.fugue.transformer.groovyscript.GroovyRunnerRegistryTransformer;
import com.cleanroommc.fugue.transformer.ic2ce.Ic2cExtrasLoadingPluginTransformer;
import com.cleanroommc.fugue.transformer.integrated_proxy.MixinLoaderTransformer;
import com.cleanroommc.fugue.transformer.inventorytweaks.InvTweaksHandlerShortcutsTransformer;
import com.cleanroommc.fugue.transformer.journeymap.FileHandlerTransformer;
import com.cleanroommc.fugue.transformer.journeymap.ThemeLoaderTransformer;
import com.cleanroommc.fugue.transformer.kubejs.KubeJSTransformer;
import com.cleanroommc.fugue.transformer.logisticpipes.*;
import com.cleanroommc.fugue.transformer.light_and_shadow.AsmTransformerTransformer;
import com.cleanroommc.fugue.transformer.loliasm.JavaFixesTransformer;
import com.cleanroommc.fugue.transformer.loliasm.LoliFMLCallHookTransformer;
import com.cleanroommc.fugue.transformer.loliasm.LoliReflectorTransformer;
import com.cleanroommc.fugue.transformer.loliasm.ModIdentifierTransformer;
import com.cleanroommc.fugue.transformer.mobends.ConnectionHelperTransformation;
import com.cleanroommc.fugue.transformer.moreplayermodels.ClientEventHandlerTransformer;
import com.cleanroommc.fugue.transformer.moresoundconfig.SoundDevicesTransformer;
import com.cleanroommc.fugue.transformer.nbtperipheral.LinkedTreeMapTransformer;
import com.cleanroommc.fugue.transformer.nothirium.BufferBuilderTransformer;
import com.cleanroommc.fugue.transformer.nothirium.FreeSectorManagerTransformer;
import com.cleanroommc.fugue.transformer.nothirium.MixinBufferBuilderTransformer;
import com.cleanroommc.fugue.transformer.nothirium.NothiriumClassTransformerTransformer;
import com.cleanroommc.fugue.transformer.offlineskins.ObfHelperTransformer;
import com.cleanroommc.fugue.transformer.offlineskins.OfflineskinsTransformersTransformer;
import com.cleanroommc.fugue.transformer.offlineskins.SetupTransformer;
import com.cleanroommc.fugue.transformer.openmodlib.InjectorMethodVisitorTransformer;
import com.cleanroommc.fugue.transformer.openmodlib.PlayerRendererHookVisitorTransformer;
import com.cleanroommc.fugue.transformer.polyfrost.LaunchWrapperTweakerTransformer;
import com.cleanroommc.fugue.transformer.replaymod.FuturesTransformer;
import com.cleanroommc.fugue.transformer.saoui.HudTransformer;
import com.cleanroommc.fugue.transformer.screenshot_viewer.ScreenshotViewerTransformer;
import com.cleanroommc.fugue.transformer.shouldersurfing.EntityPlayerRayTraceTransformer;
import com.cleanroommc.fugue.transformer.simplehotspring.SimplyHotSpringsConfigTransformer;
import com.cleanroommc.fugue.transformer.smoothfont.FontRendererTransformer;
import com.cleanroommc.fugue.transformer.splashanimation.SplashProgressTransformerTransformer;
import com.cleanroommc.fugue.transformer.subaquatic.PluginEntityTransformer;
import com.cleanroommc.fugue.transformer.subaquatic.SubaquaticIMTransformer;
import com.cleanroommc.fugue.transformer.survivalinc.ForgeASMInjectorTransformer;
import com.cleanroommc.fugue.transformer.techgun.TechgunsASMTransformerTransformer;
import com.cleanroommc.fugue.transformer.universal.RemoveMixinInitFromCotrTransformer;
import com.cleanroommc.fugue.transformer.tickcentral.*;
import com.cleanroommc.fugue.transformer.universal.*;
import com.cleanroommc.fugue.transformer.valkyrienskies.WorldPhysicsColliderTransformer;
import com.cleanroommc.fugue.transformer.vampirism.TConstructBloodConversionTransformer;
import com.cleanroommc.fugue.transformer.vampirism.WorldGenVampireOrchidTransformer;
import com.cleanroommc.fugue.transformer.xnet.EnergyConnectorSettingsTransformer;
import com.cleanroommc.fugue.transformer.zerocore.DisplayListTransformer;
import org.spongepowered.asm.mixin.transformer.Config;
import org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper;
import top.outlands.foundation.TransformerDelegate;
import top.outlands.foundation.boot.ActualClassLoader;

public class TransformerHelper {
    public static void registerTransformers() {

        if (FugueConfig.modPatchConfig.enableEnderCore) {
            TransformerDelegate.registerExplicitTransformer(
                    new EnderCoreTransformerTransformer(),
                    "com.enderio.core.common.transform.EnderCoreTransformer"
            );
        }
        if (FugueConfig.modPatchConfig.enableAR) {
            TransformerDelegate.registerExplicitTransformer(
                    new ClassTransformerTransformer(),
                    "zmaster587.advancedRocketry.asm.ClassTransformer"
            );
        }
        if (FugueConfig.modPatchConfig.enableShoulderSurfing) {
            TransformerDelegate.registerExplicitTransformer(
                    new EntityPlayerRayTraceTransformer(),
                    "com.teamderpy.shouldersurfing.asm.transformers.EntityPlayerRayTrace"
            );
        }
        if (FugueConfig.modPatchConfig.enableSA){
            TransformerDelegate.registerExplicitTransformer(
                    new SplashProgressTransformerTransformer(),
                    "pl.asie.splashanimation.core.SplashProgressTransformer"
            );
        }
        if (FugueConfig.modPatchConfig.enableTickCentral){
            TransformerDelegate.registerExplicitTransformer(
                    new InitializerTransformer(),
                    "com.github.terminatornl.laggoggles.tickcentral.Initializer"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new ClassSnifferTransformer(),
                    "com.github.terminatornl.tickcentral.api.ClassSniffer"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new CompatibilityTransformer(),
                    "com.github.terminatornl.tickcentral.asm.Compatibility"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new TickCentralTransformer(),
                    "com.github.terminatornl.tickcentral.TickCentral"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new PriorityAppendTransformer(),
                    "com.github.terminatornl.tickcentral.asm.BlockTransformer",
                    "com.github.terminatornl.tickcentral.asm.ITickableTransformer",
                    "com.github.terminatornl.tickcentral.asm.EntityTransformer",
                    "com.github.terminatornl.tickcentral.asm.HubAPITransformer",
                    "net.minecraftforge.fml.common.asm.transformers.ModAPITransformer"
            );
        }
        if (FugueConfig.modPatchConfig.enableLP){
            TransformerDelegate.registerExplicitTransformer(
                    new LogisticPipesHandlerTransformer(),
                    "logisticspipes.asm.mcmp.ClassBlockMultipartContainerHandler",
                    "logisticspipes.asm.td.ClassRenderDuctItemsHandler",
                    "logisticspipes.asm.td.ClassTravelingItemHandler"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new LogisticsClassTransformerTransformer(ActualClassLoader.class),
                    "logisticspipes.asm.LogisticsClassTransformer",
                    "logisticspipes.asm.LogisticsPipesClassInjector"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new LogisticsPipesClassInjectorTransformer(),
                    "logisticspipes.asm.LogisticsPipesClassInjector"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new defineClassTransformer(),
                    "logisticspipes.asm.wrapper.LogisticsWrapperHandler",
                    "logisticspipes.proxy.opencomputers.asm.ClassCreator"
            
            );
            TransformerDelegate.registerExplicitTransformer(
                    new LoadClassTransformer(),
                    "network.rs485.debug.OpenGLDebugger",
                    "logisticspipes.utils.StaticResolverUtil"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new StaticResolverUtilTransformer(),
                    "logisticspipes.utils.StaticResolverUtil"
            );
        }
        if (FugueConfig.modPatchConfig.enableEC){
            TransformerDelegate.registerExplicitTransformer(
                    new EnumInputClassTransformer(),
                    "austeretony.enchcontrol.common.core.EnumInputClass"
            );
        }
        if (FugueConfig.modPatchConfig.enableTheASM) {
            TransformerDelegate.registerExplicitTransformer(
                    new LoliReflectorTransformer(),
                    "zone.rong.loliasm.LoliReflector"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new JavaFixesTransformer(),
                    "zone.rong.loliasm.common.java.JavaFixes"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new LoliFMLCallHookTransformer(),
                    "zone.rong.loliasm.core.LoliFMLCallHook"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new ModIdentifierTransformer(),
                    "zone.rong.loliasm.common.crashes.ModIdentifier"
            );
        }
        if (FugueConfig.modPatchConfig.enableZeroCore) {
            TransformerDelegate.registerExplicitTransformer(
                    new DisplayListTransformer(),
                    "it.zerono.mods.zerocore.lib.client.render.DisplayList"
            );
        }
        if (FugueConfig.modPatchConfig.enableSmoothFont) {
            TransformerDelegate.registerExplicitTransformer(
                    new FontRendererTransformer(),
                    "net.minecraft.client.gui.FontRenderer"
            );
        }
        if (FugueConfig.modPatchConfig.enableCSL) {
            TransformerDelegate.registerExplicitTransformer(
                    new ForgeTweakerTransformer(),
                    "customskinloader.forge.ForgeTweaker"
            );
        }
        if (FugueConfig.modPatchConfig.enableXNet) {
            TransformerDelegate.registerExplicitTransformer(
                    new EnergyConnectorSettingsTransformer(),
                    "mcjty.xnet.apiimpl.energy.EnergyConnectorSettings"
            );
        }
        if (FugueConfig.modPatchConfig.enableCCL) {
            TransformerDelegate.registerExplicitTransformer(
                    new ClassHierarchyManagerTransformer(),
                    "codechicken.asm.ClassHierarchyManager"
            );
        }
        if (FugueConfig.modPatchConfig.enableSurvivialInc) {
            TransformerDelegate.registerExplicitTransformer(
                    new ForgeASMInjectorTransformer(),
                    "enginecrafter77.survivalinc.util.ForgeASMInjector"
            );
        }
        if (FugueConfig.modPatchConfig.enableSubaquatic) {
            TransformerDelegate.registerExplicitTransformer(
                    new PluginEntityTransformer(),
                    "git.jbredwards.subaquatic.mod.asm.plugin.vanilla.entity.PluginEntity"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new SubaquaticIMTransformer(),
                    "net.minecraft.world.gen.layer.GenLayer"
            );
        }
        if (FugueConfig.modPatchConfig.enableNothirium){
            TransformerDelegate.registerExplicitTransformer(
                    new NothiriumClassTransformerTransformer(),
                    "meldexun.nothirium.mc.asm.NothiriumClassTransformer"
            );
            MixinServiceLaunchWrapper.registerMixinClassTransformer(
                    new MixinBufferBuilderTransformer(),
                    "meldexun.nothirium.mc.mixin.vertex.MixinBufferBuilder"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new BufferBuilderTransformer(),
                    "net.minecraft.client.renderer.BufferBuilder"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new FreeSectorManagerTransformer(),
                    "meldexun.nothirium.util.FreeSectorManager$AVL",
                    "meldexun.nothirium.util.FreeSectorManager$RB"
            );
        }
        if (FugueConfig.modPatchConfig.enableGroovyScript) {
            TransformerDelegate.registerExplicitTransformer(
                    new GroovyClassLoaderTransformer(),
                    "groovy.lang.GroovyClassLoader"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new GroovyRunnerRegistryTransformer(),
                    "org.apache.groovy.plugin.GroovyRunnerRegistry"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new ASTTransformationCollectorCodeVisitorTransformer(),
                    "org.codehaus.groovy.transform.ASTTransformationCollectorCodeVisitor"
            );
        }
        if (FugueConfig.modPatchConfig.enableIC2CE) {
            Config.registerConfigModifier(
                    new IC2ExtraFixer(),
                    "mixins.ic2c_extras.json"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new Ic2cExtrasLoadingPluginTransformer(),
                    "trinsdar.ic2c_extras.asm.Ic2cExtrasLoadingPlugin"
            );
        }
        if (FugueConfig.modPatchConfig.enableSimplyHotSpring) {
            TransformerDelegate.registerExplicitTransformer(
                    new SimplyHotSpringsConfigTransformer(),
                    "connor135246.simplyhotsprings.util.SimplyHotSpringsConfig"
            );
        }
        if (FugueConfig.modPatchConfig.enableOpenModsLib) {
            TransformerDelegate.registerExplicitTransformer(
                    new PlayerRendererHookVisitorTransformer(),
                    "openmods.renderer.PlayerRendererHookVisitor"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new InjectorMethodVisitorTransformer(),
                    "openmods.renderer.PlayerRendererHookVisitor$InjectorMethodVisitor"
            );
        }
        if (FugueConfig.modPatchConfig.enableVampirism) {
            TransformerDelegate.registerExplicitTransformer(
                    new WorldGenVampireOrchidTransformer(),
                    "de.teamlapen.vampirism.biome.BiomeGenVampireForest$WorldGenVampireOrchid"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new TConstructBloodConversionTransformer(),
                    "de.teamlapen.vampirism_integrations.tconstruct.TConstructBloodConversion"
            );
        }
        if (FugueConfig.modPatchConfig.enableExtraUtilities) {
            TransformerDelegate.registerExplicitTransformer(
                    new FieldSetterTransformer(),
                    "com.rwtema.extrautils2.utils.datastructures.FieldSetter"
            );
        }
        if (FugueConfig.modPatchConfig.enableBetterFC) {
            TransformerDelegate.registerExplicitTransformer(
                    new HK_LoaderTransformer(),
                    "kpan.better_fc.asm.hook.HK_Loader"
            );
        }
        if (FugueConfig.modPatchConfig.enable5zig) {
            TransformerDelegate.registerExplicitTransformer(
                    new ClassTweakerTransformer(),
                    "eu.the5zig.mod.asm.ClassTweaker"
            );
        }
        if (FugueConfig.modPatchConfig.enableEars) {
            TransformerDelegate.registerExplicitTransformer(
                    new EarsASMTransformer(),
                    "com.unascribed.ears.asm.ImageBufferDownloadTransformer",
                    "com.unascribed.ears.asm.LayerElytraTransformer",
                    "com.unascribed.ears.asm.RenderPlayerTransformer",
                    "com.unascribed.ears.asm.ThreadDownloadImageDataTransformer",
                    "com.unascribed.ears.common.agent.mini.MiniTransformer",
                    "com.unascribed.ears.common.agent.mini.PatchContext",
                    "com.unascribed.ears.common.agent.mini.PatchContext$SearchResult"
            );
        }
        if (FugueConfig.modPatchConfig.enableColytra) {
            TransformerDelegate.registerExplicitTransformer(
                    new EntityLivingBaseTransformer(),
                    "net.minecraft.entity.EntityLivingBase"
            );
        }
        if (FugueConfig.modPatchConfig.enableCrossbow) {
            TransformerDelegate.registerExplicitTransformer(
                    new TransformerEntityArrowTransformer(),
                    "git.jbredwards.crossbow.mod.asm.transformer.TransformerEntityArrow"
            );
        }
        if (FugueConfig.modPatchConfig.enablePolyForst) {
            TransformerDelegate.registerExplicitTransformer(
                    new LaunchWrapperTweakerTransformer(),
                    "cc.polyfrost.oneconfig.loader.stage0.LaunchWrapperTweaker",
                    "cc.polyfrost.oneconfig.loader.OneConfigLoader"
            );
        }
        if (FugueConfig.modPatchConfig.enableDropt) {
            TransformerDelegate.registerExplicitTransformer(
                    new ValidatorAdapterFactoryTransformer(),
                    "com.codetaylor.mc.dropt.modules.dropt.rule.RuleLoader$ValidatorAdapterFactory"
            );
        }
        if (FugueConfig.modPatchConfig.enableIntegratedProxyPatch) {
            TransformerDelegate.registerExplicitTransformer(
                    new MixinLoaderTransformer(),
                    "com.shblock.integrated_proxy.mixin.MixinLoader"
            );
        }
        RemoveMixinInitFromCotrTransformer instance = new RemoveMixinInitFromCotrTransformer();
        if (FugueConfig.modPatchConfig.enableThaumicFixesPatch) {
            TransformerDelegate.registerExplicitTransformer(instance,
                    "com.seriouscreeper.thaumicfixes.core.ThaumicFixesLoadingPlugin"
            );
        }
        if (FugueConfig.modPatchConfig.enableErebusFixPatch) {
            TransformerDelegate.registerExplicitTransformer(instance,
                    "noobanidus.mods.erebusfix.core.EFLoadingPlugin"
            );
        }
        if (FugueConfig.modPatchConfig.enableUncraftingBlacklist) {
            TransformerDelegate.registerExplicitTransformer(instance,
                    "doomanidus.mods.uncraftingblacklist.core.UBLoadingPlugin"
            );
        }
        if (FugueConfig.modPatchConfig.enableCalculator) {
            TransformerDelegate.registerExplicitTransformer(
                    new GuiInfoCalculatorTransformer(),
                    "sonar.calculator.mod.client.gui.calculators.GuiInfoCalculator"
            );
        }
        if (FugueConfig.modPatchConfig.enableBetterPortals) {
            MixinServiceLaunchWrapper.registerMixinClassTransformer(
                    new MixinEntityRendererTransformer(),
                    "de.johni0702.minecraft.view.impl.mixin.MixinEntityRenderer_NoOF",
                    "de.johni0702.minecraft.view.impl.mixin.MixinEntityRenderer_OF"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new ExtensionKtTransformer(),
                    "de.johni0702.minecraft.betterportals.impl.transition.common.ExtensionsKt",
                    "de.johni0702.minecraft.betterportals.impl.common.ExtensionsKt",
                    "de.johni0702.minecraft.view.impl.common.ExtensionsKt"
            );
        }
        if (FugueConfig.modPatchConfig.enableEssential) {
            TransformerDelegate.registerExplicitTransformer(
                new EssentialSetupTweakerTransformer(),
                "gg.essential.loader.stage0.EssentialSetupTweaker",
                "gg.essential.loader.stage1.EssentialLoader",
                "gg.essential.loader.stage2.EssentialLoader",
                "gg.essential.main.Bootstrap"
            );
            TransformerDelegate.registerExplicitTransformer(
                new EssentialRelaunchTransformer(),
                "gg.essential.loader.stage2.relaunch.Relaunch"
            );
            TransformerDelegate.registerExplicitTransformer(
                new EssentialGlobalMouseOverrideTransformer(),
                "gg.essential.gui.overlay.OverlayManagerImpl$GlobalMouseOverride"
            );
            TransformerDelegate.registerExplicitTransformer(
                new EssentialTransformerClearTransformer(),
                "gg.essential.asm.compat.betterfps.BetterFpsTransformerWrapper",
                "gg.essential.asm.compat.PhosphorTransformer"
            
            );
            TransformerDelegate.registerExplicitTransformer(
                new EssentialTelemetryManagerTransformer(),
                "gg.essential.network.connectionmanager.telemetry.TelemetryManager"
            );
        }
        if (FugueConfig.modPatchConfig.enableDivineJourney2Addons) {
            Config.registerConfigModifier(
                    new DJ2AddonsFixer(),
                    "mixins.dj2addons.init.json"
            );
            Config.registerConfigModifier(
                    new DJ2PhaseFixer(),
                    "mixins.dj2addons.json"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new DJ2AddonsCoreTransformer(),
                    "org.btpos.dj2addons.core.DJ2AddonsCore"
            );
        }
        if (FugueConfig.modPatchConfig.enableLightAndShadow) {
            TransformerDelegate.registerExplicitTransformer(
                    new AsmTransformerTransformer(),
                    "kpan.light_and_shadow.asm.core.AsmTransformer"
            );
        }
        if (FugueConfig.modPatchConfig.enableJourneyMap) {
            TransformerDelegate.registerExplicitTransformer(
                    new ThemeLoaderTransformer(),
                    "journeymap.client.io.ThemeLoader"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new FileHandlerTransformer(),
                    "journeymap.client.io.FileHandler"
            );
        }
        if (FugueConfig.modPatchConfig.enableReplayMod) {
            TransformerDelegate.registerExplicitTransformer(
                    new FuturesTransformer(),
                    "com.replaymod.simplepathing.gui.GuiPathing",
                    "com.replaymod.simplepathing.gui.GuiPathing$10"
            );
        }
        if (FugueConfig.modPatchConfig.enableOfflineSkins) {
            TransformerDelegate.registerExplicitTransformer(
                    new ObfHelperTransformer(),
                    "lain.mods.skins.init.forge.asm.ObfHelper"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new OfflineskinsTransformersTransformer(),
                    "lain.mods.skins.init.forge.asm.ASMTransformer"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new SetupTransformer(),
                    "lain.mods.skins.init.forge.asm.Setup"
            );
        }
        if (FugueConfig.modPatchConfig.enableTechgun) {
            TransformerDelegate.registerExplicitTransformer(
                    new TechgunsASMTransformerTransformer(),
                    "techguns.core.TechgunsASMTransformer"
            );
        }
        if (FugueConfig.modPatchConfig.enableCorpse) {
            TransformerDelegate.registerExplicitTransformer(
                    new MessageOpenHistoryTransformer(),
                    "de.maxhenkel.corpse.net.MessageOpenHistory"
            );
        }
        if (FugueConfig.modPatchConfig.enableScreenshotViewer) {
            TransformerDelegate.registerExplicitTransformer(
                    new ScreenshotViewerTransformer(),
                    "io.github.lgatodu47.screenshot_viewer.ScreenshotViewer$ScreenshotViewerEvents"
            );
        }
        if (FugueConfig.modPatchConfig.enableWorseHurtTime) {
            MixinServiceLaunchWrapper.registerMixinClassTransformer(
                    new DamageSpecialArmorMixinTransformer(),
                    "arekkuusu.betterhurttimer.mixin.DamageSpecialArmorMixin"
            );
        }
        if (FugueConfig.modPatchConfig.enableInvTweaks) {
            TransformerDelegate.registerExplicitTransformer(
                    new InvTweaksHandlerShortcutsTransformer(),
                    "invtweaks.InvTweaksHandlerShortcuts"
            );
        }
        if (FugueConfig.modPatchConfig.enableMoreSoundConfig) {
            TransformerDelegate.registerExplicitTransformer(
                    new SoundDevicesTransformer(),
                    "ichttt.mods.moresoundconfig.SoundDevices"
            );
        }
        if (FugueConfig.modPatchConfig.enableNBTPeripheral) {
            TransformerDelegate.registerExplicitTransformer(
                    new LinkedTreeMapTransformer(),
                    "nl.makertim.nbtperipheral.cc.BlockStateUtil",
                    "nl.makertim.nbtperipheral.cc.CCUtil",
                    "nl.makertim.nbtperipheral.cc.NBTUtil"
            );
        }
        if (FugueConfig.modPatchConfig.enableFarseek) {
            TransformerDelegate.registerExplicitTransformer(
                    new FarseekClassTransformerTransformer(),
                    "farseek.core.FarseekClassTransformer"
            );
        }
        if (FugueConfig.modPatchConfig.enableKubeJS) {
            TransformerDelegate.registerExplicitTransformer(
                    new RemapTransformer(
                            new String[] { "jdk/nashorn/api/scripting/" },
                            new String[] { "org/openjdk/nashorn/api/scripting/" }
                    ),
                    "dev.latvian.kubejs.event.EventsJS",
                    "dev.latvian.kubejs.fluid.FluidStackJS",
                    "dev.latvian.kubejs.item.ingredient.IngredientJS",
                    "dev.latvian.kubejs.item.ItemStackJS",
                    "dev.latvian.kubejs.script.ScriptManager",
                    "dev.latvian.kubejs.server.KubeJSServerEventHandler",
                    "dev.latvian.kubejs.util.FunctionBinding",
                    "dev.latvian.kubejs.util.JsonUtilsJS",
                    "dev.latvian.kubejs.util.nbt.NBTBaseJS",
                    "dev.latvian.kubejs.util.UtilsJS"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new KubeJSTransformer(),
                    "dev.latvian.kubejs.KubeJS"
            );
        }
        if (FugueConfig.modPatchConfig.enableSaoUI) {
            TransformerDelegate.registerExplicitTransformer(
                    new HudTransformer(),
                    "com.tencao.saoui.themes.elements.Hud"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new RemapTransformer(
                            new String[] { "javax/xml/bind/" },
                            new String[] { "jakarta/xml/bind/" }
                    ),
                    "com.tencao.saoui.SAOCore",
                    "com.tencao.saoui.themes.ThemeLoader",
                    "com.tencao.saoui.themes.elements.Element",
                    "com.tencao.saoui.themes.elements.ElementGroup",
                    "com.tencao.saoui.themes.elements.GLHotbarItem",
                    "com.tencao.saoui.themes.elements.GLRectangle",
                    "com.tencao.saoui.themes.elements.GLString",
                    "com.tencao.saoui.themes.elements.Hud",
                    "com.tencao.saoui.themes.elements.package-info",
                    "com.tencao.saoui.themes.elements.RawElement",
                    "com.tencao.saoui.themes.elements.RepetitionGroup",
                    "com.tencao.saoui.themes.util.CBoolean",
                    "com.tencao.saoui.themes.util.CDouble",
                    "com.tencao.saoui.themes.util.CInt",
                    "com.tencao.saoui.themes.util.CString",
                    "com.tencao.saoui.themes.util.CUnit",
                    "com.tencao.saoui.themes.util.ExpressionAdapter",
                    "com.tencao.saoui.themes.util.ExpressionIntermediate");
        }
        if (FugueConfig.modPatchConfig.enableForgeEndertech) {
            TransformerDelegate.registerExplicitTransformer(
                    new ForgeMainTransformer(),
                    "com.endertech.minecraft.forge.ForgeMain"
            );
        }
        if (FugueConfig.modPatchConfig.enableMorePlayerModel) {
            TransformerDelegate.registerExplicitTransformer(
                    new ClientEventHandlerTransformer(),
                    "noppes.mpm.client.ClientEventHandler"
            );
        }
        if (FugueConfig.modPatchConfig.enableUnvalkyriedHeavens) {
            TransformerDelegate.registerExplicitTransformer(
                    new WorldPhysicsColliderTransformer(),
                    "org.valkyrienskies.mod.common.collision.WorldPhysicsCollider"
            );
        }
        if (FugueConfig.modPatchConfig.enableMoBends) {
            TransformerDelegate.registerExplicitTransformer(
                    new ConnectionHelperTransformation(),
                    "goblinbob.mobends.core.util.ConnectionHelper"
            );
            TransformerDelegate.registerExplicitTransformer(
                    new RemapTransformer(
                            new String[]{"org/apache/http/conn/"},
                            new String[]{"org/apache/hc/client5/http/"}
                    ),
                    "goblinbob.mobends.core.connection.PlayerSettingsDownloader",
                    "goblinbob.mobends.core.asset.AssetsModule",
                    "goblinbob.mobends.core.supporters.SupporterContent"
            );
        }

        //Common patches below

        if (FugueConfig.getCodeSourcePatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformer(
                    new ITweakerTransformer(),
                    FugueConfig.getCodeSourcePatchTargets
            );
        }
        if (FugueConfig.reflectionPatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformer(
                    new ReflectFieldTransformer(),
                    FugueConfig.reflectionPatchTargets
            );
        }
        if (FugueConfig.getURLPatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformer(
                    new URLClassLoaderTransformer(),
                    FugueConfig.getURLPatchTargets
            );
        }
        if (FugueConfig.scriptEngineTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformer(
                    new ScriptEngineTransformer(),
                    FugueConfig.scriptEngineTargets
            );
        }
        if (FugueConfig.UUIDTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformer(
                    new MalformedUUIDTransformer(),
                    FugueConfig.UUIDTargets
            );
        }
        if (FugueConfig.remapTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformer(
                    new RemapTransformer(
                            new String[] {
                                    "javax/xml/bind/",
                                    "javax/xml/ws/",
                                    "javax/ws/",
                                    "javax/activation/",
                                    "javax/soap/",
                                    "javax/jws/"
                            },
                            new String[] {
                                    "jakarta/xml/bind/",
                                    "jakarta/xml/ws/",
                                    "jakarta/ws/",
                                    "jakarta/activation/",
                                    "jakarta/soap/",
                                    "jakarta/jws/"
                            }
                    ),
                    FugueConfig.remapTargets
            );
        }
        if (FugueConfig.nonUpdateTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformer(
                    new ConnectionBlockingTransformer(),
                    FugueConfig.nonUpdateTargets
            );
        }
        if (FugueConfig.remapLWTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformer(
                    new RemapLegacyLWTransformer(),
                    FugueConfig.remapLWTargets
            );
        }
        if (FugueConfig.remapReflectionTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformer(
                    new RemapSunReflectionTransformer(),
                    FugueConfig.remapReflectionTargets
            );
        }
        if (FugueConfig.mouseWheelPatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformer(
                    new DWheelTransformer(),
                    FugueConfig.mouseWheelPatchTargets
            );
        }
        if (!FugueConfig.finalRemovingTargets.isEmpty()) {
            TransformerDelegate.registerExplicitTransformer(
                    new FinalStripperTransformer(FugueConfig.finalRemovingTargets),
                    FugueConfig.finalRemovingTargets.keySet().toArray(new String[0])
            );
        }

    }
}
