package com.cleanroommc.fugue.mixin.charset;

import com.google.common.collect.Table;
import com.google.common.graph.ValueGraph;
import com.llamalad7.mixinextras.sugar.Local;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.asie.charset.lib.utils.colorspace.Colorspace;
import pl.asie.charset.lib.utils.colorspace.Colorspaces;

import java.util.function.Function;

@Mixin(value = Colorspaces.class, remap = false)
@SuppressWarnings("unchecked")
public class ColorspacesMixin {
    //@Shadow private static Table<Colorspace, Colorspace, Function<float[], float[]>> conversionTable;
    
    @Redirect(method = "buildConversionTable", at = @At(value = "INVOKE", target = "Lcom/google/common/graph/ValueGraph;edgeValue(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static Object buildConversionTable(ValueGraph instance, Object object1, Object object2) {
        return instance.edgeValue(object1, object2).get();
    }

}
