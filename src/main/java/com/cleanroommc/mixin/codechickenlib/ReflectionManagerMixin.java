package com.cleanroommc.mixin.codechickenlib;

import codechicken.lib.reflect.ObfMapping;
import codechicken.lib.reflect.ReflectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.reflect.Field;

@Mixin(value = ReflectionManager.class, remap = false)
public class ReflectionManagerMixin {
    @Redirect(method = "removeFinal", at = @At(value = "INVOKE", target = "Lcodechicken/lib/reflect/ReflectionManager;getField(Lcodechicken/lib/reflect/ObfMapping;)Ljava/lang/reflect/Field;"))
    private static Field getField(ObfMapping e) {
        try {
            return Field.class.getDeclaredField("modifiers");
        } catch (NoSuchFieldException nsfe) {
            return null;
        }
    }
}
