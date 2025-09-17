package cc.sunshine.eventbus.impl.packet.impl;

import cc.sunshine.eventbus.impl.packet.PacketEvent;
import cc.sunshine.eventbus.impl.packet.PostPacketEvent;
import net.minecraft.network.packet.Packet;

public final class PostPacketSendEvent extends PostPacketEvent {
    public PostPacketSendEvent(Packet<?> packet) {
        super(packet);
    }
}
