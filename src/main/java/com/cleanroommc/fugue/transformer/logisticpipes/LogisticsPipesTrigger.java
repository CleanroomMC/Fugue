package com.cleanroommc.fugue.transformer.logisticpipes;

import com.cleanroommc.fugue.common.Fugue;
import net.minecraft.launchwrapper.Launch;
import top.outlands.foundation.IExplicitTransformer;

public class LogisticsPipesTrigger implements IExplicitTransformer {
    @Override
    public byte[] transform(byte[] bytes) {
        Fugue.LOGGER.info("Loading LP classes...");
        try {
            Launch.classLoader.findClass("logisticspipes.network.packets.routingdebug.RoutingUpdateUntrace");
            Launch.classLoader.findClass("logisticspipes.network.packets.modules.SupplierPipeMode");
            Launch.classLoader.findClass("logisticspipes.network.packets.module.ModuleBasedItemSinkList");
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
            Launch.classLoader.findClass("logisticspipes.network.guis.module.inhand.SneakyModuleInHandGuiProvider");
            Launch.classLoader.findClass("logisticspipes.network.guis.pipe.InvSysConSelectChannelPopupGUIProvider");
            Launch.classLoader.findClass("logisticspipes.network.packets.debuggui.DebugPanelOpen");
            Launch.classLoader.findClass("logisticspipes.network.packets.upgrade.SneakyUpgradeSidePacket");
            Launch.classLoader.findClass("logisticspipes.network.packets.hud.ChestContent");
        } catch (ClassNotFoundException ignored) {}
        return bytes;
    }
}
