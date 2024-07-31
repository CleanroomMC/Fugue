package com.cleanroommc.fugue.common;

import com.cleanroommc.fugue.config.FugueConfig;
import com.cleanroommc.fugue.modifiers.IC2ExtraFixer;
import com.cleanroommc.fugue.transformer.*;
import com.cleanroommc.fugue.transformer.groovyscript.*;
import com.cleanroommc.fugue.transformer.logisticpipes.*;
import com.cleanroommc.fugue.transformer.loliasm.JavaFixesTransformer;
import com.cleanroommc.fugue.transformer.loliasm.LoliFMLCallHookTransformer;
import com.cleanroommc.fugue.transformer.loliasm.LoliReflectorTransformer;
import com.cleanroommc.fugue.transformer.loliasm.ModIdentifierTransformer;
import com.cleanroommc.fugue.transformer.nothirium.BufferBuilderTransformer;
import com.cleanroommc.fugue.transformer.nothirium.FreeSectorManagerTransformer;
import com.cleanroommc.fugue.transformer.nothirium.MixinBufferBuilderTransformer;
import com.cleanroommc.fugue.transformer.nothirium.NothiriumClassTransformerTransformer;
import com.cleanroommc.fugue.transformer.openmodlib.InjectorMethodVisitorTransformer;
import com.cleanroommc.fugue.transformer.openmodlib.PlayerRendererHookVisitorTransformer;
import com.cleanroommc.fugue.transformer.subaquatic.PluginEntityTransformer;
import com.cleanroommc.fugue.transformer.tickcentral.*;
import com.cleanroommc.fugue.transformer.universal.*;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.mixin.transformer.Config;
import org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper;
import top.outlands.foundation.TransformerDelegate;
import top.outlands.foundation.boot.ActualClassLoader;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("Fugue")
public class FugueLoadingPlugin implements IFMLLoadingPlugin {

