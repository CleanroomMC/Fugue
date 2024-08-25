package com.cleanroommc.fugue.transformer.logisticpipes;

import net.minecraft.launchwrapper.Launch;
import top.outlands.foundation.IExplicitTransformer;

public class LogisticsPipesTrigger implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        try {
            Launch.classLoader.findClass("logisticspipes.network.packets.routingdebug.RoutingUpdateUntrace");
            Launch.classLoader.findClass("logisticspipes.network.packets.modules.SupplierPipeMode");
            Launch.classLoader.findClass("logisticspipes.network.packets.pipe.FluidSupplierMinMode");
            Launch.classLoader.findClass("logisticspipes.network.abstractguis.ModuleCoordinatesGuiProvider");
            Launch.classLoader.findClass("logisticspipes.network.guis.upgrade.SneakyUpgradeConfigGuiProvider");
        } catch (ClassNotFoundException ignored) {}
        return bytes;
    }
}
