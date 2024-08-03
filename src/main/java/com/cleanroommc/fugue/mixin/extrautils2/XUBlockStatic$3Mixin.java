package com.cleanroommc.fugue.mixin.extrautils2;

import com.rwtema.extrautils2.backend.XUBlock;
import com.rwtema.extrautils2.backend.XUBlockStatic;
import com.rwtema.extrautils2.backend.model.MutableModel;
import com.rwtema.extrautils2.backend.model.PassthruModelBlock;
import com.rwtema.extrautils2.backend.model.Transforms;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.*;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;

/**
 * @author ZZZank
 */
@Mixin(targets = "com.rwtema.extrautils2.backend.XUBlockStatic$3", remap = false)
public abstract class XUBlockStatic$3Mixin extends PassthruModelBlock {
    @Shadow
    @Final
    XUBlockStatic this$0;

    @Shadow
    HashMap<IBlockState, HashMap<EnumFacing, HashMap<BlockRenderLayer, List<BakedQuad>>>> cachedLists;

    @Unique
    private final Object fugue$lock = new Object();

    private XUBlockStatic$3Mixin(XUBlock block, IBlockState key, ModelResourceLocation modelResourceLocation) {
        super(block, key, modelResourceLocation);
    }

    /**
     * @author ZZZank
     * @reason original implementation forces the usage of {@link HashMap}, which is NOT concurrency-safe
     */
    @Overwrite
    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        synchronized (fugue$lock) {
            return cachedLists
                .computeIfAbsent(state, XUBlockStatic::dummyCreateHash)
                .computeIfAbsent(side, XUBlockStatic::dummyCreateHash)
                .computeIfAbsent(MinecraftForgeClient.getRenderLayer(), (layer) -> {
                    MutableModel model = new MutableModel(Transforms.blockTransforms);
                    this$0.cachedModels.get(state).loadIntoMutable(model, layer);
                    return model.getQuads(state, side, rand);
                });
        }
    }
}
