package com.cleanroommc.fugue.transformer.saoui;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import top.outlands.foundation.IExplicitTransformer;

/**
 * Old:
 * <pre>
 *     {@code @XmlRootElement(namespace = "http://www.bluexin.be/com/saomc/saoui/hud-schema")}
 * </pre>
 * New:
 * <pre>
 *     {@code @XmlRootElement(namespace = "http://www.bluexin.be/com/saomc/saoui/hud-schema", name = "hud")}
 * </pre>
 *
 * @see <a href="https://stackoverflow.com/a/22232188">stackoverflow solution</a>
 */
public class HudTransformer implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        for (AnnotationNode annotation : classNode.visibleAnnotations) {
            if (annotation.desc.contains("XmlRootElement")) {
                annotation.values.add("name");
                annotation.values.add("hud");
            }
        }
        ClassWriter classWriter = new ClassWriter(0);

        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
