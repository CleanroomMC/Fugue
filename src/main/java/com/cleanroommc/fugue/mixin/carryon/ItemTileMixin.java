package com.cleanroommc.fugue.mixin.carryon;

import com.google.common.base.CharMatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tschipp.carryon.common.item.ItemTile;

@Mixin(ItemTile.class)
public class ItemTileMixin {
    @Redirect(method = "onItemUse", at = @At(value = "FIELD", target = "Lcom/google/common/base/CharMatcher;JAVA_UPPER_CASE:Lcom/google/common/base/CharMatcher;", remap = false))
    private CharMatcher getCharMatcher() {
        return CharMatcher.javaUpperCase();
    }
}
