package com.cleanroommc.fugue.modifiers;

import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigModifier;

public class DJ2PhaseFixer implements IMixinConfigModifier {

    public MixinEnvironment modifyEnvironment(MixinEnvironment environment) {
        return MixinEnvironment.getEnvironment(MixinEnvironment.Phase.MOD);
    }
}
