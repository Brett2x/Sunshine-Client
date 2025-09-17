package cc.sunshine.mixin.interfaces;

import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;

public interface IClientConnectionMixin {
    void sendPacketNoEvent(Packet<?> packet, PacketCallbacks callbacks);
    void sendPacket(Packet<?> packet, PacketCallbacks callbacks);
    void apply(Packet<?> listener);
}
