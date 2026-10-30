package com.cleanroommc.fugue.mixin.logisticspipes;

import logisticspipes.proxy.cc.CCProxy;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.reflect.Field;

@Mixin(CCProxy.class)
public class CCProxyMixin {

    @Unique
    private Field fugue$holder;

    @Unique
    private Field fugue$task;

    @Redirect(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/Class;getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;"
        ),
        remap = false
    )
    private Field fugue$getDeclaredField(Class<?> instance, String name) throws NoSuchFieldException {

        fugue$holder = Thread.class.getDeclaredField("holder");
        fugue$task = fugue$holder.getType().getDeclaredField("task");

        return null;

    }

    @Redirect(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/reflect/Field;setAccessible(Z)V"
        ),
        remap = false
    )
    private void fugue$init(Field instance, boolean flag) {
        fugue$holder.setAccessible(true);
        fugue$task.setAccessible(true);
    }

    @Redirect(
        method = "getTaget",
        at = @At(
            value = "INVOKE",
            target = "Ljava/lang/reflect/Field;get(Ljava/lang/Object;)Ljava/lang/Object;"
        ),
        remap = false
    )
    private Object fugue$getTarget(Field instance, Object obj) throws IllegalAccessException {
        return fugue$task.get(fugue$holder.get(obj));
    }

}
