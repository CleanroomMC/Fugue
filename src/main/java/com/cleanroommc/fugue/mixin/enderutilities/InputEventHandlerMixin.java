package com.cleanroommc.fugue.mixin.enderutilities;

import fi.dy.masa.enderutilities.event.InputEventHandler;
import net.minecraftforge.client.event.MouseEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = InputEventHandler.class, remap = false)
public class InputEventHandlerMixin {
    @Redirect(method = "onMouseEvent", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/event/MouseEvent;getDwheel()I"))
    private int normalizeDWheel(MouseEvent event) {
        return Integer.signum(event.getDwheel()) * 120;
    }
}