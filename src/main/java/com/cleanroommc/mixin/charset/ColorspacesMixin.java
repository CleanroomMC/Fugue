package com.cleanroommc.mixin.charset;

import com.google.common.collect.Table;
import com.google.common.graph.ValueGraph;
import com.llamalad7.mixinextras.sugar.Local;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pl.asie.charset.lib.utils.colorspace.Colorspace;
import pl.asie.charset.lib.utils.colorspace.Colorspaces;

import java.util.function.Function;

@Mixin(value = Colorspaces.class, remap = false)
@SuppressWarnings("unchecked")
public class ColorspacesMixin {
    @Shadow private static Table<Colorspace, Colorspace, Function<float[], float[]>> conversionTable;

    @Inject(method = "buildConversionTable", at = @At(value = "JUMP", opcode = Opcodes.IF_ICMPGE, ordinal = 3), cancellable = true)
    private static void buildConversionTable(ValueGraph<Colorspace, Function<float[], float[]>> conversionGraph, 
                                             CallbackInfo ci, 
                                             @Local(ordinal = 3) Colorspace[] path,
                                             @Local(ordinal = 0) Colorspace from,
                                             @Local(ordinal = 1) Colorspace to) {
        Function<float[], float[]> function = null;
        for(int i = 0; i < path.length - 1; ++i) {
            if (function == null) {
                function = (Function)conversionGraph.edgeValue(path[i], path[i + 1]).get();
            } else {
                function = ((Function)conversionGraph.edgeValue(path[i], path[i + 1]).get()).compose(function);
            }
        }

        if (function != null) {
            conversionTable.put(from, to, function);
        }
        ci.cancel();
    } 
}
