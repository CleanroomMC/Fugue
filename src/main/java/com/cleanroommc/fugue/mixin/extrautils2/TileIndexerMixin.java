package com.cleanroommc.fugue.mixin.extrautils2;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.rwtema.extrautils2.transfernodes.TileIndexer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = TileIndexer.class, remap = false)
public class TileIndexerMixin {
    @Redirect(method = "reload", at = @At(value = "INVOKE", target = "Lcom/google/common/util/concurrent/Futures;addCallback(Lcom/google/common/util/concurrent/ListenableFuture;Lcom/google/common/util/concurrent/FutureCallback;)V"))
    private void redirectAddCallback(ListenableFuture future, FutureCallback callback) {
        Futures.addCallback(future, callback, Runnable::run);
    }
}
