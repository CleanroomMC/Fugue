package com.cleanroommc.fugue.mixin.extrautils2;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.rwtema.extrautils2.transfernodes.TransferNodeEnergy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = TransferNodeEnergy.class, remap = false)
public class TransferNodeEnergyMixin {
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lcom/google/common/util/concurrent/Futures;addCallback(Lcom/google/common/util/concurrent/ListenableFuture;Lcom/google/common/util/concurrent/FutureCallback;)V"))
    private void redirectAddCallback(ListenableFuture future, FutureCallback callback) {
        Futures.addCallback(future, callback, Runnable::run);
    }
}
