package com.cleanroommc.fugue.common;

import com.cleanroommc.cleanmix.ConditionalMixin;
import com.cleanroommc.fugue.config.FugueConfig;
import com.cleanroommc.fugue.transformer.tfcmedical.CommonRegistrar$Transformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import top.outlands.foundation.TransformerDelegate;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("Fugue")
public class FugueLoadingPlugin implements IFMLLoadingPlugin {

    static {
        Launch.classLoader.addTransformerExclusion("com.cleanroommc.fugue.common.");
        Launch.classLoader.addTransformerExclusion("com.cleanroommc.fugue.helper.");
        for (var prefix : FugueConfig.extraTransformExclusions) {
            Launch.classLoader.addTransformerExclusion(prefix);
        }
        ConfigManager.register(FugueConfig.class);

        // These mods add their late mixin configurations by hacking Loader, which no longer works
        var patches = FugueConfig.modPatchConfig;
        ConditionalMixin.of(patches.fixTahumicSpeedup, "mixins.thaumicspeedup.json")
            .requires("thaumicspeedup").version("(,4.0]")
            .register();
        ConditionalMixin.of(patches.fixTahumicSpeedup, "mixins.thaumicspeedup_bwmcompat.json")
            .requires("thaumicspeedup").version("(,4.0]")
            .requires("betterwithmods")
            .register();
        ConditionalMixin.of(patches.enableAquaAcrobatics, "META-INF/mixins.aquaacrobatics.galacticraft.json")
            .requires("aquaacrobatics")
            .requires("galacticraftcore")
            .register();
        ConditionalMixin.of(patches.enableAquaAcrobatics, "META-INF/mixins.aquaacrobatics.journeymap55.json")
            .requires("aquaacrobatics")
            .requires("journeymap").version("[1.12.2-5.5.4]")
            .register();
        ConditionalMixin.of(patches.enableAquaAcrobatics, "META-INF/mixins.aquaacrobatics.journeymap57.json")
            .requires("aquaacrobatics")
            .requires("journeymap").version("[1.12.2-5.7.1]")
            .register();
        ConditionalMixin.of(patches.enableAquaAcrobatics, "META-INF/mixins.aquaacrobatics.xaerosminimap.json")
            .requires("aquaacrobatics")
            .requires("xaerominimap")
            .register();
        ConditionalMixin.of(patches.enableAquaAcrobatics, "META-INF/mixins.aquaacrobatics.thaumcraft.json")
            .requires("aquaacrobatics")
            .requires("thaumcraft")
            .register();
        ConditionalMixin.of(patches.enableIntegratedProxyPatch, "mixins.integrated_proxy.mod.json")
            .requires("integrated_proxy")
            .register();
        ConditionalMixin.of(patches.enableThaumicFixesPatch, "mixins.thaumicfixes.modsupport.json")
            .requires("thaumicfixes")
            .register();
        ConditionalMixin.of(patches.enableErebusFixPatch, "mixins.erebusfix.json")
            .requires("erebusfix")
            .register();
        ConditionalMixin.of(patches.enableUncraftingBlacklist, "mixins.uncraftingblacklist.json")
            .requires("uncrafting_blacklist")
            .register();
        ConditionalMixin.of(patches.enableChiseledMe, "chiseled-me.compat.mixins.json")
            .requires("chiseled_me")
            .register();

        if (FugueConfig.modPatchConfig.enableReplayMod) {
            injectCascadingTweak(LateBootstrapTweaker.class.getName());
        }
        TransformerHelper.registerTransformers();
    }

    
    @Override
    public String[] getASMTransformerClass() {
        if (FugueConfig.modPatchConfig.enableTFCMedical) {
            TransformerDelegate.registerExplicitTransformer(
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
