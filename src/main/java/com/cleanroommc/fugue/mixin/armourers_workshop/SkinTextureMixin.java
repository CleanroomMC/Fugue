package com.cleanroommc.fugue.mixin.armourers_workshop;

import moe.plushie.armourers_workshop.common.skin.data.SkinTexture;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = SkinTexture.class, remap = false)
public abstract class SkinTextureMixin {
    @Shadow protected abstract void deleteTexture();

    @Redirect(method = "finalize", at = @At(value = "INVOKE", target = "Lmoe/plushie/armourers_workshop/common/skin/data/SkinTexture;deleteTexture()V"))
    private void redirectDeleteTexture(SkinTexture skinTexture) {
        Minecraft.getMinecraft().addScheduledTask(this::deleteTexture);
    }
}
