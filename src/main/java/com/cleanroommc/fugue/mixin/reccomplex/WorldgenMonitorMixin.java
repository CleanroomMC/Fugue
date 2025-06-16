package com.cleanroommc.fugue.mixin.reccomplex;

import ivorius.reccomplex.world.gen.feature.WorldgenMonitor;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldgenMonitor.class)
public class WorldgenMonitorMixin {
    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/core/Logger;addAppender(Lorg/apache/logging/log4j/core/Appender;)V"))
    private static void hijack(Logger instance, Appender appender) {}
}
