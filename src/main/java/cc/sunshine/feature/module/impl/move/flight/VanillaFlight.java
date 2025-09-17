package cc.sunshine.feature.module.impl.move.flight;

import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.impl.move.FlightModule;
import cc.sunshine.feature.module.mode.AbstractModuleMode;
import cc.sunshine.utils.packet.PacketUtil;
import cc.sunshine.utils.player.MoveUtil;
import net.minecraft.entity.MovementType;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class VanillaFlight extends AbstractModuleMode<FlightModule> {
    public VanillaFlight(FlightModule module, String name) {
        super(module, name);
    }

    @SubscribeEvent
    private final Listener<PlayerUpdateEvent> updateEventListener = event -> {
        if(mc.world == null) return;
        if(mc.player.getAbilities().creativeMode) return;

        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().allowFlying = true;

        //mc.player.addVelocity(new Vec3d(0, 0, 0));

        //mc.player.setPos(mc.player.getX() - 0.1, mc.player.getY(), mc.player.getZ());

        //MoveUtil.strafe(mc.player.getYaw(), 0.5);

        /*if(!mc.player.isOnGround()) {
            //mc.player.addVelocity(new Vec3d(mc.player.mo));
            //mc.player.move(MovementType.PLAYER, new Vec3d(0, 0, 0));
            PacketUtil.sendPacketN(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(),
                    mc.player.getPitch(), true));


            double yaw = Math.toRadians(mc.player.getYaw());
            mc.player.setPosition(new Vec3d(-Math.sin(yaw), mc.player.getY(), Math.cos(yaw)));
        }*/

    };

}
