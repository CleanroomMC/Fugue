package com.cleanroommc.fugue.mixin.reccomplex;

import ivorius.reccomplex.shadow.mcopts.commands.parameters.Parameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.StringReader;
import java.lang.reflect.Field;

@Mixin(Parameters.class)
public class ParametersMixin {
    @Inject(method = "index", at = @At("HEAD"), cancellable = true)
    private static void index(StringReader reader, CallbackInfoReturnable<Integer> cir) {
        Class<?> clazz = reader.getClass();
        Field next = null;
        while (clazz != Object.class && next == null) {
            try {
                next = clazz.getDeclaredField("next");
                next.setAccessible(true);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        if (next != null) {
            try {
                cir.setReturnValue(next.getInt(reader));
            } catch (IllegalAccessException e) {
                cir.setReturnValue(0);
            }
        } else {
            cir.setReturnValue(0);
        }
    }
}
