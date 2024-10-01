package com.cleanroommc.fugue.mixin.betterrecords;

import com.cleanroommc.fugue.helper.SoundThread;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.feldman.betterrecords.api.sound.Sound;
import tech.feldman.betterrecords.client.sound.SoundManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(value = SoundManager.class, remap = false)
public class SoundManagerMixin {
    @Unique
    private static final Map<Thread, AtomicBoolean> threads = new ConcurrentHashMap<>();
    @Redirect(method = "stopQueueAt", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;stop()V"))
    private void stopQueueAt(Thread thread) {
        threads.get(thread).set(Boolean.FALSE);
        thread.interrupt();
    }

    @Inject(method = "queueSongsAt", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private void hackThread(BlockPos pos, int dimension, List<Sound> sounds, boolean shuffle, boolean repeat, CallbackInfo ci, @Local LocalRef<Thread> job) {
        AtomicBoolean interrupted = new AtomicBoolean(false);
        Thread thread = new SoundThread(interrupted, sounds, pos, dimension, repeat);
        job.set(thread);
        threads.put(thread, interrupted);
    }

}
