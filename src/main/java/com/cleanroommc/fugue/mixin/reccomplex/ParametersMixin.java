package com.cleanroommc.fugue.mixin.reccomplex;

import ivorius.reccomplex.shadow.mcopts.commands.parameters.Parameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zone.rong.imaginebreaker.Index;
import zone.rong.imaginebreaker.api.ImagineBreaker;

import java.io.StringReader;
import java.lang.reflect.Field;

@Mixin(value = Parameters.class, remap = false)
public class ParametersMixin {
    @Unique
    private static Field fugue$r = null;
    @Unique
    private static Field fugue$next = null;

    @Inject(method = "index", at = @At("HEAD"), cancellable = true)
    private static void index(StringReader reader, CallbackInfoReturnable<Integer> cir) throws NoSuchFieldException {
        ImagineBreaker ib = Index.get();
        if (fugue$next == null) {
            fugue$r = StringReader.class.getDeclaredField("r");
            fugue$next = ib.get(reader, fugue$r).getClass().getDeclaredField("next");
        }
        cir.setReturnValue(ib.getInt(ib.get(reader, fugue$r), fugue$next));
    }
}
