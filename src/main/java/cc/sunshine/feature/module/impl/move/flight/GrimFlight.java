package cc.sunshine.feature.module.impl.move.flight;

import cc.sunshine.eventbus.impl.packet.impl.PacketReceiveEvent;
import cc.sunshine.eventbus.impl.player.PlayerMoveEvent;
import cc.sunshine.eventbus.impl.player.PlayerNetworkUpdateEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.impl.move.FlightModule;
import cc.sunshine.feature.module.mode.AbstractModuleMode;
import cc.sunshine.utils.player.MoveUtil;
import cc.sunshine.utils.time.Stopwatch;
import net.minecraft.network.packet.s2c.play.EntityDamageS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.math.Vec3d;

public class GrimFlight extends AbstractModuleMode<FlightModule> {
    public GrimFlight(FlightModule module, String name) {
        super(module, name);
    }

    private boolean stop = false;
    private int ticksCount;
    private final Stopwatch timer = new Stopwatch();

    @Override
    protected void onEnable() {
        super.onEnable();
        ticksCount = 0;
        if(mc.player.isOnGround()) {
            mc.options.forwardKey.setPressed(false);
            mc.options.backKey.setPressed(false);
            mc.options.leftKey.setPressed(false);
            mc.options.rightKey.setPressed(false);
            mc.player.jump();
        }
    }

    @SubscribeEvent
    private final Listener<PacketReceiveEvent> packetEvent = event -> {
        if(event.getPacket() instanceof ExplosionS2CPacket || event.getPacket() instanceof EntityVelocityUpdateS2CPacket || event.getPacket() instanceof EntityDamageS2CPacket) {
            if(timer.reached(350)) {
                stop = true;
            }
        }
    };

    @SubscribeEvent
    private final Listener<PlayerNetworkUpdateEvent> event = event -> {
        ticksCount++;

        if(ticksCount > 2) {
            //if (mc.player.hurtTime <= ticks.getValue()) {
            float yaw = (float) Math.toRadians(mc.player.getYaw());
            float pitch = (float) Math.toRadians(mc.player.getPitch());

            //event.setYaw((float) (yaw + MathHelper.nextDouble(random, 20, 20)));
            //event.setPitch(pitch);


            // Position

            //event.setY((mc.player.getY() + 1500000) * 0.98f * Math.tan(-80));

            if(MoveUtil.isMoving())
                event.setPosition(new Vec3d(5 * 1500000, (mc.player.getY() + 1500000) * 0.98f * Math.tan(-80), 2 * 1500000));

            mc.options.forwardKey.setPressed(true);
        }
    };

}
