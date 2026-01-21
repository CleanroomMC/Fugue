package com.cleanroommc.fugue.transformer.universal;

import net.minecraftforge.fml.common.FMLLog;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import top.outlands.foundation.IExplicitTransformer;

public class RemapTransformer implements IExplicitTransformer {
    private final String[] fromPrefixes;
    private final String[] toPrefixes;

    public RemapTransformer(String[] fromPrefixes, String[] toPrefixes) {
        this.fromPrefixes = fromPrefixes;
        this.toPrefixes = toPrefixes;
    }

    @Override
    public byte[] transform( byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(0);
        ClassVisitor visitor = new ClassRemapper(writer, new PrefixRemapper(fromPrefixes, toPrefixes));
        try {
            reader.accept(visitor, ClassReader.EXPAND_FRAMES);
        } catch (Exception e) {
            FMLLog.log.warn("Couldn't remap class {}", reader.getClassName(), e);
            return bytes;
        }
        return writer.toByteArray();
    }

    static class PrefixRemapper extends Remapper {
        private final String[] fromPrefixes;
        private final String[] toPrefixes;

        public PrefixRemapper(String[] fromPrefixes, String[] toPrefixes) {
            super(Opcodes.ASM9);
            this.fromPrefixes = fromPrefixes;
            this.toPrefixes = toPrefixes;
        }

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
