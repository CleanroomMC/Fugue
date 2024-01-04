package com.cleanroommc;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FugueLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader {
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
        return Collections.singletonList("fugue.mixin.minecraftmultipartcbe.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        return mixinConfig.equals("fugue.mixin.minecraftmultipartcbe.json");
        //return IEarlyMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
    }
}
