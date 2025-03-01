package com.cleanroommc.fugue.mixin.customnpcs;

import com.google.common.reflect.ClassPath;
import net.minecraft.launchwrapper.Launch;
import noppes.npcs.PlayerEventHandler;
import noppes.npcs.ScriptPlayerEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {ScriptPlayerEventHandler.class, PlayerEventHandler.class}, remap = false)
public class ScriptPlayerEventHandlerMixin {
    @Redirect(method = "registerForgeEvents", at = @At(value = "INVOKE", target = "Lcom/google/common/reflect/ClassPath$ClassInfo;load()Ljava/lang/Class;"))
    private Class<?> hackClassinfoLoad(ClassPath.ClassInfo instance) throws ClassNotFoundException {
        return Launch.classLoader.findClass(instance.getName());
    }
}
