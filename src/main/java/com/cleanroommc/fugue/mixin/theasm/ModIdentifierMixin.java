package com.cleanroommc.fugue.mixin.theasm;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraftforge.fml.common.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zone.rong.loliasm.common.crashes.ModIdentifier;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Mixin(value = ModIdentifier.class, remap = false)
public class ModIdentifierMixin {
    @Inject(method = "identifyFromClass(Ljava/lang/String;Ljava/util/Map;)Ljava/util/Set;", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;debug(Ljava/lang/String;)V"), cancellable = true)
    private static void catchRUL(String className, Map<File, Set<ModContainer>> modMap, CallbackInfoReturnable<Set<ModContainer>> cir, @Local URL url) throws URISyntaxException {
        if (url != null && url.toString().startsWith("jrt")) {
            cir.setReturnValue(Collections.emptySet());
        }
    }
}
