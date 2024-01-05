package com.cleanroommc.mixin.minecraftmultipartcbe;

import codechicken.multipart.asm.StackAnalyser;
import org.objectweb.asm.Type;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = StackAnalyser.class, remap = false)
public class StackAnalyserMixin {
    @Redirect(method = "visitInsn", at = @At(value = "INVOKE", target = "Lorg/objectweb/asm/Type;getType(Ljava/lang/String;)Lorg/objectweb/asm/Type;"))
    Type getType(String typeDescriptor) {
        try {
            return Type.getType(typeDescriptor);
        } catch (IllegalArgumentException e) {
            return Type.getType("L" + typeDescriptor + ";");
        }
    }
}
