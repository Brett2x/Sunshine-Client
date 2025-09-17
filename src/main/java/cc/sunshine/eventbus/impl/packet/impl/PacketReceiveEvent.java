package cc.sunshine.eventbus.impl.packet.impl;

import cc.sunshine.eventbus.impl.packet.PacketEvent;
import net.minecraft.network.packet.Packet;

public final class PacketReceiveEvent extends PacketEvent {
    public PacketReceiveEvent(Packet<?> packet) {
        super(packet);
    }
}
