package com.cleanroommc.fugue.mixin.unilib;

import com.cleanroommc.fugue.common.Fugue;
import io.github.cdagaming.unicore.utils.FileUtils;
import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(value = FileUtils.class, remap = false)
public class FileUtilsMixin {
    @Inject(method = "getValidClass(Ljava/lang/ClassLoader;ZZ[Ljava/lang/String;)Ljava/lang/Class;", at = @At("HEAD"), cancellable = true)
    private static void getValidClass(ClassLoader loader, boolean init, boolean forceCache, String[] paths, CallbackInfoReturnable<Class<?>> cir) {
        //Arrays.stream(paths).forEach(Fugue.LOGGER::info);
        try {
            Class<?> clazz = Launch.classLoader.findClass(paths[0]);
            cir.setReturnValue(clazz);
        } catch (ClassNotFoundException ignored) {}
    }
}
