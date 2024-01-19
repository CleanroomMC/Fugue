package com.cleanroommc;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class FugueLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {
    static {
        Launch.classLoader.addTransformerExclusionFilter("com.github.terminatornl.laggoggles.");
        Launch.classLoader.addTransformerExclusionFilter("quaternary.botaniatweaks.");
        Launch.classLoader.addTransformerExclusionFilter("zmaster587.advancedRocketry.asm.");
        Launch.classLoader.addTransformerExclusion("pl.asie.foamfix.shared.");
        Launch.classLoader.addTransformerExclusion("pl.asie.patchy.handlers.");
        Launch.classLoader.addTransformerExclusion("pl.asie.foamfix.coremod.patches.BlockPosPatch$BlockPosMethodVisitor");
        Launch.classLoader.addTransformerExclusionFilter("pl.asie.foamfix");
        Launch.classLoader.registerTransformer("com.cleanroommc.transformer.InitializerTransformer");
    }
    
    @Override
    public String[] getASMTransformerClass() {
        
        return new String[]{
                "com.cleanroommc.transformer.EnderCoreTransformerTransformer", 
                "com.cleanroommc.transformer.InitializerTransformer",
                "com.cleanroommc.transformer.ClassTransformerTransformer",
                "com.cleanroommc.transformer.FoamFixTransformer",
                "com.cleanroommc.transformer.EntityPlayerRayTraceTransformer"
        };
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
