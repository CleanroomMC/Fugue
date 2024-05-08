package com.cleanroommc.fugue.common;

import com.cleanroommc.fugue.helper.HookHelper;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.CoreModManager;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;


public class LateBootstrapTweaker implements ITweaker {
    @Override
    public void acceptOptions(List<String> list, File file, File file1, String s) {
        if (Launch.classLoader.isClassExist("com.replaymod.core.tweaker.ReplayModTweaker")) {
            Fugue.LOGGER.info("Manually injecting ReplayMod");
            try {
                URL source = Class.forName("com.replaymod.core.tweaker.ReplayModTweaker").getProtectionDomain().getCodeSource().getLocation();
                String path = HookHelper.toURI(source).toString();
                Method load = CoreModManager.class.getDeclaredMethod("loadCoreMod", LaunchClassLoader.class, String.class, File.class);
                load.setAccessible(true);
                load.invoke(null, Launch.classLoader, "com.replaymod.core.LoadingPlugin", new File(path));
                CoreModManager.getIgnoredMods().remove(path);
                CoreModManager.getReparseableCoremods().add(path);
            } catch (Throwable t){
                Fugue.LOGGER.error(t);
            }
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {

    }

    @Override
    public String getLaunchTarget() {
        return "";
    }

    @Override
    public String[] getLaunchArguments() {
        return new String[0];
    }
}
