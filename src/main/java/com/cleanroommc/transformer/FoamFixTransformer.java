package com.cleanroommc.transformer;

import com.cleanroommc.Fugue;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.LinkedHashMap;

public class FoamFixTransformer implements IClassTransformer {
    private int count = 4;
    private static final LinkedHashMap<String, String> classMap = new LinkedHashMap<>();
    static {
        classMap.put("pl.asie.foamfix.coremod.patches.BlockPosPatch", "patchOtherClass");
        classMap.put("pl.asie.foamfix.coremod.patches.GhostBusterBlockStateAccessPatch", "apply");
        classMap.put("pl.asie.foamfix.coremod.patches.ClassGetSimpleNamePatch", "apply");
        classMap.put("pl.asie.patchy.helpers.ConstructorReplacingTransformer", "apply");
    }
    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (bytes == null)
        {
            return null;
        }

        if (count == 0)
        {
            return bytes;
        }
        
        for (String clazz : classMap.keySet()) {
            if (s1.equals(clazz)) {
                ClassNode classNode = new ClassNode();
                ClassReader classReader = new ClassReader(bytes);
                classReader.accept(classNode, 0);
                boolean modified = false;
                if (classNode.methods != null) {
                    for (MethodNode methodNode : classNode.methods) {
                        if (methodNode.name.equals(classMap.get(clazz))) {
                            
                            InsnList instructions = methodNode.instructions;
                            if (instructions != null) {
                                for (AbstractInsnNode insnNode : instructions) {
                                    if (insnNode instanceof LdcInsnNode ldcInsnNode) {
                                        instructions.insert(ldcInsnNode, new LdcInsnNode(Opcodes.ASM9));
                                        instructions.remove(ldcInsnNode);
                                        modified = true;
                                    }
                                }
                            }
                        }
                    }
                }
                if (modified) {
                    Launch.classLoader.addTransformerExclusion(clazz);
                    count--;
                    ClassWriter classWriter = new ClassWriter(0);

                    classNode.accept(classWriter);
                    return classWriter.toByteArray();
                }
                return bytes;
            }
        }
        return bytes;
    }
}
