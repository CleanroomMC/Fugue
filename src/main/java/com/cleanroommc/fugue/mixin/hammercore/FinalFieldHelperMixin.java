package com.cleanroommc.fugue.mixin.hammercore;

import com.zeitheron.hammercore.utils.FinalFieldHelper;
import com.zeitheron.hammercore.utils.ReflectionUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;

/**
 * This class is deprecated but somehow still being used by other mods. We redirect everything to {@link com.zeitheron.hammercore.utils.ReflectionUtil} because {@code ReflectionUtil} is patched
 *
 * @author ZZZank
 */
@Mixin(value = FinalFieldHelper.class, remap = false)
public abstract class FinalFieldHelperMixin {

    @Inject(method = "setStaticFinalField(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Object;)Z", at = @At("HEAD"), cancellable = true)
    private static void setFinalFieldStaticInClass(Class<?> cls, String name, Object val, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ReflectionUtil.setStaticFinalField(cls, name, val));
    }

    @Inject(method = "setStaticFinalField(Ljava/lang/reflect/Field;Ljava/lang/Object;)Z", at = @At("HEAD"), cancellable = true)
    private static void setFinalFieldStatic(Field f, Object val, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ReflectionUtil.setStaticFinalField(f, val));
    }

    @Inject(method = "setFinalField", at = @At("HEAD"), cancellable = true)
    private static void setFinalField(Field f, Object instance, Object thing, CallbackInfoReturnable<Boolean> cir) throws ReflectiveOperationException {
        cir.setReturnValue(ReflectionUtil.setFinalField(f, instance, thing));
    }
}
