package com.cleanroommc.fugue.mixin.reccomplex;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import ivorius.reccomplex.json.NBTToJson;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NBTToJson.class, remap = false)
public abstract class NBTToJsonMixin {

    @Inject(method = "getNBTTypeSmart", at = @At(value = "HEAD"), cancellable = true)
    private static void create(JsonElement element, CallbackInfoReturnable<Class<? extends NBTBase>> cir) {
        if (element.isJsonArray())
        {
            JsonArray array = element.getAsJsonArray();

            if (array.isEmpty())
            {
                cir.setReturnValue(NBTTagList.class);
                return;
            }

            boolean allByte = true;
            boolean allInt = true;
            for (JsonElement arrayElement : array)
            {
                if (arrayElement.isJsonPrimitive())
                {
                    JsonPrimitive primitive = arrayElement.getAsJsonPrimitive();
                    if (!(primitive.isNumber() && primitive.getAsNumber() instanceof Byte))
                    {
                        allByte = false;
                    }
                    if (!(primitive.isNumber() && primitive.getAsNumber() instanceof Integer))
                    {
                        allInt = false;
                    }
                }
                else
                {
                    allByte = false;
                    allInt = false;
                }
            }

            if (allByte)
            {
                cir.setReturnValue(NBTTagByteArray.class);
                return;
            }
            if (allInt)
            {
                cir.setReturnValue(NBTTagIntArray.class);
                return;
            }

            cir.setReturnValue(NBTTagList.class);
            return;
        }
        else if (element.isJsonObject())
        {
            String key = element.getAsJsonObject().getAsJsonPrimitive("nbtType").getAsString();
            Class<?> clazz = switch (key) {
                case "int" -> NBTTagInt.class;
                case "long" -> NBTTagLong.class;
                case "short" -> NBTTagShort.class;
                case "float" -> NBTTagFloat.class;
                case "double" -> NBTTagDouble.class;
                case "byte" -> NBTTagByte.class;
                case "list" -> NBTTagList.class;
                case "string" -> NBTTagString.class;
                default -> null;
            };
            if (clazz != null) {
                cir.setReturnValue((Class<? extends NBTBase>) clazz);
                return;
            }
            cir.setReturnValue(NBTTagCompound.class);
            return;
        }
        else if (element.isJsonPrimitive())
        {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString())
            {
                cir.setReturnValue(NBTTagString.class);
                return;
            }
            else if (primitive.isNumber())
            {
                cir.setReturnValue(NBTPrimitive.class);
                return;
            }
        }



        cir.setReturnValue(null);
    }
}
