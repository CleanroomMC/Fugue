package com.cleanroommc.fugue.mixin.subaquatic;

import git.jbredwards.subaquatic.mod.common.capability.IBubbleColumn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(value = IBubbleColumn.class, remap = false)
public interface IBubbleColumnMixin {
    @Shadow @Final @Nonnull public static Map<Class<?>, Supplier<IBubbleColumn>> BUBBLE_COLUMN_FACTORY = null;

    @Shadow
    public static Supplier<IBubbleColumn> getHandlerForClass(@Nonnull Class<?> clazzIn) {
        return null;
    }

    @Inject(method = "getHandlerForClass", at = @At("HEAD"), cancellable = true)
    private static void fixCME(@Nonnull Class<?> clazzIn, CallbackInfoReturnable<Supplier<IBubbleColumn>> cir) {
        if(clazzIn == Object.class) throw new IllegalArgumentException("Class has no bubble column handler, this should never happen!");
        cir.setReturnValue(() -> {
            if(BUBBLE_COLUMN_FACTORY.containsKey(clazzIn)) {
                return BUBBLE_COLUMN_FACTORY.get(clazzIn).get();
            } else {
                return getHandlerForClass(clazzIn.getSuperclass()).get();
            }
        });
    }
}
