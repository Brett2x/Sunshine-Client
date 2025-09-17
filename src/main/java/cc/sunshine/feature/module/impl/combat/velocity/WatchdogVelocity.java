package cc.sunshine.feature.module.impl.combat.velocity;

import cc.sunshine.eventbus.impl.packet.impl.PacketReceiveEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.impl.combat.VelocityModule;
import cc.sunshine.feature.module.mode.AbstractModuleMode;
import cc.sunshine.mixin.interfaces.IEntityVelocityUpdateS2CPacketMixin;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

import javax.swing.text.html.parser.Entity;

public class WatchdogVelocity extends AbstractModuleMode<VelocityModule> {
    public WatchdogVelocity(VelocityModule module, String name) {
        super(module, name);
    }

    @SubscribeEvent
    private final Listener<PacketReceiveEvent> eventListener = event -> {
        if(event.getPacket() instanceof EntityVelocityUpdateS2CPacket) {
            EntityVelocityUpdateS2CPacket s12 = event.getPacket();

            if(s12.getEntityId() != mc.player.getId()) return;

                ((IEntityVelocityUpdateS2CPacketMixin) s12).setXVelo(0);
                ((IEntityVelocityUpdateS2CPacketMixin) s12).setZVelo(0);
                //((IEntityVelocityUpdateS2CPacketMixin) s12).setXVelo((int) mc.player.getVelocity().getX());
                //((IEntityVelocityUpdateS2CPacketMixin) s12).setZVelo((int) mc.player.getVelocity().getZ());




        }
    };

}
