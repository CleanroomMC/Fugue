package com.cleanroommc.fugue.mixin.extrautils2;

import com.cleanroommc.hackery.ReflectionHackery;
import com.rwtema.extrautils2.utils.datastructures.FieldSetter;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(value = FieldSetter.class, remap = false)
public class FieldSetterMixin {
    @Shadow private Field field;


    @Inject(method = "apply", at = @At(value = "HEAD"), cancellable = true)
    private void onApply(Object owner, Object object, CallbackInfo ci) {
        try {
            ReflectionHackery.setField(this.field, owner, object);
        } catch (Exception e) {
            FMLLog.log.log(Level.WARN, "Unable to set {} with value {}", this.field, object);
        }
        ci.cancel();
    }
}
