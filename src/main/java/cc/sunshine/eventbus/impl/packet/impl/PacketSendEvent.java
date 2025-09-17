package cc.sunshine.eventbus.impl.packet.impl;

import cc.sunshine.eventbus.impl.packet.PacketEvent;
import net.minecraft.network.packet.Packet;

public final class PacketSendEvent extends PacketEvent {
    public PacketSendEvent(Packet<?> packet) {
        super(packet);
    }
}