    static {
        Launch.classLoader.addTransformerExclusion("com.cleanroommc.fugue.common.");
        ConfigManager.register(FugueConfig.class);
        if (FugueConfig.modPatchConfig.enableEnderCore) {
            TransformerDelegate.registerExplicitTransformerByInstance(new EnderCoreTransformerTransformer(), "com.enderio.core.common.transform.EnderCoreTransformer");
        }
        if (FugueConfig.modPatchConfig.enableAR) {
            TransformerDelegate.registerExplicitTransformerByInstance(new ClassTransformerTransformer(), "zmaster587.advancedRocketry.asm.ClassTransformer");
        }
        if (FugueConfig.modPatchConfig.enableShoulderSurfing) {
            TransformerDelegate.registerExplicitTransformerByInstance(new EntityPlayerRayTraceTransformer(),"com.teamderpy.shouldersurfing.asm.transformers.EntityPlayerRayTrace");
        }
        if (FugueConfig.modPatchConfig.enableSA){
            TransformerDelegate.registerExplicitTransformerByInstance(new SplashProgressTransformerTransformer(), "pl.asie.splashanimation.core.SplashProgressTransformer");
        }
        if (FugueConfig.modPatchConfig.enableTickCentral){
            TransformerDelegate.registerExplicitTransformerByInstance(new InitializerTransformer(), "com.github.terminatornl.laggoggles.tickcentral.Initializer");
            TransformerDelegate.registerExplicitTransformerByInstance(new ClassSnifferTransformer(), "com.github.terminatornl.tickcentral.api.ClassSniffer");
            TransformerDelegate.registerExplicitTransformerByInstance(new CompatibilityTransformer(), "com.github.terminatornl.tickcentral.asm.Compatibility");
            TransformerDelegate.registerExplicitTransformerByInstance(new TickCentralTransformer(), "com.github.terminatornl.tickcentral.TickCentral");
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new PriorityAppendTransformer(),
                    "com.github.terminatornl.tickcentral.asm.BlockTransformer",
                    "com.github.terminatornl.tickcentral.asm.ITickableTransformer",
                    "com.github.terminatornl.tickcentral.asm.EntityTransformer",
                    "com.github.terminatornl.tickcentral.asm.HubAPITransformer",
                    "net.minecraftforge.fml.common.asm.transformers.ModAPITransformer");
        }
        if (FugueConfig.modPatchConfig.enableLP){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new LogisticPipesHandlerTransformer(),
                    "logisticspipes.asm.mcmp.ClassBlockMultipartContainerHandler",
                    "logisticspipes.asm.td.ClassRenderDuctItemsHandler",
                    "logisticspipes.asm.td.ClassTravelingItemHandler");
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new LogisticsClassTransformerTransformer(ActualClassLoader.class),
                    "logisticspipes.asm.LogisticsClassTransformer",
                    "logisticspipes.asm.LogisticsPipesClassInjector");
            TransformerDelegate.registerExplicitTransformerByInstance(new LogisticsPipesClassInjectorTransformer(), "logisticspipes.asm.LogisticsPipesClassInjector");
            TransformerDelegate.registerExplicitTransformerByInstance(new defineClassTransformer(),
                    "logisticspipes.asm.wrapper.LogisticsWrapperHandler",
                    "logisticspipes.proxy.opencomputers.asm.ClassCreator"
            );
            TransformerDelegate.registerExplicitTransformerByInstance(new LoadClassTransformer(),
                    "network.rs485.debug.OpenGLDebugger",
                    "logisticspipes.utils.StaticResolverUtil");
        }
        if (FugueConfig.modPatchConfig.enableEC){
            TransformerDelegate.registerExplicitTransformerByInstance(new EnumInputClassTransformer(), "austeretony.enchcontrol.common.core.EnumInputClass");
        }
        if (FugueConfig.modPatchConfig.enableTheASM) {
            TransformerDelegate.registerExplicitTransformerByInstance(new LoliReflectorTransformer(), "zone.rong.loliasm.LoliReflector");
            TransformerDelegate.registerExplicitTransformerByInstance(new JavaFixesTransformer(), "zone.rong.loliasm.common.java.JavaFixes");
            TransformerDelegate.registerExplicitTransformerByInstance(new LoliFMLCallHookTransformer(), "zone.rong.loliasm.core.LoliFMLCallHook");
            TransformerDelegate.registerExplicitTransformerByInstance(new ModIdentifierTransformer(), "zone.rong.loliasm.common.crashes.ModIdentifier");
        }
        if (FugueConfig.modPatchConfig.enableZeroCore) {
            TransformerDelegate.registerExplicitTransformerByInstance(new DisplayListTransformer(), "it.zerono.mods.zerocore.lib.client.render.DisplayList");
        }
        if (FugueConfig.modPatchConfig.enableSmoothFont) {
            TransformerDelegate.registerExplicitTransformerByInstance(new FontRendererTransformer(), "net.minecraft.client.gui.FontRenderer");
        }
        if (FugueConfig.modPatchConfig.enableCSL) {
            TransformerDelegate.registerExplicitTransformerByInstance(new ForgeTweakerTransformer(), "customskinloader.forge.ForgeTweaker");
        }
        if (FugueConfig.modPatchConfig.enableXNet) {
            TransformerDelegate.registerExplicitTransformerByInstance(new EnergyConnectorSettingsTransformer(), "mcjty.xnet.apiimpl.energy.EnergyConnectorSettings");
        }
        if (FugueConfig.modPatchConfig.enableCCL) {
            TransformerDelegate.registerExplicitTransformerByInstance(new ClassHierarchyManagerTransformer(), "codechicken.asm.ClassHierarchyManager");
        }
        if (FugueConfig.modPatchConfig.enableSurvivialInc) {
            TransformerDelegate.registerExplicitTransformerByInstance(new ForgeASMInjectorTransformer(), "enginecrafter77.survivalinc.util.ForgeASMInjector");
        }
        if (FugueConfig.modPatchConfig.enableSubaquatic) {
            TransformerDelegate.registerExplicitTransformerByInstance(new PluginEntityTransformer(), "git.jbredwards.subaquatic.mod.asm.plugin.vanilla.entity.PluginEntity");
            TransformerDelegate.registerExplicitTransformerByInstance(new SubaquaticIMTransformer(), "net.minecraft.world.gen.layer.GenLayer");
        }
        if (FugueConfig.modPatchConfig.enableNothirium){
            TransformerDelegate.registerExplicitTransformerByInstance(new NothiriumClassTransformerTransformer(), "meldexun.nothirium.mc.asm.NothiriumClassTransformer");
            MixinServiceLaunchWrapper.registerMixinClassTransformer(new MixinBufferBuilderTransformer(), "meldexun.nothirium.mc.mixin.vertex.MixinBufferBuilder");
            TransformerDelegate.registerExplicitTransformerByInstance(new BufferBuilderTransformer(), "net.minecraft.client.renderer.BufferBuilder");
            TransformerDelegate.registerExplicitTransformerByInstance(new FreeSectorManagerTransformer(), "meldexun.nothirium.util.FreeSectorManager$AVL", "meldexun.nothirium.util.FreeSectorManager$RB");
        }
        if (FugueConfig.modPatchConfig.enableGroovyScript) {
            //TransformerDelegate.registerExplicitTransformerByInstance(new GroovyClassLoaderTransformer(), "groovy.lang.GroovyClassLoader");

            //TransformerDelegate.registerExplicitTransformerByInstance(new GroovyClassLoaderTransformer(), "groovy.lang.GroovyClassLoader");
            //TransformerDelegate.registerExplicitTransformerByInstance(new ExceptionMessageTransformer(), "org.codehaus.groovy.control.messages.ExceptionMessage");
            TransformerDelegate.registerExplicitTransformerByInstance(new GroovyRunnerRegistryTransformer(), "org.apache.groovy.plugin.GroovyRunnerRegistry");
            TransformerDelegate.registerExplicitTransformerByInstance(new ASTTransformationCollectorCodeVisitorTransformer(), "org.codehaus.groovy.transform.ASTTransformationCollectorCodeVisitor");


            /*TransformerDelegate.registerExplicitTransformerByInstance(new ProxyGeneratorAdapterTransformer(), "org.codehaus.groovy.runtime.ProxyGeneratorAdapter$InnerLoader");
            TransformerDelegate.registerExplicitTransformerByInstance(new SunClassLoaderTransformer(), "org.codehaus.groovy.reflection.SunClassLoader");
            TransformerDelegate.registerExplicitTransformerByInstance(new ClassLoaderForClassArtifactsTransformer(), "org.codehaus.groovy.reflection.ClassLoaderForClassArtifacts");
            TransformerDelegate.registerExplicitTransformerByInstance(new ReflectorLoaderTransformer(), "org.codehaus.groovy.runtime.metaclass.ReflectorLoader");*/
        }
        if (FugueConfig.modPatchConfig.enableIC2CE) {
            Config.registerConfigModifier(new IC2ExtraFixer(), "mixins.ic2c_extras.json");
            TransformerDelegate.registerExplicitTransformerByInstance(new Ic2cExtrasLoadingPluginTransformer(), "trinsdar.ic2c_extras.asm.Ic2cExtrasLoadingPlugin");
        }
        if (FugueConfig.modPatchConfig.enableSimplyHotSpring) {
            TransformerDelegate.registerExplicitTransformerByInstance(new SimplyHotSpringsConfigTransformer(), "connor135246.simplyhotsprings.util.SimplyHotSpringsConfig");
        }
        if (FugueConfig.modPatchConfig.enableOpenModsLib) {
            TransformerDelegate.registerExplicitTransformerByInstance(new PlayerRendererHookVisitorTransformer(), "openmods.renderer.PlayerRendererHookVisitor");
            TransformerDelegate.registerExplicitTransformerByInstance(new InjectorMethodVisitorTransformer(), "openmods.renderer.PlayerRendererHookVisitor$InjectorMethodVisitor");
        }
        if (FugueConfig.modPatchConfig.enableValkyrie) {
            MixinServiceLaunchWrapper.registerMixinClassTransformer(new MinecraftMixinTransformer(), "dev.redstudio.valkyrie.mixin.MinecraftMixin");
        }
        if (FugueConfig.modPatchConfig.enableReplayMod) {
            injectCascadingTweak(LateBootstrapTweaker.class.getName());
        }
        if (FugueConfig.modPatchConfig.enableVampirism) {
            TransformerDelegate.registerExplicitTransformerByInstance(new WorldGenVampireOrchidTransformer(), "de.teamlapen.vampirism.biome.BiomeGenVampireForest$WorldGenVampireOrchid");
        }
        if (FugueConfig.modPatchConfig.enableExtraUtilities) {
            TransformerDelegate.registerExplicitTransformerByInstance(new FieldSetterTransformer(), "com.rwtema.extrautils2.utils.datastructures.FieldSetter");
        }
        if (FugueConfig.modPatchConfig.enableBetterFC) {
            TransformerDelegate.registerExplicitTransformerByInstance(new HK_LoaderTransformer(), "kpan.better_fc.asm.hook.HK_Loader");
        }
        if (FugueConfig.modPatchConfig.enable5zig) {
            TransformerDelegate.registerExplicitTransformerByInstance(new ClassTweakerTransformer(), "eu.the5zig.mod.asm.ClassTweaker");
        }
        if (FugueConfig.modPatchConfig.enableEars) {
            TransformerDelegate.registerExplicitTransformerByInstance(new EarsASMTransformer(),
                    "com.unascribed.ears.asm.ImageBufferDownloadTransformer",
                    "com.unascribed.ears.asm.LayerElytraTransformer",
                    "com.unascribed.ears.asm.RenderPlayerTransformer",
                    "com.unascribed.ears.asm.ThreadDownloadImageDataTransformer",
                    "com.unascribed.ears.common.agent.mini.MiniTransformer",
                    "com.unascribed.ears.common.agent.mini.PatchContext",
                    "com.unascribed.ears.common.agent.mini.PatchContext$SearchResult"
            );
        }
        if (FugueConfig.getCodeSourcePatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(new ITweakerTransformer(), FugueConfig.getCodeSourcePatchTargets);
        }
        if (FugueConfig.reflectionPatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(new ReflectFieldTransformer(), FugueConfig.reflectionPatchTargets);
        }
        if (FugueConfig.getURLPatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(new URLClassLoaderTransformer(), FugueConfig.getURLPatchTargets);
        }
        if (FugueConfig.scriptEngineTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(new ScriptEngineTransformer(), FugueConfig.scriptEngineTargets);
        }
        if (FugueConfig.UUIDTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(new MalformedUUIDTransformer(), FugueConfig.UUIDTargets);
        }
        if (FugueConfig.remapTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(new RemapTransformer(), FugueConfig.remapTargets);
        }
        if (FugueConfig.nonUpdateTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(new ConnectionBlockingTransformer(), FugueConfig.nonUpdateTargets);
        }
        if (FugueConfig.remapLWTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(new RemapLegacyLWTransformer(), FugueConfig.remapLWTargets);
        }
        if (FugueConfig.remapReflectionTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(new RemapSunReflectionTransformer(), FugueConfig.remapReflectionTargets);
        }
        if (FugueConfig.mouseWheelPatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(new DWheelTransformer(), FugueConfig.mouseWheelPatchTargets);
        }
        if (!FugueConfig.finalRemovingTargets.isEmpty()) {
            TransformerDelegate.registerExplicitTransformerByInstance(new FinalStripperTransformer(FugueConfig.finalRemovingTargets), FugueConfig.finalRemovingTargets.keySet().toArray(new String[0]));
        }

    }

    
    @Override
    public String[] getASMTransformerClass() {
        if (FugueConfig.modPatchConfig.enableTFCMedical) {
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new CommonRegistrar$Transformer(),
                    "com.lumintorious.tfcmedicinal.CommonRegistrar$",
                    "com.lumintorious.tfcmedicinal.object.mpestle.MPestleRecipe$",
                    "com.lumintorious.tfcmedicinal.object.heater.HeaterRecipe");
        }
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> map) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    public static void injectCascadingTweak(String tweakClassName)
    {
        @SuppressWarnings("unchecked")
        List<String> tweakClasses = (List<String>) Launch.blackboard.get("TweakClasses");
        tweakClasses.add(tweakClassName);
    }
}
