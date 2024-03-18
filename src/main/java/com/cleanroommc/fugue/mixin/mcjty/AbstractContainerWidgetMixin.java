package com.cleanroommc.fugue.mixin.mcjty;

import mcjty.lib.gui.widgets.AbstractContainerWidget;
import mcjty.lib.gui.widgets.AbstractWidget;
import mcjty.lib.gui.widgets.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = AbstractContainerWidget.class, remap = false)
public abstract class AbstractContainerWidgetMixin <P extends AbstractContainerWidget<P>>
        extends AbstractWidget<P> {
    @Shadow @Final private List<Widget<?>> children;

    protected AbstractContainerWidgetMixin(Minecraft mc, Gui gui) {
        super(mc, gui);
    }

    @Inject(method = "mouseWheel", at = @At("HEAD"))
    private void setChildWindow(int amount, int x, int y, CallbackInfoReturnable<Boolean> cir) {
        this.children.forEach(c -> c.setWindow(this.window));
    }

}
