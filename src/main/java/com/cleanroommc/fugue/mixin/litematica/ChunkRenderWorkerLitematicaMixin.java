package com.cleanroommc.fugue.mixin.litematica;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import fi.dy.masa.litematica.render.schematic.ChunkRenderWorkerLitematica;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ChunkRenderWorkerLitematica.class, remap = false)
public class ChunkRenderWorkerLitematicaMixin {
    @Redirect(method = "processTask", at = @At(value = "INVOKE", target = "Lcom/google/common/util/concurrent/Futures;addCallback(Lcom/google/common/util/concurrent/ListenableFuture;Lcom/google/common/util/concurrent/FutureCallback;)V"))
    protected void hackFuture(ListenableFuture listenableFuture, FutureCallback futureCallback) {
        Futures.addCallback(listenableFuture, futureCallback, Runnable::run);
    }
}
