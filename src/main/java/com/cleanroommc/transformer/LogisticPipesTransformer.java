package com.cleanroommc.transformer;

import com.cleanroommc.FugueLoadingPlugin;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class LogisticPipesTransformer implements IClassTransformer {
    public LogisticPipesTransformer(){
        FugueLoadingPlugin.registerToKnownTransformer("logisticspipes", this);
    }
    private static int hit = 0;
    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (bytes == null)
        {
            return null;
        }
        
        int match = 0;
        if (s1.equals("logisticspipes.asm.mcmp.ClassBlockMultipartContainerHandler"))
        {
            match = 1;
        }
        if (s1.equals("logisticspipes.asm.td.ClassRenderDuctItemsHandler"))
        {
            match = 1;
        }
        if (s1.equals("logisticspipes.asm.td.ClassTravelingItemHandler"))
        {
            match = 3;
        }
        if (match == 0)
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
                if (methodNode.name.equals("handleClass") || methodNode.name.equals("handleRenderDuctItemsClass")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.ICONST_1 && insnNode instanceof InsnNode iConstNode)
                            {
                                instructions.insert(iConstNode, new InsnNode(Opcodes.ICONST_0));
                                instructions.remove(iConstNode);
                                match--;
                                if (match == 0) {
                                    modified = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (modified)
        {
            hit++;
            if (hit == 3) {
                Launch.classLoader.unRegisterSuperTransformer(this);
            }
            ClassWriter classWriter = new ClassWriter(0);

            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        return bytes;
    }
}
