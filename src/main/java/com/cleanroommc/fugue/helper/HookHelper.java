package com.cleanroommc.fugue.helper;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.objectweb.asm.Opcodes;
import top.outlands.foundation.TransformerDelegate;
import top.outlands.foundation.boot.ActualClassLoader;
import top.outlands.foundation.boot.JVMDriverHolder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.*;
import java.nio.file.Paths;
import java.util.List;

public class HookHelper {
    public static boolean isInterface (int opcode) {
        return opcode == Opcodes.INVOKEINTERFACE;
    }

    public static List<IClassTransformer> transformers;

    @SuppressWarnings("deprecation")
    public static String byGetResource() {
        Class<?> clazz = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
        URL classResource = clazz.getResource(clazz.getSimpleName() + ".class");
        if (classResource == null) {
            throw new RuntimeException("class resource is null");
        }
        String url = classResource.toString();
        if (url.startsWith("jar:file:")) {
            // extract 'file:......jarName.jar' part from the url string
            String path = url.replaceAll("^jar:(file:.*[.]jar)!/.*", "$1");
            try {
                return Paths.get(new URL(path).toURI()).toString();
            } catch (Exception e) {
                throw new RuntimeException("Invalid Jar File URL String");
            }
        }
        throw new RuntimeException("Invalid Jar File URL String");
    }

    public static URI toURI(URL url) throws IOException, URISyntaxException {
        return  ((JarURLConnection)url.openConnection()).getJarFileURL().toURI();
    }

    public static List<IClassTransformer> getTransformers() {
        return TransformerDelegate.getTransformers();
    }
    public static final URL deadLink;

    static {
        try {
            deadLink = new URI("file:/dev/noupdate$").toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream open(URL instance) throws IOException {
        throw new IOException("Connection blocked by Fugue!");
    }

    public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
        if (!clazz.equals(LaunchClassLoader.class)) return JVMDriverHolder.findField(clazz, name);
        if (name.equals("transformers") || name.equals("renameTransformer")) {
            HookHelper.transformers = TransformerDelegate.getTransformers();
            return HookHelper.class.getDeclaredField(name);
        } else {
            return ActualClassLoader.class.getDeclaredField(name);
        }
    }


    public static byte[] redirectGetClassByte(LaunchClassLoader instance, String s) throws IOException {
        byte[] bytes = null;
        boolean failed = false;
        try {
            bytes = instance.getClassBytes(s);
        } catch (IOException e) {
            failed = true;
        }
        if (failed || bytes == null) {
            bytes = instance.findResource(s.replace(".", "/")).openStream().readAllBytes();
        }
        return bytes;
    }
}
