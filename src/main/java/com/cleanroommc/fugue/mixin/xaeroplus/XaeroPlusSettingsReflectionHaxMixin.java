package com.cleanroommc.fugue.mixin.xaeroplus;

import com.cleanroommc.hackery.enums.EnumHackery;
import com.google.common.primitives.Primitives;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaeroplus.settings.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(value = XaeroPlusSettingsReflectionHax.class, remap = false)
@SuppressWarnings({"unchecked", "rawtypes"})
public class XaeroPlusSettingsReflectionHaxMixin{
    @Inject(method = "constructXaeroPlusSettings", at = @At("HEAD"), cancellable = true)
    private static void constructXaeroPlusSettings(Class clazz, @Coerce Object type, List<XaeroPlusSetting> settings, CallbackInfoReturnable<List> cir) {
        List list = new ArrayList<>();
        for (XaeroPlusSetting setting : settings) {
            Object cursorBox;
            if (setting.getTooltipTranslationKey() != null) {
                if (type == type.getClass().getEnumConstants()[0]) {
                    cursorBox = new xaero.map.gui.CursorBox(setting.getTooltipTranslationKey());
                } else {
                    cursorBox = new xaero.common.graphics.CursorBox(setting.getTooltipTranslationKey());
                }
            } else {
                cursorBox = null;
            }
            String name = setting.getSettingName().replace("[XP] ", "");
            Object[] args = getArgs(type, setting, cursorBox);
            Class<?>[] classes = new Class[args.length];
            for (int i = 0; i< args.length; i++) {
                classes[i] = Primitives.isWrapperType(args[i].getClass()) ? Primitives.unwrap(args[i].getClass()) : args[i].getClass();
                
            }
            list.add(EnumHackery.addEnumEntry(clazz, name, classes, args));
        }
        cir.setReturnValue(list);
    }

    private static Object[] getArgs(Object type, XaeroPlusSetting setting, Object cursorBox) {
        Object[] args = new Object[]{
                setting.getSettingName(), 
                setting instanceof XaeroPlusFloatSetting || setting instanceof XaeroPlusEnumSetting, 
                setting instanceof XaeroPlusBooleanSetting, 
                setting instanceof XaeroPlusFloatSetting ? ((XaeroPlusFloatSetting) setting).getValueMin() : 0.0F, 
                setting instanceof XaeroPlusFloatSetting ? ((XaeroPlusFloatSetting) setting).getValueMax() : (setting instanceof XaeroPlusEnumSetting ? (float)((XaeroPlusEnumSetting) setting).getIndexMax() : 0.0F), setting instanceof XaeroPlusFloatSetting ? ((XaeroPlusFloatSetting) setting).getValueStep() : 1.0F, cursorBox, setting.isIngameOnly(), setting.isRequiresMinimap()};
        if (type == type.getClass().getEnumConstants()[1]) {
            args = Arrays.copyOf(args, args.length - 1);
        }
        return args;
    }
}
