package com.cleanroommc.fugue.mixin.reccomplex;

import ivorius.reccomplex.shadow.mcopts.commands.parameters.Parameters;
import net.lenni0451.reflect.Fields;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.StringReader;
import java.lang.reflect.Field;

@Mixin(value = Parameters.class, remap = false)
public class ParametersMixin {
    @Unique
    private static Field fugue$r = null;
    @Unique
    private static Field fugue$next = null;

    @Inject(method = "index", at = @At("HEAD"), cancellable = true)
    private static void index(StringReader reader, CallbackInfoReturnable<Integer> cir) {
        if (fugue$next == null) {
            fugue$r = Fields.getDeclaredField(StringReader.class, "r");
            fugue$next = Fields.getDeclaredField(Fields.get(reader, fugue$r).getClass(), "next");
        }
        cir.setReturnValue(Fields.get(Fields.get(reader, fugue$r), fugue$next));
    }
}
