package com.cleanroommc.fugue.transformer.essential;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

//Target: [
//      gg.essential.loader.stage0.EssentialSetupTweaker
//      gg.essential.loader.stage1.EssentialLoader
//      gg.essential.loader.stage2.EssentialLoader
//      gg.essential.main.Bootstrap
// ]
public class EssentialSetupTweakerTransformer implements IExplicitTransformer, Opcodes {

    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (var method : classNode.methods) {
            // Overwrite -- Actual Redirect
            if ("addUrlHack".equals(method.name)) {
                // clear the method
                clearMethod(method);

                //do not inject into app cl
                // method.visitVarInsn(ALOAD, 0);
                // method.visitVarInsn(ALOAD, 1);
                // method.visitMethodInsn(INVOKESTATIC, "com/cleanroommc/fugue/helper/HookHelper", "addURL", "(Ljava/lang/ClassLoader;Ljava/net/URL;)V", false);
                method.visitInsn(RETURN);
            }

            // Overwrite -- to complex to inject
            else if ("addToClasspath".equals(method.name) && "(Ljava/nio/file/Path;)V".equals(method.desc)) {
                // clear the method
                clearMethod(method);

                // URL url = path.toUri().toURL(); -- 2
                method.visitVarInsn(ALOAD, 1);
                method.visitMethodInsn(INVOKEINTERFACE, "java/nio/file/Path", "toUri", "()Ljava/net/URI;", true);
                method.visitMethodInsn(INVOKEVIRTUAL, "java/net/URI", "toURL", "()Ljava/net/URL;", false);
                method.visitVarInsn(ASTORE, 2);

                // LaunchClassLoader lcl = Launch.classLoader; -- 3
                method.visitFieldInsn(GETSTATIC, "net/minecraft/launchwrapper/Launch", "classLoader", "Lnet/minecraft/launchwrapper/LaunchClassLoader;");
                method.visitVarInsn(ASTORE, 3);

                // lcl.addURL(url);
                method.visitVarInsn(ALOAD, 3);
                method.visitVarInsn(ALOAD, 2);
                method.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/launchwrapper/LaunchClassLoader", "addURL", "(Ljava/net/URL;)V", false);
                
                 //do not inject into app cl
                // // HookHelper.addURL(lcl.getClass().getClassLoader(), url);
                // method.visitVarInsn(ALOAD, 3);
                // method.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
                // method.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getClassLoader", "()Ljava/lang/ClassLoader;", false);
                // method.visitVarInsn(ALOAD, 2);
                // method.visitMethodInsn(INVOKESTATIC, "com/cleanroommc/fugue/helper/HookHelper", "addURL", "(Ljava/lang/ClassLoader;Ljava/net/URL;)V", false);
                
                //this.ourEssentialPath = path;
                method.visitVarInsn(ALOAD, 0);
                method.visitVarInsn(ALOAD, 1);
                method.visitFieldInsn(PUTFIELD, "gg/essential/loader/stage2/EssentialLoader", "ourEssentialPath", "Ljava/nio/file/Path;");

                //this.ourEssentialUrl = url;
                method.visitVarInsn(ALOAD, 0);
                method.visitVarInsn(ALOAD, 2);
                method.visitFieldInsn(PUTFIELD, "gg/essential/loader/stage2/EssentialLoader", "ourEssentialUrl", "Ljava/net/URL;");

                //this.ourMixinUrl = url;
                method.visitVarInsn(ALOAD, 0);
                method.visitVarInsn(ALOAD, 2);
                method.visitFieldInsn(PUTFIELD, "gg/essential/loader/stage2/EssentialLoader", "ourMixinUrl", "Ljava/net/URL;");
                
                method.visitInsn(RETURN);
            } else if ("getModClassLoader".equals(method.name)) {
                clearMethod(method);
                method.visitFieldInsn(GETSTATIC, "net/minecraft/launchwrapper/Launch", "classLoader", "Lnet/minecraft/launchwrapper/LaunchClassLoader;");
                method.visitInsn(ARETURN);
            } else if ("workaroundThreadUnsafeTransformerList".equals(method.name)) {
                clearMethod(method);
                method.visitInsn(RETURN);
            }

        }

        var classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    @Override
    public int getPriority() {
        return 0;
    }

    static void clearMethod(MethodNode method) {
        if (method.instructions != null) method.instructions.clear();
        if (method.tryCatchBlocks != null) method.tryCatchBlocks.clear();
        if (method.localVariables != null) method.localVariables.clear();
        if (method.visibleLocalVariableAnnotations != null) method.visibleLocalVariableAnnotations.clear();
        if (method.invisibleLocalVariableAnnotations != null) method.invisibleLocalVariableAnnotations.clear();
    }
}