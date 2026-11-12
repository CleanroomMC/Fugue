package com.cleanroommc.fugue.transformer.mobends;

import com.cleanroommc.fugue.common.Fugue;
import com.cleanroommc.fugue.transformer.universal.RemapTransformer;
import net.minecraft.launchwrapper.Launch;
import top.outlands.foundation.IExplicitTransformer;

import java.io.IOException;

public class ConnectionHelperTransformation implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] basicClass) {
        try {
            return new PackageRemapper().transform(Launch.classLoader.getClassBytes("com.cleanroommc.fugue.helper.ConnectionHelper"));
        } catch (IOException e) {
            Fugue.LOGGER.error("Mo'Bends class replacing failed.", e);
            return basicClass;
        }
    }

    private static class PackageRemapper extends RemapTransformer {

        public PackageRemapper() {
            super(new String[]{"com/cleanroommc/fugue/helper"}, new String[]{"goblinbob/mobends/core/util"});
        }
    }
}
