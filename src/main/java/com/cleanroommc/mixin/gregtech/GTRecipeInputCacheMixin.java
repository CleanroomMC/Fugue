package com.cleanroommc.mixin.gregtech;

import gregtech.api.recipes.GTRecipeInputCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = GTRecipeInputCache.class, remap = false)
public class GTRecipeInputCacheMixin {
    @ModifyArg(method = "enableCache", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectOpenHashSet;<init>(IF)V"), index = 1)
    private static float modifyLoadFactor(float f) {
        return 0.75F;
    }
}
