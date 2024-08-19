package com.cleanroommc.fugue.mixin.infinitylib;

import com.infinityraider.infinitylib.render.block.BakedInfBlockModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.function.Function;

@Mixin(value = BakedInfBlockModel.class, remap = false)
public class BakedInfBlockModelMixin {
    @Redirect(method = "getQuads(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/EnumFacing;J)Lcom/google/common/collect/ImmutableList;", at = @At(value = "INVOKE", target = "Ljava/util/Map;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;"))
    private Object hackCIA(Map instance, Object n, Function<Object, Object> k) {
        if (instance.containsKey(n)) {
            return instance.get(n);
        } else {
            Object v = k.apply(n);
            instance.put(n, v);
            return v;
        }
    }
}
