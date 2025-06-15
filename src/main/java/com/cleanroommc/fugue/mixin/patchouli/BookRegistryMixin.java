package com.cleanroommc.fugue.mixin.patchouli;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.patchouli.common.book.BookRegistry;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

@Mixin(value = BookRegistry.class, remap = false)
public class BookRegistryMixin {

    @Redirect(method = "lambda$null$0", at = @At(value = "INVOKE", target = "Ljava/nio/file/Files;exists(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z"))
    private static boolean moreCheck(Path path, LinkOption[] options) {
        boolean exist = Files.exists(path, options);
        if (exist && Files.isDirectory(path, options)) {
            return false;
        }
        return exist;
    }

}
