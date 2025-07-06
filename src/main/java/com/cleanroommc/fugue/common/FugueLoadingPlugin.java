package com.cleanroommc.fugue.common;

import com.cleanroommc.fugue.Reference;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ICrashCallable;
import com.cleanroommc.fugue.config.FugueConfig;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLCallHook;
import top.outlands.foundation.TransformerDelegate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("Fugue")
public class FugueLoadingPlugin implements IFMLLoadingPlugin {

    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_NAME);

    static {
        LOGGER.info("Fugue Version: " + Reference.MOD_VERSION);
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

    public static void injectCascadingTweak(String tweakClassName)
    {
        @SuppressWarnings("unchecked")
        List<String> tweakClasses = (List<String>) Launch.blackboard.get("TweakClasses");
        tweakClasses.add(tweakClassName);
    }
}
