package com.cleanroommc.helper;

import scala.tools.asm.Opcodes;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HookHelper {
    public static boolean isInterface (int opcode) {
        return opcode == Opcodes.INVOKEINTERFACE;
    }

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
}
