package cc.sunshine.feature.module.impl.combat;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.impl.packet.impl.PacketSendEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.feature.property.impl.BooleanProperty;
import cc.sunshine.feature.property.impl.FloatProperty;
import cc.sunshine.management.RotationManager;
import cc.sunshine.utils.packet.PacketUtil;
import cc.sunshine.utils.rotation.RotationUtil;
import com.google.common.base.Stopwatch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

// TODO
@ModuleData(name = "Kill Aura", category = ModuleCategory.COMBAT, key = GLFW.GLFW_KEY_X)
public class KillAuraModule extends AbstractModule {

    private final BooleanProperty keepSprint = new BooleanProperty("Keep Sprint", true);
    private final BooleanProperty noSwing = new BooleanProperty("No Swing", true);
    private final BooleanProperty version = new BooleanProperty("1.9+", false);
    private final BooleanProperty invisRots = new BooleanProperty("Invisible Rotations", false);
    private final BooleanProperty critOnly = new BooleanProperty("Critical Hit Only", false);
    private final FloatProperty range = new FloatProperty("Range", 3, 3, 6);
    private final Stopwatch stopwatches = Stopwatch.createStarted();
    private Entity mcTarget;
    private boolean rotating = false, noPackets = false;
    private float lastTickFallDistance;

    @SubscribeEvent
    private final Listener<PlayerUpdateEvent> onPlayerUpdate = event -> {

        if (mc.world == null) return;
        if (mc.player.isDead() || event.getState() == PlayerUpdateEvent.State.POST) return;

        List<Entity> entities = new ArrayList<>();
        mc.world.getEntities().forEach(entities::add);
        entities.sort(Comparator.comparingDouble(e -> e.distanceTo(mc.player)));
        entities.removeIf(e -> e == mc.player /*|| e.getScoreboardTeam() == mc.player.getScoreboardTeam()*/ || !(e instanceof PlayerEntity));
        if (entities.size() > 0)
            mcTarget = entities.get(0);
        else
            mcTarget = mc.player;

        boolean blocking = true;


        //ExampleMod.INSTANCE.getRotationManager().setNextRotation(RotationUtil.aimTarget(mcTarget), RotationManager.MovementFixMode.ADVANCED);


        if (mcTarget.distanceTo(mc.player) <= (range.getValue() + 1) && mcTarget instanceof PlayerEntity && mcTarget.distanceTo(mc.player) >= 0.5 && mcTarget != mc.player) {
            if (invisRots.getValue()) {
                //PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), RotationUtil.aimTarget(mcTarget).x, RotationUtil.aimTarget(mcTarget).y, mc.player.isOnGround()));
                rotating = true;

                PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), RotationUtil.aimTarget(mcTarget).x, RotationUtil.aimTarget(mcTarget).y, mc.player.isOnGround()));

            } else {
                ExampleMod.INSTANCE.getRotationManager().forceNextRotation((RotationUtil.aimTarget(mcTarget)), RotationManager.MovementFixMode.ADVANCED);
            }

        }
        if(!version.getValue()) {
            if (!stopwatches.isRunning())
                stopwatches.start();

            long ms = stopwatches.elapsed(TimeUnit.MILLISECONDS);
            if (/*stopwatch.reached((int) (100 + Math.random() * 2))*/ (ms >= 100) && mcTarget.distanceTo(mc.player) <= range.getValue() && mcTarget instanceof PlayerEntity && !((PlayerEntity) mcTarget).isDead() && mcTarget.distanceTo(mc.player) > 0.5 && mcTarget != mc.player) {

                if (!keepSprint.getValue())
                    mc.player.setSprinting(false);
                blocking = false;

                //PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.LookAndOnGround(RotationUtil.aimTarget(mcTarget).x, RotationUtil.aimTarget(mcTarget).y, mc.player.isOnGround()));

                if (!noSwing.getValue())
                    mc.player.swingHand(Hand.MAIN_HAND);
                if (event.getState() == PlayerUpdateEvent.State.PRE)
                    //PacketUtil.sendPacketNoEvent(PlayerInteractEntityC2SPacket.attack(mcTarget, mc.player.isSneaking()));
                    mc.interactionManager.attackEntity(mc.player, mcTarget);
                //mc.interactionManager.attackEntity(mc.player, mcTarget);
                //PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
                //ExampleMod.INSTANCE.getRotationManager().forceNextRotation(new Vec2f(mc.player.getYaw(), mc.player.getPitch()), RotationManager.MovementFixMode.ADVANCED);

                ExampleMod.LOGGER.debug("ATTACKED! Stopwatch time: " + ms);
                System.out.println("ATTACKED! Stopwatch time: " + ms);
                stopwatches.reset();
            } else {
                blocking = true;

            }
        } else {
            float f = this.mc.player.getAttackCooldownProgress(0.0f);
            boolean bl = false;
            if (mcTarget != null && f >= 1.0f) {
                bl = this.mc.player.getAttackCooldownProgressPerTick() > 5.0f;
                bl &= mcTarget.isAlive();
            }

            if(bl) {
                if(mcTarget.distanceTo(mc.player) <= range.getValue() && mcTarget instanceof PlayerEntity && !((PlayerEntity) mcTarget).isDead() && mcTarget != mc.player) {
                    if(!(critOnly.getValue() && (mc.player.isOnGround() || lastTickFallDistance <= mc.player.fallDistance || mc.player.fallDistance == 0))) {


                        assert mc.interactionManager != null;
                        mc.interactionManager.attackEntity(mc.player, mcTarget);
                        if (!noSwing.getValue())
                            mc.player.swingHand(Hand.MAIN_HAND);

                    }
                }
            } else if (f >= 1.0f) {
                if(mcTarget.distanceTo(mc.player) <= range.getValue() && mcTarget instanceof PlayerEntity && !((PlayerEntity) mcTarget).isDead() && mcTarget != mc.player) {
                    if(!(critOnly.getValue() && (mc.player.isOnGround() || lastTickFallDistance <= mc.player.fallDistance || mc.player.fallDistance == 0))) {


                        assert mc.interactionManager != null;
                        mc.interactionManager.attackEntity(mc.player, mcTarget);
                        if (!noSwing.getValue())
                            mc.player.swingHand(Hand.MAIN_HAND);

                    }

                }
            }



        }

        lastTickFallDistance = mc.player.fallDistance;

        /*if (blocking && mc.player.getOffHandStack().getItem() == Items.SHIELD) {
            mc.options.useKey.setPressed(true);
            PacketUtil.sendPacketNoEvent(new PickFromInventoryC2SPacket(mc.player.getInventory().selectedSlot % 8 + 1));
            PacketUtil.sendPacketNoEvent(new PickFromInventoryC2SPacket(mc.player.getInventory().selectedSlot));

        } else
            //mc.options.useKey.setPressed(false);
            PacketUtil.sendPacketNoEvent(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));*/

    };

    @SubscribeEvent
    private final Listener<PacketSendEvent> packetSendEventListener = event -> {

        if (invisRots.getValue()) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket) {
                if (rotating) {
                    //PacketUtil.sendPacketN(new PlayerMoveC2SPacket.Full());
                    PlayerMoveC2SPacket packet = (PlayerMoveC2SPacket) event.getPacket();
                    //((IPlayerMoveC2SPacketMixin) packet).yawSetter(RotationUtil.aimTarget(mcTarget).x);
                    //((IPlayerMoveC2SPacketMixin) packet).pitchSetter(RotationUtil.aimTarget(mcTarget).y);
                }
            } else {
                if (rotating)
                    noPackets = true;
            }
        }

    };


}
