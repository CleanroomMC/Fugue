package com.cleanroommc.transformer;

import com.cleanroommc.FugueLoadingPlugin;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ClassTransformerTransformer implements IClassTransformer {
    
    public ClassTransformerTransformer(){
        FugueLoadingPlugin.registerToKnownTransformer("advancedrocketry", this);
    }
    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (bytes == null)
        {
            return null;
        }

        if (!s1.equals("zmaster587.advancedRocketry.asm.ClassTransformer"))
        {
            return bytes;
        }


        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean modified = false;
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("transform")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode instanceof LineNumberNode lineNumberNode)
                            {
                                if (lineNumberNode.line == 693) {
                                    instructions.remove(instructions.get(instructions.indexOf(lineNumberNode) + 1));
                                    instructions.insert(lineNumberNode, new InsnNode(Opcodes.ICONST_0));
                                    modified = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (modified)
        {
            Launch.classLoader.unRegisterSuperTransformer(this);
            ClassWriter classWriter = new ClassWriter(0);

            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        return bytes;
    }
}
