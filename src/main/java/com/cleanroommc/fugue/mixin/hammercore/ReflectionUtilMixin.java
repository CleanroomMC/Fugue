package com.cleanroommc.fugue.mixin.hammercore;

import com.cleanroommc.fugue.common.Fugue;
import com.zeitheron.hammercore.utils.ReflectionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zone.rong.imaginebreaker.Index;

import java.lang.reflect.Field;

@Mixin(value = ReflectionUtil.class, remap = false)
public class ReflectionUtilMixin {
    @Inject(method = "setStaticFinalField(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Object;)Z", at = @At("HEAD"), cancellable = true)
    private static void setFinalFieldStaticInClass(Class<?> cls, String _var, Object val, CallbackInfoReturnable<Boolean> cir) {
        try {
            Index.get().set(null, cls.getDeclaredField(_var), val);
        } catch (NoSuchFieldException e) {
            Fugue.LOGGER.error(e);
        }
        cir.setReturnValue(true);
    }
    
    @Inject(method = "setStaticFinalField(Ljava/lang/reflect/Field;Ljava/lang/Object;)Z", at = @At("HEAD"), cancellable = true)
    private static void setFinalFieldStatic(Field f, Object val, CallbackInfoReturnable<Boolean> cir) {
        Index.get().set(null, f, val);
        cir.setReturnValue(true);
    }
    
    @Inject(method = "setFinalField", at = @At("HEAD"), cancellable = true)
    private static void setFinalField(Field f, Object instance, Object thing, CallbackInfoReturnable<Boolean> cir) {
        Index.get().set(instance, f, thing);
        cir.setReturnValue(true);
    }
    
    @Inject(method = "makeWritable", at = @At("HEAD"), cancellable = true)
    private static void makeWritable(Field f, CallbackInfoReturnable<Field> cir) {
        Index.get().enableFinalFieldMutation();
        f.setAccessible(true);
        cir.setReturnValue(f);
    }
}
