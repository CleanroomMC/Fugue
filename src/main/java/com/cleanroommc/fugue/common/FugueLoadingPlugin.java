package com.cleanroommc.fugue.common;

import com.cleanroommc.fugue.Reference;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ICrashCallable;
import com.cleanroommc.fugue.config.FugueConfig;
import com.cleanroommc.fugue.transformer.tfcmedical.CommonRegistrar$Transformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLCallHook;
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
        if (FugueConfig.modPatchConfig.enableReplayMod) {
            injectCascadingTweak(LateBootstrapTweaker.class.getName());
        }
        TransformerHelper.registerTransformers();
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return "com.cleanroommc.fugue.common.FugueLoadingPlugin$Setup";
    }

    public static void injectCascadingTweak(String tweakClassName)
    {
        @SuppressWarnings("unchecked")
        List<String> tweakClasses = (List<String>) Launch.blackboard.get("TweakClasses");
        tweakClasses.add(tweakClassName);
    }

    public static class Setup implements IFMLCallHook {
        public void injectData(Map<String,Object> data) {}

        public Void call() {
            FMLCommonHandler.instance().registerCrashCallable(new FugueCrashTag());
            return null;
        }

        public static class FugueCrashTag implements ICrashCallable {
	        @Override
            public String call() {
                return Reference.MOD_VERSION;
            }
            @Override
            public String getLabel() {
                return "Fugue Version";
            }
        }
    }
}
