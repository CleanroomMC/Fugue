package com.cleanroommc.fugue.mixin.refinedstorage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "com.raoulvdberge.refinedstorage.apiimpl.network.NetworkNodeGraph$Operator")
public class OperatorMixin {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectOpenHashSet;<init>(IF)V"), index = 1)
    private float modify1(float old) {
        return 0.75F;
    }
}
