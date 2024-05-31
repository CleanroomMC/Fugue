package com.cleanroommc.fugue.mixin.incontrol;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.function.Function;

@Mixin(targets = "mcjty.incontrol.rules.RuleCache$CachePerWorld", remap = false)
public class CachePerWorldMixin {
    @Redirect(method = "count", at = @At(value = "INVOKE", target = "Ljava/util/Map;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;"))
    private Object redirectComputeIfAbsent(Map<Object, Object> map, Object key, Function<Object, Object> function) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            Object value = function.apply(key);
            map.put(key, value);
            return value;
        }
    }
}
