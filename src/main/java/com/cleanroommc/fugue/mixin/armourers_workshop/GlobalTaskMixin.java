package com.cleanroommc.fugue.mixin.armourers_workshop;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import moe.plushie.armourers_workshop.common.library.global.task.GlobalTask;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.FutureTask;

@Mixin(value = GlobalTask.class, remap = false)
public class GlobalTaskMixin {
    @Redirect(
            method =
                    {
                            "createTask(Lcom/google/common/util/concurrent/FutureCallback;)Lcom/google/common/util/concurrent/ListenableFutureTask;",
                            "createTaskAndRun"
                    },
            at = @At(value = "INVOKE", target = "Lcom/google/common/util/concurrent/Futures;addCallback(Lcom/google/common/util/concurrent/ListenableFuture;Lcom/google/common/util/concurrent/FutureCallback;)V"))
    private void redirectAddCallback(ListenableFuture listenableFuture, FutureCallback futureCallback) {
        Futures.addCallback(listenableFuture, futureCallback, Runnable::run);
    }
}
