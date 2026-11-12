package com.cleanroommc.fugue.transformer.allmusic_client;

import com.cleanroommc.fugue.common.Fugue;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.io.IOUtils;
import top.outlands.foundation.IExplicitTransformer;

import java.io.IOException;
import java.io.InputStream;

public class AllMusicPlayerTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try (InputStream inputStream = getClass().getResourceAsStream("/patches/AllMusicPlayer.bin")) {
            if (inputStream != null) {
                return IOUtils.toByteArray(inputStream);
            } else {
                Fugue.LOGGER.error("Resource is null");
                return null;
            }
        } catch (IOException e) {
            Fugue.LOGGER.error(e.getLocalizedMessage());
            return null;
        }
    }
}
