package com.cleanroommc.fugue.transformer.essential;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;

import top.outlands.foundation.IExplicitTransformer;

import java.util.ListIterator;

//Target: [
//      gg.essential.network.connectionmanager.telemetry.TelemetryManager
// ]
public class EssentialTelemetryManagerTransformer implements IExplicitTransformer, Opcodes{
    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (var method : classNode.methods) {
            if ("sendHardwareAndOSTelemetry".equals(method.name)) {
                EssentialSetupTweakerTransformer.clearMethod(method);

                // this.enqueue(new ClientTelemetryPacket("HARDWARE_V2", HookHelper.essential$gatherEnvironmentDetails()))

                method.visitVarInsn(ALOAD, 0);

                method.visitTypeInsn(NEW, "gg/essential/connectionmanager/common/packet/telemetry/ClientTelemetryPacket");
                method.visitInsn(DUP);
                method.visitLdcInsn("HARDWARE_V2");
                method.visitMethodInsn(INVOKESTATIC, "com/cleanroommc/fugue/helper/HookHelper", "essential$gatherEnvironmentDetails", "()Ljava/util/HashMap;", false);
                method.visitMethodInsn(INVOKESPECIAL, "gg/essential/connectionmanager/common/packet/telemetry/ClientTelemetryPacket", "<init>", "(Ljava/lang/String;Ljava/util/Map;)V", false);
                
                method.visitMethodInsn(INVOKEVIRTUAL, "gg/essential/network/connectionmanager/telemetry/TelemetryManager", "enqueue", "(Lgg/essential/connectionmanager/common/packet/telemetry/ClientTelemetryPacket;)V", false);
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
}