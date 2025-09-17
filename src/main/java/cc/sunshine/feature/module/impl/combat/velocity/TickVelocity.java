package cc.sunshine.feature.module.impl.combat.velocity;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.impl.packet.impl.PacketReceiveEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.impl.combat.VelocityModule;
import cc.sunshine.feature.module.mode.AbstractModuleMode;
import cc.sunshine.feature.property.impl.IntProperty;
import cc.sunshine.utils.player.MoveUtil;
import net.minecraft.network.packet.s2c.play.EntityDamageS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;

public class TickVelocity extends AbstractModuleMode<VelocityModule> {
    public TickVelocity(VelocityModule module, String name) {
        super(module, name);
    }

    private boolean cancel;

    @SubscribeEvent
    private final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        if(mc.world == null || mc.player == null || mc.player.isInLava()) {
            return;
        }

        if(event.getPacket() instanceof EntityVelocityUpdateS2CPacket ||
                event.getPacket() instanceof EntityDamageS2CPacket) {
            if (cancel) {
                event.cancel();

                cancel = false;

            }
        }
    };

    @SubscribeEvent
    private final Listener<PlayerUpdateEvent> onPlayerUpdate = event -> {
        if(mc.world == null || mc.player == null || mc.player.isInLava()) {
            return;
        }

        if(mc.player.hurtTime >= 3) {
            mc.player.setMovementSpeed(0);
            cancel = true;
        }
    };
}
