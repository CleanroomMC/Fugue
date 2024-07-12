package com.cleanroommc.fugue.transformer;

import net.minecraftforge.fml.common.FMLLog;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import top.outlands.foundation.IExplicitTransformer;

public class EarsASMTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] basicClass) {
        ClassReader reader = new ClassReader(basicClass);
        ClassWriter writer = new ClassWriter(0);
        ClassVisitor visitor = new ClassRemapper(writer, new ASMRemapper());
        try {
            reader.accept(visitor, ClassReader.EXPAND_FRAMES);
        } catch (Exception e) {
            FMLLog.log.warn("Couldn't remap class {}", reader.getClassName(), e);
            return basicClass;
        }
        return writer.toByteArray();
    }

    static class ASMRemapper extends Remapper {
        final String fromPrefixes = "com/unascribed/ears/common/agent/mini/asm/";

        final String toPrefixes = "org/objectweb/asm/";

        @Override
        public String map(String typeName) {
            if (typeName == null) {
                return null;
            }
            if (typeName.startsWith(fromPrefixes)) {
                return toPrefixes + typeName.substring(fromPrefixes.length());
            }


            return typeName;
        }
    }
}
