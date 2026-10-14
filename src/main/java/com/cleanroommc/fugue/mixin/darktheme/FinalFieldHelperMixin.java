package com.cleanroommc.fugue.mixin.darktheme;

import net.minecraftforge.fml.common.FMLLog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import zone.rong.imaginebreaker.Index;

import java.lang.reflect.Field;

@SuppressWarnings("OverwriteAuthorRequired")
@Pseudo
@Mixin(targets = "org.zeith.darktheme.internal.FinalFieldHelper")
public abstract class FinalFieldHelperMixin {

    @Overwrite(remap = false)
    public static boolean setStaticFinalField(Field f, Object val) {
        try {
            Index.get().set(null, f, val);
            return true;
        } catch (Exception e) {
            FMLLog.log.warn("Failed to set static final field: " + f, e);
            return false;
        }
    }

    @Overwrite(remap = false)
    public static boolean setStaticFinalField(Class<?> clazz, String fieldName, Object val) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            Index.get().set(null, f, val);
            return true;
        } catch (Exception e) {
            FMLLog.log.warn("Failed to set static final field: " + fieldName + " in " + clazz.getName(), e);
            return false;
        }
    }

    @Overwrite(remap = false)
    public static boolean setFinalField(Field f, Object instance, Object value) {
        try {
            Index.get().set(instance, f, value);
            return true;
        } catch (Exception e) {
            FMLLog.log.warn("Failed to set final field: " + f, e);
            return false;
        }
    }

    @Overwrite(remap = false)
    public static Field makeWritable(Field f) {
        try {
            Index.get().enableFinalFieldMutation();
            f.setAccessible(true);
            return f;
        } catch (Exception e) {
            FMLLog.log.warn("Failed to make field writable: " + f, e);
            return f;
        }
    }
}
