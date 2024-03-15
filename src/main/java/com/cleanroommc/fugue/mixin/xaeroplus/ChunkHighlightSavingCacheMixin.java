package com.cleanroommc.fugue.mixin.xaeroplus;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaeroplus.util.highlights.ChunkHighlightSavingCache;

import java.util.List;

@Mixin(value = ChunkHighlightSavingCache.class, remap = false)
public abstract class ChunkHighlightSavingCacheMixin {
    @Shadow protected abstract List<ListenableFuture<?>> saveAllChunks();

    @Shadow public abstract void reset();

    @Shadow protected abstract void initializeWorld();

    @Shadow protected abstract void loadChunksInActualDimension();

    @Inject(method = "onDisable", at = @At("HEAD"), cancellable = true)
    public void onDisable(CallbackInfo ci) {
        Futures.whenAllComplete(this.saveAllChunks()).call(() -> {
            this.reset();
            return null;
        }, Runnable::run);
        ci.cancel();
    }
    
    @Inject(method = "handleWorldChange", at = @At("HEAD"), cancellable = true)
    public void handleWorldChange(CallbackInfo ci) {
        Futures.whenAllComplete(this.saveAllChunks()).call(() -> {
            this.reset();
            this.initializeWorld();
            this.loadChunksInActualDimension();
            return null;
        }, Runnable::run);
        ci.cancel();
    }
}
