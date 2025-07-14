package com.cleanroommc.fugue.mixin.dissolution;

import ladysnake.dissolution.common.entity.PossessableEntityFactory;
import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PossessableEntityFactory.class)
public class PossessableEntityFactoryMixin {
    @Redirect(method = "lambda$defineGenericPossessable$0", at = @At(value = "INVOKE", target = "Lladysnake/dissolution/common/entity/PossessableEntityFactory$ASMClassLoader;define(Ljava/lang/String;[B)Ljava/lang/Class;"))
    private static Class<?> fixDefine(@Coerce ClassLoader instance, String name, byte[] data) {

        return Launch.classLoader.defineClass(name, data);
    }
}
