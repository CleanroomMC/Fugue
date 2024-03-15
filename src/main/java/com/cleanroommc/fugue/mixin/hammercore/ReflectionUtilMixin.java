package com.cleanroommc.fugue.mixin.hammercore;

import com.cleanroommc.fugue.Fugue;
import com.cleanroommc.hackery.ReflectionHackery;
import com.zeitheron.hammercore.utils.ReflectionUtil;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;

@Mixin(value = ReflectionUtil.class, remap = false)
public class ReflectionUtilMixin {
    @Inject(method = "setStaticFinalField(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Object;)Z", at = @At("HEAD"), cancellable = true)
    private static void setFinalFieldStaticInClass(Class<?> cls, String _var, Object val, CallbackInfoReturnable<Boolean> cir) {
        try {
            ReflectionHackery.setField(cls.getDeclaredField(_var), null, val);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Fugue.LOGGER.error(e);
        }
        cir.setReturnValue(true);
    }
    
    @Inject(method = "setStaticFinalField(Ljava/lang/reflect/Field;Ljava/lang/Object;)Z", at = @At("HEAD"), cancellable = true)
    private static void setFinalFieldStatic(Field f, Object val, CallbackInfoReturnable<Boolean> cir) {
        try {
            ReflectionHackery.setField(f, null, val);
        } catch (IllegalAccessException e) {
            Fugue.LOGGER.error(e);
        }
        cir.setReturnValue(true);
    }
    
    @Inject(method = "setFinalField", at = @At("HEAD"), cancellable = true)
    private static void setFinalField(Field f, Object instance, Object thing, CallbackInfoReturnable<Boolean> cir) {
        try {
            ReflectionHackery.setField(f, instance, thing);
        } catch (IllegalAccessException e) {
            Fugue.LOGGER.error(e);
        }
        cir.setReturnValue(true);
    }
    
    @Inject(method = "makeWritable", at = @At("HEAD"), cancellable = true)
    private static void makeWritable(Field f, CallbackInfoReturnable<Field> cir) {
        try {
            ReflectionHackery.stripFieldOfFinalModifier(f);
        } catch (IllegalAccessException e) {
            Fugue.LOGGER.error(e);
        }
        cir.setReturnValue(f);
    }
}
