package com.cleanroommc.mixin.hammercore;

import com.cleanroommc.Fugue;
import com.cleanroommc.hackery.ReflectionHackery;
import com.zeitheron.hammercore.utils.ReflectionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;

@Mixin(value = ReflectionUtil.class, remap = false)
public class ReflectionUtilMixin {
    @Inject(method = "setStaticFinalField(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Object;)Z", at = @At("HEAD"), cancellable = true)
    private static void setFinalFieldInClass(Class<?> cls, String _var, Object val, CallbackInfoReturnable<Boolean> cir) {
        try {
            ReflectionHackery.setField(cls.getDeclaredField(_var), null, val);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Fugue.LOGGER.error(e);
        }
        cir.setReturnValue(true);
    }
    
    @Inject(method = "setStaticFinalField(Ljava/lang/reflect/Field;Ljava/lang/Object;)Z", at = @At("HEAD"), cancellable = true)
    private static void setFinalField(Field f, Object val, CallbackInfoReturnable<Boolean> cir) {
        try {
            ReflectionHackery.setField(f, null, val);
        } catch (IllegalAccessException e) {
            Fugue.LOGGER.error(e);
        }
        cir.setReturnValue(true);
    }
    
    @Inject(method = "makeWritable", at = @At("HEAD"), cancellable = true)
    private static void makeWritable(Field f, CallbackInfoReturnable<Field> cir) {
        try {
            ReflectionHackery.stripFieldOfFinalModifier(f);
        } catch (IllegalAccessException ignored) {}
        cir.setReturnValue(f);
    }
}
