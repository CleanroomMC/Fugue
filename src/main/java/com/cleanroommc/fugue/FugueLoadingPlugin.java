package com.cleanroommc.fugue;

import com.cleanroommc.fugue.config.FugueConfig;
import com.cleanroommc.fugue.transformer.*;
import com.cleanroommc.fugue.transformer.logisticpipes.LogisticPipesTransformer;
import com.cleanroommc.fugue.transformer.logisticpipes.LogisticsClassTransformerTransformer;
import com.cleanroommc.fugue.transformer.logisticpipes.LogisticsPipesClassInjectorTransformer;
import com.cleanroommc.fugue.transformer.loliasm.JavaFixesTransformer;
import com.cleanroommc.fugue.transformer.loliasm.LoliFMLCallHookTransformer;
import com.cleanroommc.fugue.transformer.loliasm.LoliReflectorTransformer;
import com.cleanroommc.fugue.transformer.tickcentral.*;
import com.cleanroommc.fugue.transformer.universal.*;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.mixin.Mixins;
import top.outlands.foundation.TransformerDelegate;
import top.outlands.foundation.boot.ActualClassLoader;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class FugueLoadingPlugin implements IFMLLoadingPlugin {

    static {
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
                    new LogisticPipesTransformer(1),
                    "logisticspipes.asm.mcmp.ClassBlockMultipartContainerHandler",
                    "logisticspipes.asm.td.ClassRenderDuctItemsHandler");
            TransformerDelegate.registerExplicitTransformerByInstance(new LogisticPipesTransformer(3), "logisticspipes.asm.td.ClassTravelingItemHandler");
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new LogisticsClassTransformerTransformer(ActualClassLoader.class),
                    "logisticspipes.asm.LogisticsClassTransformer",
                    "logisticspipes.asm.LogisticsPipesClassInjector");
            TransformerDelegate.registerExplicitTransformerByInstance(new LogisticsPipesClassInjectorTransformer(), "logisticspipes.asm.LogisticsPipesClassInjector");
        }
        if (FugueConfig.modPatchConfig.enableEC){
            TransformerDelegate.registerExplicitTransformerByInstance(new EnumInputClassTransformer(), "austeretony.enchcontrol.common.core.EnumInputClass");
        }
        if (FugueConfig.modPatchConfig.enableTheASM) {
            TransformerDelegate.registerExplicitTransformerByInstance(new LoliReflectorTransformer(), "zone.rong.loliasm.LoliReflector");
            TransformerDelegate.registerExplicitTransformerByInstance(new JavaFixesTransformer(), "zone.rong.loliasm.common.java.JavaFixes");
            TransformerDelegate.registerExplicitTransformerByInstance(new LoliFMLCallHookTransformer(), "zone.rong.loliasm.core.LoliFMLCallHook");
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
}
