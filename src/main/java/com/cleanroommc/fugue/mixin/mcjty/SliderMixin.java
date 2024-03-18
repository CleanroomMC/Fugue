package com.cleanroommc.fugue.mixin.mcjty;

import mcjty.lib.gui.Window;
import mcjty.lib.gui.widgets.AbstractWidget;
import mcjty.lib.gui.widgets.Slider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Slider.class, remap = false)
public abstract class SliderMixin extends AbstractWidget {
    protected SliderMixin(Minecraft mc, Gui gui) {
        super(mc, gui);
    }

    @Shadow protected abstract void findScrollable(Window window);

    @Inject(method = "mouseWheel", at = @At("HEAD"))
    private void checkBeforeRun(int amount, int x, int y, CallbackInfoReturnable<Boolean> cir) {
        this.findScrollable(this.window);
    }
}
