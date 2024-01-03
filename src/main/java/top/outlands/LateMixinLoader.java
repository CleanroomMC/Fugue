package top.outlands;

import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

public class LateMixinLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("fugue.mixin.charset.json");
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        switch (mixinConfig) {
            case "fugue.mixin.charset.json":
                return Loader.isModLoaded("charset");
            default:
                return ILateMixinLoader.super.shouldMixinConfigQueue(mixinConfig);
                
        }
    }
    
}
