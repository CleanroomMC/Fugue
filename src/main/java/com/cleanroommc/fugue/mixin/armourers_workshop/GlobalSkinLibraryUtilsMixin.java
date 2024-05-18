package com.cleanroommc.fugue.mixin.armourers_workshop;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import moe.plushie.armourers_workshop.common.library.global.GlobalSkinLibraryUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GlobalSkinLibraryUtils.class, remap = false)
public class GlobalSkinLibraryUtilsMixin {
    @Redirect(method = "getUserInfo", at = @At(value = "INVOKE", target = "Lcom/google/common/util/concurrent/Futures;addCallback(Lcom/google/common/util/concurrent/ListenableFuture;Lcom/google/common/util/concurrent/FutureCallback;)V"))
    private static void redirectAddCallback(ListenableFuture future, FutureCallback callback) {
        Futures.addCallback(future, callback, Runnable::run);
    }
}
