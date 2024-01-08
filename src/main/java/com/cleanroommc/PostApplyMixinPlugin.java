package com.cleanroommc;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class PostApplyMixinPlugin implements IMixinConfigPlugin {
    private static boolean hit = false;
    @Override
    public void onLoad(String mixinPackage) {
        
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if (hit || !targetClassName.equals("net.minecraft.client.renderer.BlockFluidRenderer")) {
            return;
        }
        for (MethodNode methodNode : targetClass.methods) {
            if (methodNode != null) {
                InsnList insnList = methodNode.instructions;
                if (insnList != null)
                    for (AbstractInsnNode abstractInsnNode : insnList) {
                        if (abstractInsnNode instanceof MethodInsnNode methodInsnNode) {
                            if (methodInsnNode.owner.startsWith("org/spongepowered/asm/synthetic/args/Args$")) {
                                if (methodInsnNode.name.equals("of")) {
                                    methodInsnNode.owner = "com/cleanroommc/helper/ArgsHelper";
                                    methodInsnNode.desc = "(FFFF)Lcom/cleanroommc/helper/ArgsHelper;";
                                }
                                if (methodInsnNode.name.startsWith("$")) {
                                    int index = Integer.parseInt(methodInsnNode.name.substring(1));
                                    if (index < 6) {
                                        insnList.insertBefore(methodInsnNode, new InsnNode(Opcodes.ICONST_0 + index));
                                    } else {
                                        insnList.insertBefore(methodInsnNode, new LdcInsnNode(index));
                                    }
                                    insnList.insert(methodInsnNode, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F"));
                                    insnList.insert(methodInsnNode, new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Float"));
                                    methodInsnNode.owner = "com/cleanroommc/helper/ArgsHelper";
                                    methodInsnNode.desc = "(I)Ljava/lang/Object;";
                                    methodInsnNode.name = "get";
                                    hit = true;
                                }
                            }
                        }
                    }
            }
        }
    }
}
