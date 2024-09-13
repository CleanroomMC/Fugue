package com.cleanroommc.fugue.helper;

import com.cleanroommc.fugue.common.Fugue;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.objectweb.asm.Opcodes;
import top.outlands.foundation.TransformerDelegate;
import top.outlands.foundation.boot.ActualClassLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.*;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.function.Function;

public class HookHelper {
    public static boolean isInterface(int opcode) {
        return opcode == Opcodes.INVOKEINTERFACE;
    }

    public static List<IClassTransformer> transformers;
    private static final Map<String, Class<?>> grsMap = new HashMap<>();

    @SuppressWarnings("deprecation")
    private static String byGetResource() {
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
        URLConnection connection = url.openConnection();
        if (connection instanceof JarURLConnection jarURLConnection) {
            return jarURLConnection.getJarFileURL().toURI();
        } else  {
            return url.toURI();
        }
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
        if (!clazz.equals(LaunchClassLoader.class)) {
            return clazz.getDeclaredField(name);
        }
        if (name.equals("transformers") || name.equals("renameTransformer")) {
            HookHelper.transformers = TransformerDelegate.getTransformers();
            return HookHelper.class.getDeclaredField(name);
        } else {
            return ActualClassLoader.class.getDeclaredField(name);
        }
    }

    public static Class<?> defineClass(String name, byte[] bytes, int off, int len, CodeSource codeSource) {
        Fugue.LOGGER.info("Defining class: {}", name);
        if (grsMap.containsKey(name)) {
            return grsMap.get(name);
        } else {
            return grsMap.computeIfAbsent(name, k -> Launch.classLoader.defineClass(k, bytes, codeSource));
        }
    }

    public static Class<?> defineClass(String name, byte[] bytes, int off, int len, ProtectionDomain domain) {
        Fugue.LOGGER.info("Defining class: {}", name);
        if (grsMap.containsKey(name)) {
            return grsMap.get(name);
        } else {
            return grsMap.computeIfAbsent(name, k -> Launch.classLoader.defineClass(k, bytes));
        }
    }

    public static Class<?> defineClass(String name, byte[] bytes) {
        Fugue.LOGGER.info("Defining class: {}", name);
        if (grsMap.containsKey(name)) {
            return grsMap.get(name);
        } else {
            return grsMap.computeIfAbsent(name, k -> Launch.classLoader.defineClass(k, bytes));
        }
    }

    public static Class<?> defineClass(byte[] bytes, String name) {
        Fugue.LOGGER.info("Defining class: {}", name);
        if (grsMap.containsKey(name)) {
            return grsMap.get(name);
        } else {
            return grsMap.computeIfAbsent(name, k -> Launch.classLoader.defineClass(k, bytes));
        }

    }

    public static void logCL(ClassLoader parent) {
        Fugue.LOGGER.info("ClassLoader: {}", parent);
    }

    public static Class<?> loadClass(String name) {
        Class<?> c = null;
        try {
            c = Launch.classLoader.findClass(name);
        } catch (ClassNotFoundException | NoClassDefFoundError ignored) {}
        return c;
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

    public static byte[] transformAsPureFML(String name, String transformedName, byte[] basicClass) {
        for (IClassTransformer transformer : TransformerDelegate.getTransformers()) {
            boolean allowed = (transformer instanceof net.minecraftforge.fml.common.asm.transformers.PatchingTransformer || transformer instanceof net.minecraftforge.fml.common.asm.transformers.SideTransformer || transformer instanceof net.minecraftforge.fml.common.asm.transformers.SoundEngineFixTransformer || transformer instanceof net.minecraftforge.fml.common.asm.transformers.DeobfuscationTransformer || transformer instanceof net.minecraftforge.fml.common.asm.transformers.AccessTransformer || transformer instanceof net.minecraftforge.fml.common.asm.transformers.FieldRedirectTransformer || transformer instanceof net.minecraftforge.fml.common.asm.transformers.TerminalTransformer || transformer instanceof net.minecraftforge.fml.common.asm.transformers.ModAPITransformer);
            if (!allowed)
                continue;
            basicClass = transformer.transform(name, transformedName, basicClass);
        }
        return basicClass;
    }

    public static void TickCentralPreLoad() {
        List<String> list = new ArrayList<>() {{
            add("com.github.terminatornl.tickcentral.asm.BlockTransformer");
            add("com.github.terminatornl.tickcentral.asm.ITickableTransformer");
            add("com.github.terminatornl.tickcentral.asm.EntityTransformer");
            add("com.github.terminatornl.tickcentral.asm.HubAPITransformer");
            add("net.minecraftforge.fml.common.asm.transformers.ModAPITransformer");
            add("com.github.terminatornl.tickcentral.api.ClassSniffer");
        }};
        try {
            for (var s : list) {
                Launch.classLoader.findClass(s);
            }
        } catch (ClassNotFoundException ignored) {
        }
    }

    public static boolean isClassExist(String clazz) {
        return Launch.classLoader.isClassExist(clazz);
    }

    public static Object computeIfAbsent(Map<Object, Object> map, Object key, Function<Object, Object> function) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            Object value = function.apply(key);
            map.put(key, value);
            return value;
        }
    }

    public static boolean isInitialized(File file) {
        try {
            return Arrays.asList(Launch.classLoader.getURLs()).contains(file.toURI().toURL());
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static void addToClasspath(File file) {
        try {
            Launch.classLoader.addURL(file.toURI().toURL());
        } catch (MalformedURLException ignored) {}
    }
}
