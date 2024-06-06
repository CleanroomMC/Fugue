package com.cleanroommc.fugue.mixin.jei;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import mezz.jei.gui.recipes.RecipesGui;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RecipesGui.class, remap = false)
public class RecipesGuiMixin {
    @Inject(method = "updateLayout", at = @At(value = "JUMP", opcode = Opcodes.IFNE), require = 0)
    private void hackrpp(CallbackInfo ci, @Local(ordinal = 2)LocalIntRef recipesPerPage) {
        if (recipesPerPage.get() < 0) recipesPerPage.set(0);
    }
}
