package com.cleanroommc.transformer;

import com.cleanroommc.FugueLoadingPlugin;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class EnumInputClassTransformer implements IClassTransformer {
    public EnumInputClassTransformer() {
        FugueLoadingPlugin.registerToKnownTransformer("enchcontrol", this);
    }
    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (bytes == null)
        {
            return null;
        }

        if (!s1.equals("austeretony.enchcontrol.common.core.EnumInputClass"))
        {
            return bytes;
        }

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean firstPatched = false;
        boolean modified = false;
        if (classNode.methods != null)
        {
            for (MethodNode methodNode : classNode.methods)
            {
                if (methodNode.name.equals("pathcMCLocale")) {
                    InsnList instructions = methodNode.instructions;
                    if (instructions != null)
                    {
                        for (AbstractInsnNode insnNode : instructions)
                        {
                            if (insnNode.getOpcode() == Opcodes.SIPUSH && insnNode instanceof IntInsnNode intInsnNode)
                            {
                                if (intInsnNode.operand == Opcodes.INVOKESPECIAL)
                                {
                                    intInsnNode.operand = Opcodes.INVOKEVIRTUAL;
                                    firstPatched = true;
                                }
                            }
                            if (firstPatched && insnNode.getOpcode() == Opcodes.ICONST_3 && insnNode instanceof InsnNode insnNode1)
                            {
                                instructions.insert(insnNode1, new InsnNode(Opcodes.ICONST_2));
                                instructions.remove(insnNode1);
                                modified = true;
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
