package com.cleanroommc;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import scala.actors.threadpool.Arrays;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@SuppressWarnings("deprecation")
public class FugueLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {
    private static final LinkedHashMap<String, IClassTransformer> transformerCache = new LinkedHashMap<>();
    
    static {
        List<String> transformers = asList(
                "com.cleanroommc.transformer.EnderCoreTransformerTransformer",
                "com.cleanroommc.transformer.ClassTransformerTransformer",
                "com.cleanroommc.transformer.FoamFixTransformer",
                "com.cleanroommc.transformer.EntityPlayerRayTraceTransformer",
                "com.cleanroommc.transformer.SplashProgressTransformerTransformer",
                "com.cleanroommc.transformer.InitializerTransformer",
                "com.cleanroommc.transformer.ClassBlockMultipartContainerHandlerTransformer",
                "com.cleanroommc.transformer.OpenDisksUnpackTransformer",
                "com.cleanroommc.transformer.SoundUnpackTransformer"
        );

        for(String str : transformers) {
            Fugue.LOGGER.info("Registering " + str);
            Launch.classLoader.registerSuperTransformer(str);
        }
    }

    /**
     * Used to register transformer need to be un register
     * @param key         The identify key, could be modid or class name (if not containing ".")
     * @param transformer The transformer instance
     */
    public static void registerToKnownTransformer(String key, IClassTransformer transformer) {
        transformerCache.put(key, transformer);
    }

    /**
     * Used to un register super transformers when the target mods is not exist to lower performance impact
     */
    public static void unRegisterUselessTransformer() {
        for(String key : transformerCache.keySet()) {
            if (key.contains(".")) {
                try {
                    Class.forName(key);
                } catch (ClassNotFoundException e) {
                    Launch.classLoader.unRegisterSuperTransformer(transformerCache.get(key));
                }
            } else {
                if (!Loader.isModLoaded(key)) {
                    Launch.classLoader.unRegisterSuperTransformer(transformerCache.get(key));
                }
            }
        }
    }
    
    @Override
    public String[] getASMTransformerClass() {
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
