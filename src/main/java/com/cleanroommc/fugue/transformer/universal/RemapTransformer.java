package com.cleanroommc.fugue.transformer.universal;

import net.minecraftforge.fml.common.FMLLog;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import top.outlands.foundation.IExplicitTransformer;

public class RemapTransformer implements IExplicitTransformer {


    @Override
    public byte[] transform( byte[] bytes) {

        if (bytes == null) {
            return null;
        }
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(0);
        ClassVisitor visitor = new ClassRemapper(writer, new RemapTransformer.JavaxRemapper());
        try {
            reader.accept(visitor, ClassReader.EXPAND_FRAMES);
        } catch (Exception e) {
            FMLLog.log.warn("Couldn't remap class {}", reader.getClassName(), e);
            return bytes;
        }
        return writer.toByteArray();
    }

    static class JavaxRemapper extends Remapper {
        final String[] fromPrefixes = new String[] { "javax/xml/bind/", "javax/xml/ws/", "javax/ws/", "javax/activation/", "javax/soap/", "javax/jws/"};

        final String[] toPrefixes = new String[] { "jakarta/xml/bind/", "jakarta/xml/ws/", "jakarta/ws/", "jakarta/activation/", "jakarta/soap/", "jakarta/jws/"};

        @Override
        public String map(String typeName) {
            if (typeName == null) {
                return null;
            }
            for (int pfx = 0; pfx < fromPrefixes.length; pfx++) {
                if (typeName.startsWith(fromPrefixes[pfx])) {
                    return toPrefixes[pfx] + typeName.substring(fromPrefixes[pfx].length());
                }
            }

            return typeName;
        }
    }
}
