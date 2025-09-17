package cc.sunshine.feature.module.impl.world;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.packet.impl.PacketReceiveEvent;
import cc.sunshine.eventbus.impl.packet.impl.PacketSendEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.impl.move.ScaffoldModule;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.feature.property.impl.BooleanProperty;
import cc.sunshine.mixin.interfaces.IClientConnectionMixin;
import cc.sunshine.mixin.interfaces.IPlayerMoveC2SPacketMixin;
import cc.sunshine.utils.packet.PacketUtil;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@ModuleData(name = "Blink", category = ModuleCategory.WORLD, description = "Blinks you", key = GLFW.GLFW_KEY_V)
public class BlinkModule extends AbstractModule {

    private final ConcurrentLinkedQueue<Packet<?>> sentPacketList = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Packet<?>> recievedPacketList = new ConcurrentLinkedQueue<>();
    private final BooleanProperty incoming = new BooleanProperty("Cancel incoming packets (S2C)", true);
    private final BooleanProperty outgoing = new BooleanProperty("Cancel outgoing packets (C2S)", false);



    @Override
    protected void onDisable() {
        sentPacketList.forEach(packet -> {
            PacketUtil.sendPacketNoEvent(packet);
            sentPacketList.remove(packet);
        });

        recievedPacketList.forEach(packet -> {
            PacketUtil.applyPacket(packet);
            recievedPacketList.remove(packet);
        });
        super.onDisable();
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        sentPacketList.clear();
        recievedPacketList.clear();
    }

    @SubscribeEvent
    private final Listener<PacketSendEvent> packetReceiveEventListener = event -> {
        //if(!(event.getPacket() instanceof PlayerInteractBlockC2SPacket)) {
        if(outgoing.getValue()) {
            event.setCancelled(true);
            sentPacketList.add(event.getPacket());
        }
        //}

        /*if(event.getPacket() instanceof PlayerInteractBlockC2SPacket) {
            PlayerInteractBlockC2SPacket interactBlockC2SPacket = event.getPacket();
            mc.world.removeBlock(interactBlockC2SPacket.getBlockHitResult().getBlockPos(), false);
        }*/

    };

    @SubscribeEvent
    private final Listener<PacketReceiveEvent> packetReceiveEventListenerr = event -> {
        if(!(event.getPacket() instanceof TeamS2CPacket)) {
            if (incoming.getValue()) {
                event.setCancelled(true);
                recievedPacketList.add(event.getPacket());
            }
        }
        //event.getPacket().apply(event.getPacket());
    };

}
