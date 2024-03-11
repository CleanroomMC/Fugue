package com.cleanroommc;

import com.cleanroommc.config.FugueConfig;
import com.cleanroommc.transformer.*;
import com.cleanroommc.transformer.universal.*;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import top.outlands.foundation.IExplicitTransformer;
import top.outlands.foundation.TransformerDelegate;
import top.outlands.foundation.boot.ActualClassLoader;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@SuppressWarnings("deprecation")
public class FugueLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {

    static {
        ConfigManager.register(FugueConfig.class);
        if (FugueConfig.enableEnderCore) {
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "com.enderio.core.common.transform.EnderCoreTransformer"
                    },
                    new EnderCoreTransformerTransformer()
            );
        }
        if (FugueConfig.enableAR) {
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "zmaster587.advancedRocketry.asm.ClassTransformer"
                    },
                    new ClassTransformerTransformer()
            );
        }
        if (FugueConfig.enableShoulderSurfing) {
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "com.teamderpy.shouldersurfing.asm.transformers.EntityPlayerRayTrace"
                    },
                    new EntityPlayerRayTraceTransformer()
            );
        }
        if (FugueConfig.enableSA){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "pl.asie.splashanimation.core.SplashProgressTransformer"
                    },
                    new SplashProgressTransformerTransformer()
            );
        }
        if (FugueConfig.enableTickCentral){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "com.github.terminatornl.laggoggles.tickcentral.Initializer"
                    },
                    new InitializerTransformer()
            );
        }
        if (FugueConfig.enableLP){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "logisticspipes.asm.mcmp.ClassBlockMultipartContainerHandler",
                            "logisticspipes.asm.td.ClassRenderDuctItemsHandler"
                    },
                    new LogisticPipesTransformer(1)
            );
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "logisticspipes.asm.td.ClassTravelingItemHandler"
                    },
                    new LogisticPipesTransformer(3)
            );
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "logisticspipes.asm.LogisticsClassTransformer",
                            "logisticspipes.asm.LogisticsPipesClassInjector"
                    },
                    new LogisticsClassTransformerTransformer(ActualClassLoader.class)
            );
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "logisticspipes.asm.LogisticsPipesClassInjector"
                    },
                    new LogisticsPipesClassInjectorTransformer()
            );
        }
        if (FugueConfig.enableOpenAddons){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "pcl.opendisks.OpenDisksUnpack",
                            "pcl.opensecurity.util.SoundUnpack",
                            "pcl.OpenFM.misc.DepLoader"
                    },
                    new OpenDisksUnpackTransformer()
            );
        }
        if (FugueConfig.enableEC){
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "austeretony.enchcontrol.common.core.EnumInputClass"
                    },
                    new EnumInputClassTransformer()
            );
        }
        IExplicitTransformer instance = new ReflectFieldTransformer();
        if (FugueConfig.reflectionPatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.reflectionPatchTargets, instance);
        }
        instance = new URLClassLoaderTransformer();
        if (FugueConfig.getURLPatchTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.getURLPatchTargets, instance);
        }
        instance = new ScriptEngineTransformer();
        if (FugueConfig.scriptEngineTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.scriptEngineTargets, instance);
        }
        instance = new MalformedUUIDTransformer();
        if (FugueConfig.UUIDTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.UUIDTargets, instance);
        }
        instance = new ReflectionTransformer();
        if (FugueConfig.remapTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.remapTargets, instance);
        }
        instance = new ConnectionBlockingTransformer();
        if (FugueConfig.nonUpdateTargets.length > 0) {
            TransformerDelegate.registerExplicitTransformerByInstance(FugueConfig.nonUpdateTargets, instance);
        }

    }
    
    @Override
    public String[] getASMTransformerClass() {
        if (FugueConfig.enableTFCMedical) {
            TransformerDelegate.registerExplicitTransformerByInstance(
                    new String[]{
                            "com.lumintorious.tfcmedicinal.CommonRegistrar$",
                            "com.lumintorious.tfcmedicinal.object.mpestle.MPestleRecipe$",
                            "com.lumintorious.tfcmedicinal.object.heater.HeaterRecipe"
                    },
                    new CommonRegistrar$Transformer()
            );
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

    @Override
    public List<String> getMixinConfigs() {
        return Collections.emptyList();
    }
}
