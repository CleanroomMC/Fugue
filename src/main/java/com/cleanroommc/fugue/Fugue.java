package com.cleanroommc.fugue;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	@Instance(Reference.MOD_ID)
	public static Fugue _instance;
    public Fugue() {}

    @NetworkCheckHandler
    public boolean checker(Map<String, String> mods, Side side) {
        return true;
    }

}
