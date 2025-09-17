package cc.sunshine.feature.module.impl.combat;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.packet.impl.PacketReceiveEvent;
import cc.sunshine.eventbus.impl.player.PlayerNetworkUpdateEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.management.RotationManager;
import cc.sunshine.mixin.interfaces.IEntityVelocityUpdateS2CPacketMixin;
import cc.sunshine.utils.packet.PacketUtil;
import cc.sunshine.utils.rotation.RotationUtil;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.math.*;

@ModuleData(name = "GrimTickVeelo", category = ModuleCategory.COMBAT, description = "GIMRIJ")
public class GrimTickVelocityModule extends AbstractModule {

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
                //event.setCancelled(true);

            }
        }

        if(event.getPacket() instanceof ExplosionS2CPacket) {
            shouldSend = true;
            event.setCancelled(true);
        }
    };

    @SubscribeEvent
    public Listener<PlayerNetworkUpdateEvent> onStrafe = event -> {
        float rotate = event.getRotation().x;

        if(shouldSend && mc.player.hurtTime != 0) {
            float yaw = (float) Math.toRadians(mc.player.getYaw());

            float nextrot = rotate + 40;
            if(rotate + 40 > 180)
                ExampleMod.INSTANCE.getRotationManager().forceNextRotation(new Vec2f(-180, event.getRotation().y), RotationManager.MovementFixMode.BASIC);
                //event.setRotation(new Vec2f(-180, event.getRotation().y));
             else {
                rotate += 40;
                //event.setRotation(new Vec2f(rotate, event.getRotation().y));
                ExampleMod.INSTANCE.getRotationManager().forceNextRotation(new Vec2f(rotate, event.getRotation().y), RotationManager.MovementFixMode.BASIC);
            }

             //if(mc.player.isOnGround()) mc.player.jump();

             //mc.player.setVelocityClient(mc.player.getVelocity().x / 2, mc.player.getVelocity().y / 2, mc.player.getVelocity().z / 2);


            //if(vulcanC0A.getValue()) PacketUtil.send(new C0APacketAnimation());

            //mc.player.setPosition(mc.player.getX() - 0.000001, mc.player.getY() - 0.000001, mc.player.getZ() - 0.000001);


            event.setPosition(new Vec3d(Math.sin(yaw) * 29999999, mc.player.getY(), mc.player.getZ()));

            //mc.player.setPosition(new Vec3d(Math.sin(yaw) * 299999999, mc.player.getY(), mc.player.getZ()));
            //if(!mc.player.isOnGround())


            //PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.Full(Math.sin(yaw) * 29999999, mc.player.getY(), mc.player.getZ(), yaw, mc.player.getPitch(), mc.player.isOnGround()));
            //PacketUtil.sendPacketNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, mc.player.getBlockPos(), Direction.UP));
            //PacketUtil.sendPacketNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, mc.player.getBlockPos(), Direction.UP));
            shouldSend = false;
        }
    };

}
