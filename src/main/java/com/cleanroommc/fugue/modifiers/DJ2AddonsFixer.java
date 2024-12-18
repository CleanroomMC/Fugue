package com.cleanroommc.fugue.modifiers;

import org.spongepowered.asm.mixin.extensibility.IMixinConfigModifier;

import java.util.List;

public class DJ2AddonsFixer implements IMixinConfigModifier {
    @Override
    public List<String> modifyMixinClasses(List<String> list) {
        list.removeIf(s -> s.endsWith("MLoader"));
        return list;
    }
}
