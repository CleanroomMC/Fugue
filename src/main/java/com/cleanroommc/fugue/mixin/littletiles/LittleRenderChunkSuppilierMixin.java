package com.cleanroommc.fugue.mixin.littletiles;

import com.creativemd.littletiles.client.render.world.LittleRenderChunkSuppilier;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("OverwriteAuthorRequired")
@Mixin(value = LittleRenderChunkSuppilier.class, remap = false)
public abstract class LittleRenderChunkSuppilierMixin {

    @Shadow
    public abstract void unloadRenderCache();

    // Finalizer thread has no GL context, LWJGL3 aborts the JVM on glDeleteBuffers
    @Overwrite
    protected void finalize() {
        Minecraft.getMinecraft().addScheduledTask(this::unloadRenderCache);
    }
}
