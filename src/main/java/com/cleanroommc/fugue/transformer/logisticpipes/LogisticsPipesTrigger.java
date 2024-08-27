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
            Launch.classLoader.findClass("logisticspipes.network.packets.block.SecurityCardPacket");
            Launch.classLoader.findClass("logisticspipes.network.packets.debug.PipeDebugLogAskForTarget");
            Launch.classLoader.findClass("logisticspipes.network.packets.block.SecurityAuthorizationPacket");
            Launch.classLoader.findClass("logisticspipes.network.packets.orderer.DiskDropPacket");
            Launch.classLoader.findClass("logisticspipes.network.packets.AddNewChannelPacket");
            Launch.classLoader.findClass("logisticspipes.network.packets.block.SecurityStationCCIDs");
            Launch.classLoader.findClass("logisticspipes.network.guis.block.PowerProviderGui");
            Launch.classLoader.findClass("logisticspipes.network.guis.module.inpipe.FluidSupplierSlot");
        } catch (ClassNotFoundException ignored) {}
        return bytes;
    }
}
