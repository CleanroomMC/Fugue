package com.cleanroommc.fugue.mixin.astralsorcery;

import hellfirepvp.astralsorcery.common.constellation.perk.attribute.PerkAttributeType;
import org.apache.commons.compress.utils.Lists;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Mixin(PerkAttributeType.class)
public class PerkAttributeTypeMixin {
    @Redirect(method = {"onApply", "onRemove", "hasTypeApplied"}, at = @At(value = "INVOKE", target = "Ljava/util/Map;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;"))
    private Object redirectComputeIfAbsent(Map<Object, Object> map, Object key, Function<Object, Object> function) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            List<UUID> list = Lists.newArrayList();
            map.put(key, list);
            return list;
        }
    }

}
