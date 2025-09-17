package cc.sunshine.eventbus.impl.packet;

import cc.sunshine.eventbus.CancellableEvent;
import net.minecraft.network.packet.Packet;

public abstract class PacketEvent extends CancellableEvent {
    private Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public <T extends Packet<?>> T getPacket() {
        return (T) packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }
}
