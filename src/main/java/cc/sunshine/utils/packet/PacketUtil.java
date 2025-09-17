package cc.sunshine.utils.packet;

import cc.sunshine.mixin.interfaces.IClientConnectionMixin;
import cc.sunshine.mixin.interfaces.IClientPlayerInteractionManagerMixin;
import cc.sunshine.utils.IMinecraft;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;

public final class PacketUtil implements IMinecraft {
    public static void sendPacketNoEvent(Packet<?> packet) {
        ClientConnection connection = mc.getNetworkHandler().getConnection();
        ((IClientConnectionMixin) connection).sendPacketNoEvent(packet, null);
    }

    public static void sendPacketN(Packet<?> packet) {
        ClientConnection connection = mc.getNetworkHandler().getConnection();
        ((IClientConnectionMixin) connection).sendPacket(packet, null);
    }

    public static void applyPacket(Packet<?> packet) {
        ClientConnection clientConnection = mc.getNetworkHandler().getConnection();
        ((IClientConnectionMixin) clientConnection).apply(packet);
    }


    public static void updateSelectedSlot(int slot) {
        mc.player.getInventory().selectedSlot = slot;
        ((IClientPlayerInteractionManagerMixin) mc.interactionManager).syncSlot();
    }
}
