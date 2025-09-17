package cc.sunshine.feature.module.impl.combat.velocity;

import cc.sunshine.eventbus.impl.packet.impl.PacketReceiveEvent;
import cc.sunshine.eventbus.impl.player.PlayerStrafeEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.impl.combat.VelocityModule;
import cc.sunshine.feature.module.mode.AbstractModuleMode;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityDamageS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.math.Direction;

public class GrimVelocity extends AbstractModuleMode<VelocityModule> {

    private boolean send, realVelocity;

    @SubscribeEvent
    private final Listener<PacketReceiveEvent> packetReceiveEventListener = event -> {
        if(mc.world == null || mc.player == null || mc.player.isInLava()) {
            return;
        }

        if(event.getPacket() instanceof EntityDamageS2CPacket) {
            EntityDamageS2CPacket packet = event.getPacket();

            if(packet.entityId() != mc.player.getId()) {
                return;
            }

            realVelocity = true;
        }

        if(event.getPacket() instanceof EntityVelocityUpdateS2CPacket && realVelocity) {
            EntityVelocityUpdateS2CPacket packet = event.getPacket();

            if(packet.getEntityId() != mc.player.getId()) {
                return;
            }

            event.cancel();
            realVelocity = false;
            send = true;
        }
    };

    @SubscribeEvent
    private final Listener<PlayerStrafeEvent> onStrafe = event -> {
        if (mc.player.hurtTime > 0 && send) {
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos(), Direction.DOWN));

            send = false;
        }
    };

    public GrimVelocity(VelocityModule module, String name) {
        super(module, name);
    }
}
