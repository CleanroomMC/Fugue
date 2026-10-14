package com.cleanroommc.fugue.config;

import com.cleanroommc.fugue.Reference;
import net.minecraftforge.common.config.Config;

import java.util.HashMap;
import java.util.Map;

@Config(modid = Reference.MOD_ID, name = Reference.MOD_ID)
@Config.RequiresMcRestart
public class FugueConfig {
    @Config.Comment("""
        Fix and patches for certain mods.
        WARNING: Enable too much patches may lower performance.
        If you are a pack maker, just enable what you need.
        """)
    public static ModPatchConfig modPatchConfig = new ModPatchConfig();

    @Config.Comment("""
        Non-Update was gone with Security Manager.
        As a workaround, These targets will be banned from making connections with URL.openStream().
        If you don't need a proxy to access github, you could empty this setting.
        The Secret Room entry should be kept - the url now points to an 404 page which will crash the game.
        This may block more connection than update checks, so if anything gone wrong please open an issue.\
        """)
    @Config.Name("Connection Blocking List")
    public static String[] nonUpdateTargets = new String[] {
        "xxrexraptorxx.customizeddungeonloot.util.UpdateChecker$1",
        "com.nekokittygames.mffs.common.Versioninfo",
        "me.ichun.mods.ichunutil.common.thread.ThreadGetResources",
        "com.buuz135.industrial.proxy.CommonProxy",
        "micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore",
        "vazkii.quark.base.client.ContributorRewardHandler$ThreadContributorListLoader",
        "com.wynprice.secretroomsmod.handler.HandlerUpdateChecker",
    };

    @Config.Comment("Use this when you encountered ClassCircularityError.")
    @Config.Name("Extra Transform Exclusion")
    public static String[] extraTransformExclusions = new String[] {
        "org.vivecraft.",
    };

    @Config.Comment("""
        Target field's final modifier will be removed and force to be public. No checks will be preformed before removal.
        All fields with same name will be targeted.
        Format: S:"foo.bar.classname"=field1|filed2
        """)
    @Config.Name("Final Fields Patching List")
    public static Map<String, String> finalRemovingTargets = new HashMap<>() {
        {
            // VintageFix
            put("net.minecraftforge.event.terraingen.BiomeEvent$BiomeColor", "originalColor");
            // Snow Real Magic
            put("net.minecraft.item.ItemBlock", "field_150939_a"); // block
            // EntityDistance
            put("net.minecraft.client.gui.GuiOptions", "field_146441_g|field_146443_h"); // lastScreen|settings
            put(
                    "net.minecraft.entity.EntityTracker",
                    "field_72793_b|field_72794_c|field_72795_a"); // entries|trackedEntityHashTable|world
            put(
                    "net.minecraft.entity.EntityTrackerEntry",
                    "field_73130_b|field_73132_a|field_187262_f|field_73131_c|field_73143_t"); // range|trackedEntity|maxRange|updateFrequency|sendVelocityUpdates
            put("net.minecraft.world.World", "field_72996_f"); // loadedEntityList
            put("net.minecraft.client.renderer.EntityRenderer", "field_78504_Q");
            put("meldexun.nothirium.api.renderer.chunk.ChunkRenderPass", "ALL"); // For nothirium + gtceu
            put("com.mrcrayfish.vehicle.crafting.VehicleRecipes", "RECIPES");
        }
    };
}
