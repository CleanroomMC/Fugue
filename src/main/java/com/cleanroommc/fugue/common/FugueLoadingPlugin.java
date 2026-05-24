package com.cleanroommc.fugue.common;

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
@IFMLLoadingPlugin.TransformerExclusions({
    "com.cleanroommc.fugue.common.",
    "com.cleanroommc.fugue.helper.",
    "com.cleanroommc.fugue.transformer."
})
public class FugueLoadingPlugin implements IFMLLoadingPlugin {

    static {
        for (var prefix : FugueConfig.extraTransformExclusions) {
            Launch.classLoader.addTransformerExclusion(prefix);
        }
        ConfigManager.register(FugueConfig.class);
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
    public void injectData(Map<String, Object> map) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    public static void injectCascadingTweak(String tweakClassName) {
        @SuppressWarnings("unchecked")
        List<String> tweakClasses = (List<String>) Launch.blackboard.get("TweakClasses");
        tweakClasses.add(tweakClassName);
    }
}
