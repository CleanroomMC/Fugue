package com.cleanroommc.fugue.common;

import com.cleanroommc.fugue.Reference;
import com.cleanroommc.fugue.handler.PlayerJoinHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.artifact.versioning.ComparableVersion;

import java.util.Map;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        useMetadata = true,
        version = Reference.MOD_VERSION,
        dependencies = Reference.MOD_DEPENDENCIES,
        acceptableRemoteVersions = "*"
)
public class Fugue {
    
    public static Logger LOGGER = LogManager.getLogger(Reference.MOD_NAME);

    public static boolean isModNewerThan(String modid, String version) {
        var mod = Loader.instance().getIndexedModList().get(modid);
        return new ComparableVersion(mod.getVersion()).compareTo(new ComparableVersion(version)) > 0;
    }
	
	@Instance(Reference.MOD_ID)
	public static Fugue _instance;
    public Fugue() {

    }

    @NetworkCheckHandler
    public boolean checker(Map<String, String> mods, Side side) {
        return true;
    }

}
