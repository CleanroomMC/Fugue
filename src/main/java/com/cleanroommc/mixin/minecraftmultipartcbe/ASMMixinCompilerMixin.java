package com.cleanroommc.mixin.minecraftmultipartcbe;


import codechicken.multipart.asm.ASMMixinCompiler$;
import org.objectweb.asm.Type;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = codechicken.multipart.asm.ASMMixinCompiler$.class, remap = false)
public class ASMMixinCompilerMixin {

    @Shadow
    @Final
    @Mutable
    public static ASMMixinCompiler$ MODULE$;
    
    @Redirect(method = "staticTransform$1", at = @At(value = "INVOKE", target = "Lorg/objectweb/asm/Type;getType(Ljava/lang/String;)Lorg/objectweb/asm/Type;"))
    Type getType(String typeDescriptor) {
        try {
            return Type.getType(typeDescriptor);
        } catch (IllegalArgumentException e) {
            return Type.getType("L" + typeDescriptor + ";");
        }
    }
}
