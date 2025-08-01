package com.cleanroommc.fugue.mixin.wirelessutils;

import com.lordmau5.repack.net.covers1624.model.LayeredTemplateModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.function.Function;

@Mixin(LayeredTemplateModel.ModelBlockWrapper.class)
public class ModelBlockWrapperMixin {
    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Ljava/util/Map;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;"))
    private static Object computeIfAbsent(Map<Object, Object> map, Object key, Function<Object, Object> function) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            Object value = function.apply(key);
            map.put(key, value);
            return value;
        }
    }
}
