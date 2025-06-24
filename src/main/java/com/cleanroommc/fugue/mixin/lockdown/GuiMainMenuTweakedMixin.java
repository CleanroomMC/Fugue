package com.cleanroommc.fugue.mixin.lockdown;

import adubbz.lockdown.gui.GuiMainMenuTweaked;
import net.minecraft.client.gui.GuiButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(GuiMainMenuTweaked.class)
public class GuiMainMenuTweakedMixin {
    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"))
    public Object redirectGet(List<GuiButton> instance, int i) {
        if (i == 2) {
            return new GuiButton(0, 0, 0, "dummy");
        }
        if (i > 2) {
            return instance.get(i - 1);
        }
        return instance.get(i);
    }
}
