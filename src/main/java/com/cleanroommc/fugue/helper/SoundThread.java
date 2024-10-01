package com.cleanroommc.fugue.helper;

import net.minecraft.util.math.BlockPos;
import tech.feldman.betterrecords.api.sound.Sound;
import tech.feldman.betterrecords.client.sound.SoundPlayer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SoundThread extends Thread {
    private final AtomicBoolean interrupted;
    private final List<Sound> sounds;
    private final BlockPos pos;
    private final int dim;
    private final boolean repeat;

    public SoundThread(AtomicBoolean interrupted, List<Sound> sounds, BlockPos pos, int dim, boolean repeat) {
        super("Better Records Sound Thread");
        this.interrupted = interrupted;
        this.sounds = sounds;
        this.pos = pos;
        this.dim = dim;
        this.repeat = repeat;
    }

    @Override
    public void interrupt() {
        interrupted.set(true);
    }

    @Override
    public void run() {
        while (!interrupted.get()) {
            sounds.forEach(sound -> SoundPlayer.INSTANCE.playSound(pos, dim, sound));
            if (!repeat) return;
        }
    }
}
