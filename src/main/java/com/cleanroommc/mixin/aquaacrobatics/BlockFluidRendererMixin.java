package com.cleanroommc.mixin.aquaacrobatics;

import net.minecraft.client.renderer.BlockFluidRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockFluidRenderer.class, priority = 0)
public class BlockFluidRendererMixin {
}
