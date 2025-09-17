package cc.sunshine.eventbus.impl.packet;

import cc.sunshine.eventbus.Event;
import net.minecraft.network.packet.Packet;

public abstract class PostPacketEvent extends Event {
    private final Packet<?> packet;

    public PostPacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public <T extends Packet<?>> T getPacket() {
        return (T) packet;
    }
}

