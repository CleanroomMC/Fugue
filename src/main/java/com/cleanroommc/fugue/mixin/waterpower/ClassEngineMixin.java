package com.cleanroommc.fugue.mixin.waterpower;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import waterpower.annotations.ClassEngine;

import java.lang.invoke.MethodHandles;

@Mixin(value = ClassEngine.class, remap = false)
public class ClassEngineMixin {
    @Shadow @Final @Mutable
    public static ClassEngine INSTANCE;

    @Shadow @Final @Mutable
    private static kotlin.Lazy lookup$delegate;

    @Inject(method = "getLookup", at = @At("HEAD"), cancellable = true)
    private void hackLookup(CallbackInfoReturnable<MethodHandles.Lookup> cir) {
        cir.setReturnValue(MethodHandles.lookup());
    }

}
