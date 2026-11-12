package com.cleanroommc.fugue.mixin.solarflux;

import net.minecraftforge.fml.common.FMLLog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tk.zeitheron.solarflux.SolarFlux;
import zone.rong.imaginebreaker.Index;

@SuppressWarnings("OverwriteAuthorRequired")
@Mixin(value = SolarFlux.FinalFieldHelper.class, remap = false)
public abstract class FinalFieldHelperMixin {

    @Overwrite
    static boolean setStaticFinalField(Class<?> clazz, String fieldName, Object val) {
        try {
            Index.get().set(null, clazz.getDeclaredField(fieldName), val);
            return true;
        } catch (Exception e) {
            FMLLog.log.warn("Failed to set static final field: " + fieldName + " in " + clazz.getName(), e);
            return false;
        }
    }
}
