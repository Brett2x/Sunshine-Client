package cc.sunshine.feature.module.impl.move.flight;

import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.impl.packet.impl.PostPacketSendEvent;
import cc.sunshine.eventbus.impl.player.PlayerNetworkUpdateEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.impl.move.FlightModule;
import cc.sunshine.feature.module.mode.AbstractModuleMode;
import cc.sunshine.mixin.interfaces.IPlayerMoveC2SPacketMixin;
import cc.sunshine.utils.packet.PacketUtil;
import net.minecraft.entity.MovementType;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class PacketFlight extends AbstractModuleMode<FlightModule> {
    public PacketFlight(FlightModule module, String name) {
        super(module, name);
    }

    @SubscribeEvent
    private final Listener<PlayerNetworkUpdateEvent> playerUpdateEventListener = event -> {
        /*double[] dir = yawPos(1);
        double xDir = dir[0];
        double zDir = dir[1];
        if(mc.options.forwardKey.isPressed() || mc.options.leftKey.isPressed() || mc.options.rightKey.isPressed() || mc.options.backKey.isPressed()) {
            //mc.player.move(MovementType.SELF, new Vec3d(xDir * 0.26, 0, zDir * 0.26));
            mc.player.setVelocity(new Vec3d(xDir * 0.25, 0, zDir * 0.25));
        }

        mc.player.setVelocity(new Vec3d(mc.player.getX() + mc.player.getVelocity().x, mc.player.getY() + (mc.options.jumpKey.isPressed() ? 0.0624 : 0) - (mc.options.sneakKey.isPressed() ? 0.0624 : 0), mc.player.getZ() + mc.player.getVelocity().z));
        mc.player.setPosition(new Vec3d(mc.player.getX() + mc.player.getVelocity().x, mc.player.getY() + 10000, mc.player.getZ() + mc.player.getVelocity().z));*/

        for(int i = 0; i <= 4; i++) {
            mc.player.setPosition(mc.player.getX() + yawPos(0.0014)[0], mc.player.getY(), mc.player.getZ() + yawPos(0.0014)[1]);
            //PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX() + yawPos(0.001)[0], mc.player.getY(), mc.player.getZ() + yawPos(0.001)[1], mc.player.getYaw(), 89, mc.player.isOnGround()));
        }

        //PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(), 89, mc.player.isOnGround()));


    };

    @SubscribeEvent
    private final Listener<PostPacketSendEvent> packetSendEventListener = event -> {

    };

    public double[] yawPos(double value) {
        assert mc.player != null;
        double yaw = Math.toRadians(mc.player.getYaw());
        return new double[] {-Math.sin(yaw) * value, Math.cos(yaw) * value};
    }

}
