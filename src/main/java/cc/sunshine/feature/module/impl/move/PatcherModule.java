package cc.sunshine.feature.module.impl.move;

import cc.sunshine.eventbus.impl.packet.impl.PacketReceiveEvent;
import cc.sunshine.eventbus.impl.packet.impl.PacketSendEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PickFromInventoryC2SPacket;

@ModuleData(name = "Patcher", description = "Patches Anticheat checks", category = ModuleCategory.MOVE)
public class PatcherModule extends AbstractModule {

    @SubscribeEvent
    private final Listener<PacketReceiveEvent> packetReceiveEventListener = event -> {

    };

    @SubscribeEvent
    private final Listener<PacketSendEvent> packetSendEventListener = event -> {

    };

}
