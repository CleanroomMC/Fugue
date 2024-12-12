package com.cleanroommc.fugue.config;

import net.minecraftforge.common.config.Config;

public class LogisticsPipesPreloadConfig {
    @Config.Comment("""
            Logistics Pipes is doing some multi-thread custom class loading,
            Which breaks Cleanroom hard. If you encountered any crash with
            IllegalArgumentException, just add the not found class here and
            it should be fixed.
            """)
    public String[] classList = new String[]{
            "logisticspipes.network.packets.routingdebug.RoutingUpdateUntrace",
            "logisticspipes.network.packets.modules.SupplierPipeMode",
            "logisticspipes.network.packets.module.ModuleBasedItemSinkList",
            "logisticspipes.network.packets.pipe.FluidSupplierMinMode",
            "logisticspipes.network.abstractguis.ModuleCoordinatesGuiProvider",
            "logisticspipes.network.guis.upgrade.SneakyUpgradeConfigGuiProvider",
            "logisticspipes.network.packets.block.SecurityCardPacket",
            "logisticspipes.network.packets.debug.PipeDebugLogAskForTarget",
            "logisticspipes.network.packets.block.SecurityAuthorizationPacket",
            "logisticspipes.network.packets.orderer.DiskDropPacket",
            "logisticspipes.network.packets.AddNewChannelPacket",
            "logisticspipes.network.packets.block.SecurityStationCCIDs",
            "logisticspipes.network.guis.block.PowerProviderGui",
            "logisticspipes.network.guis.module.inpipe.FluidSupplierSlot",
            "logisticspipes.network.guis.module.inhand.SneakyModuleInHandGuiProvider",
            "logisticspipes.network.guis.pipe.InvSysConSelectChannelPopupGUIProvider",
            "logisticspipes.network.packets.debuggui.DebugPanelOpen",
            "logisticspipes.network.packets.upgrade.SneakyUpgradeSidePacket",
            "logisticspipes.network.packets.hud.ChestContent",
            "logisticspipes.network.packets.chassis.ChestGuiClosed",
            "logisticspipes.network.guis.LogisticsPlayerSettingsGuiProvider",
            "logisticspipes.network.guis.EditChannelGuiProvider",
            "logisticspipes.network.packets.cpipe.CPipeSatelliteImport",
    };
}
