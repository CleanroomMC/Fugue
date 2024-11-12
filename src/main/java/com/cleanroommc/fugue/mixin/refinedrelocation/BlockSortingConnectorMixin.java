package com.cleanroommc.fugue.mixin.refinedrelocation;

import net.blay09.mods.refinedrelocation.block.BlockSortingConnector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.function.Function;

@Mixin(value = BlockSortingConnector.class, remap = false)
public class BlockSortingConnectorMixin {
    @Redirect(method = "getBoundingBox", at = @At(value = "INVOKE", target = "Ljava/util/Map;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;"), remap = true)
    public Object hackCIA(Map instance, Object k, Function<Object, Object> key) {
        if (instance.containsKey(k)) {
            return instance.get(k);
        } else {
            Object v = key.apply(k);
            instance.put(k, v);
            return v;
        }
    }
}
