package com.cleanroommc.fugue.transformer.allmusic_client;

import com.cleanroommc.fugue.common.Fugue;
import org.apache.commons.io.IOUtils;
import top.outlands.foundation.IExplicitTransformer;

import java.io.IOException;
import java.io.InputStream;

public class AllMusicHudTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try (InputStream inputStream = getClass().getResourceAsStream("/patches/AllMusicHud.clazz")) {
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
