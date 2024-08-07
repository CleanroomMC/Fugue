package com.cleanroommc.fugue.mixin.extrautils2;

import java.util.HashMap;
import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "com.rwtema.extrautils2.backend.XUBlockStatic$3")
public class XUBlockStatic$3Mixin {
    @Redirect(method = "getQuads", at = @At(value = "INVOKE", target = "Ljava/util/HashMap;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;", remap = false))
    private Object hackCIA(HashMap instance, Object n, Function<Object, Object> k) {
        if (instance.containsKey(n)) {
            return instance.get(n);
        } else {
            Object v = k.apply(n);
            instance.put(n, v);
            return v;
        }
    }
}
