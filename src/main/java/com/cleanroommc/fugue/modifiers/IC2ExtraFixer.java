package com.cleanroommc.fugue.modifiers;

import com.cleanroommc.fugue.common.Fugue;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigModifier;

import java.util.List;

public class IC2ExtraFixer implements IMixinConfigModifier {
    @Override
    public void injectConfig(String s) {
    }

    @Override
    public List<String> modifyMixinClasses(List<String> list) {
        return list;
    }

    @Override
    public List<String> modifyMixinClassesClient(List<String> list) {
        return list;
    }

    @Override
    public List<String> modifyMixinClassesServer(List<String> list) {
        return list;
    }

    @Override
    public MixinEnvironment modifyEnvironment(MixinEnvironment mixinEnvironment) {
        return MixinEnvironment.getEnvironment(MixinEnvironment.Phase.MOD);
    }

    @Override
    public boolean shouldAddMixinConfig(boolean b) {
        return true;
    }
}
