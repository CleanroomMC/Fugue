package com.cleanroommc.fugue.transformer.essential;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import top.outlands.foundation.IExplicitTransformer;

import java.util.ListIterator;

//Target: [
//      gg.essential.gui.overlay.OverlayManagerImpl$GlobalMouseOverride
// ]
public class EssentialGlobalMouseOverrideTransformer implements IExplicitTransformer{

    @Override
    public byte[] transform(byte[] bytes) {
        var classReader = new ClassReader(bytes);
        var classNode = new ClassNode();
        classReader.accept(classNode, 0);

        for (var method : classNode.methods) {
            if ("<clinit>".equals(method.name)) {
                ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() instanceof LdcInsnNode ldcInsnNode) {
                        if (ldcInsnNode.cst instanceof String str) {
                            if ("event_x".equals(str)) ldcInsnNode.cst = "lastEventX"; // Better api ?
                            else if ("event_y".equals(str)) ldcInsnNode.cst = "lastEventY";
                        }
                    }
                }
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