package com.cleanroommc.fugue.transformer.universal;

import com.cleanroommc.fugue.common.Fugue;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.util.*;

public class FinalStripperTransformer implements IExplicitTransformer {
    private final Map<String, String> targets;
    public FinalStripperTransformer(Map<String, String> targets) {
        this.targets = targets;
    }
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        Set<String> fields = Set.of(targets.get(classNode.name.replace('/', '.')).split("\\|"));
        if (classNode.methods != null)
        {
            for (FieldNode fieldNode : classNode.fields)
            {
                if (fields.contains(fieldNode.name)) {
                    fieldNode.access &= ~Opcodes.ACC_FINAL;
                    Fugue.LOGGER.debug("Stripping final modifier of {} from {}", fieldNode.name, classNode.name.replace('/', '.'));
                }
            }
        }
        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
