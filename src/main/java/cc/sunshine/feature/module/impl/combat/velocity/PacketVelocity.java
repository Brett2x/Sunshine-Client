package cc.sunshine.feature.module.impl.combat.velocity;

import cc.sunshine.eventbus.impl.packet.impl.PacketReceiveEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.impl.combat.VelocityModule;
import cc.sunshine.feature.module.mode.AbstractModuleMode;
import cc.sunshine.mixin.interfaces.IEntityVelocityUpdateS2CPacketMixin;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

public class PacketVelocity extends AbstractModuleMode<VelocityModule> {


    public PacketVelocity(VelocityModule module, String name) {
        super(module, name);
    }
    @SubscribeEvent
    private final Listener<PacketReceiveEvent> packetReceiveEventListener = event -> {
        if(event.getPacket() instanceof EntityVelocityUpdateS2CPacket) {
            EntityVelocityUpdateS2CPacket s12 = event.getPacket();
            ((IEntityVelocityUpdateS2CPacketMixin) s12).setXVelo((int) ((s12.getVelocityX() * 8000) * ((double) VelocityModule.horizontal.getValue() / 100)));
            ((IEntityVelocityUpdateS2CPacketMixin) s12).setYVelo((int) ((s12.getVelocityY() * 8000) * ((double) VelocityModule.vertical.getValue() / 100)));
            ((IEntityVelocityUpdateS2CPacketMixin) s12).setZVelo((int) ((s12.getVelocityZ() * 8000) * ((double) VelocityModule.horizontal.getValue() / 100)));
        }
    };

}
