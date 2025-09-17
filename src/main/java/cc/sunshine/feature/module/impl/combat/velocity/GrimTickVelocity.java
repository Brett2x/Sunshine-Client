package cc.sunshine.feature.module.impl.combat.velocity;

import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.impl.packet.impl.PacketReceiveEvent;
import cc.sunshine.eventbus.impl.player.PlayerNetworkUpdateEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.impl.combat.VelocityModule;
import cc.sunshine.feature.module.mode.AbstractModuleMode;
import cc.sunshine.utils.packet.PacketUtil;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public final class GrimTickVelocity extends AbstractModuleMode<VelocityModule> {
    public GrimTickVelocity(VelocityModule module, String name) {
        super(module, name);
    }

    private boolean shouldSend;

    @Override
    public void onEnable() {
        shouldSend = false;
    }

    @SubscribeEvent
    public Listener<PacketReceiveEvent> onPacketReceive = event -> {
        if(event.getPacket() instanceof EntityVelocityUpdateS2CPacket s12PacketEntityVelocity) {
            if (s12PacketEntityVelocity.getEntityId() == mc.player.getId()) {
                shouldSend = true;
            }
        }

        if(event.getPacket() instanceof ExplosionS2CPacket) {
            shouldSend = true;
            //event.setCancelled(true);
        }
    };

    @SubscribeEvent
    public Listener<PlayerNetworkUpdateEvent> onStrafe = event -> {
        if(shouldSend) {
            float yaw = (float) Math.toRadians(mc.player.getYaw());

            //if(vulcanC0A.getValue()) PacketUtil.send(new C0APacketAnimation());

            //event.setPosition(new Vec3d(Math.sin(yaw) * 29999999, mc.player.getY(), mc.player.getZ()));
            //PacketUtil.sendPacketNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos(), Direction.UP));
            //PacketUtil.sendPacketNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, mc.player.getBlockPos(), Direction.UP));
            shouldSend = false;
        }
    };

}
