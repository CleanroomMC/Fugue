package com.cleanroommc.fugue.mixin.codechickenlib;

import codechicken.asm.ClassHierarchyManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

@Mixin(value = ClassHierarchyManager.class, remap = false)
public class ClassHierarchyManagerMixin {
    @Shadow public static HashMap<String, ClassHierarchyManager.SuperCache> superclasses;

    @Inject(method = "getOrCreateCache", at = @At("HEAD"), cancellable = true)
    private static void threadSafe(String name, CallbackInfoReturnable<ClassHierarchyManager.SuperCache> cir) {
        ClassHierarchyManager.SuperCache cache;
        if (!superclasses.containsKey(name)) {
            cache = new ClassHierarchyManager.SuperCache();
            superclasses.put(name, cache);
        } else {
            cache = superclasses.get(name);
        }
        cir.setReturnValue(cache);
    }
}
