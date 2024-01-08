package com.cleanroommc;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class FugueLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {
    static {
        Launch.classLoader.addTransformerExclusionFilter("com.github.terminatornl.laggoggles.");
        Launch.classLoader.addTransformerExclusionFilter("quaternary.botaniatweaks.");
        Launch.classLoader.registerTransformer("com.cleanroommc.transformer.InitializerTransformer");
    }
    
    @Override
    public String[] getASMTransformerClass() {
        
        return new String[]{
                "com.cleanroommc.transformer.EnderCoreTransformerTransformer", 
                "com.cleanroommc.transformer.InitializerTransformer"
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
        return Collections.singletonList("fugue.mixin.aquaacrobatics.json");
    }
}
