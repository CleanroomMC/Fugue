package com.cleanroommc.fugue.mixin.funkylocomotion;

import com.rwtema.funkylocomotion.asm.WrenchFactory;
import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WrenchFactory.class)
public class WrenchFactoryMixin {
    @Redirect(method = "makeMeAWrench", at = @At(value = "INVOKE", target = "Lcom/rwtema/funkylocomotion/asm/WrenchFactory$ASMClassLoader;define(Ljava/lang/String;[B)Ljava/lang/Class;"))
    private static Class<?> redefine(@Coerce ClassLoader instance, String name, byte[] data) {
        return Launch.classLoader.defineClass(name, data);
    }


}
