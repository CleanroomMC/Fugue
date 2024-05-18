package com.cleanroommc.fugue.mixin.mage;

import com.google.common.graph.ValueGraph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pl.asie.mage.util.colorspace.Colorspaces;

@Mixin(value = Colorspaces.class, remap = false)
@SuppressWarnings("unchecked")
public class ColorspacesMixin {
    
    @Redirect(method = "buildConversionTable", at = @At(value = "INVOKE", target = "Lcom/google/common/graph/ValueGraph;edgeValue(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static Object buildConversionTable(ValueGraph instance, Object object1, Object object2) {
        return instance.edgeValue(object1, object2).get();
    }

}
