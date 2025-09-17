package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.impl.player.PlayerMoveEvent;
import cc.sunshine.eventbus.impl.player.PlayerNetworkSprintEvent;
import cc.sunshine.eventbus.impl.player.PlayerNetworkUpdateEvent;
import cc.sunshine.eventbus.impl.player.PlayerSlowDownEvent;
import cc.sunshine.mixin.interfaces.IClientPlayerEntityMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin implements IClientPlayerEntityMixin {
    private PlayerNetworkUpdateEvent lastPlayerNetworkUpdateEvent;

    @Shadow
    private float lastYaw;
    @Shadow
    private float lastPitch;

    @Inject(at = @At(value = "HEAD",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V",
            ordinal = 0), method = "tick()V")
    public void onTick(CallbackInfo ci) {
        ExampleMod.INSTANCE.getEventBus().publishEvent(new PlayerUpdateEvent(PlayerUpdateEvent.State.PRE));
    }

    @Inject(at = @At(value = "TAIL",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V",
            ordinal = 0), method = "tick()V")
    public void onTicker(CallbackInfo ci) {
        ExampleMod.INSTANCE.getEventBus().publishEvent(new PlayerUpdateEvent(PlayerUpdateEvent.State.POST));
    }

    @Inject(at = @At("HEAD"), method = "sendMovementPackets")
    public void preSendMovementPackets(CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        lastPlayerNetworkUpdateEvent = new PlayerNetworkUpdateEvent(new Vec3d(player.getX(), player.getY(), player.getZ()), new Vec2f(player.getYaw(), player.getPitch()), player.isOnGround(), player.isSneaking());
        ExampleMod.INSTANCE.getEventBus().publishEvent(lastPlayerNetworkUpdateEvent);
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getX()D"))
    public double redirectX(ClientPlayerEntity entity) {
        return lastPlayerNetworkUpdateEvent.getPosition().x;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getY()D"))
    public double redirectY(ClientPlayerEntity entity) {
        return lastPlayerNetworkUpdateEvent.getPosition().y;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getZ()D"))
    public double redirectZ(ClientPlayerEntity entity) {
        return lastPlayerNetworkUpdateEvent.getPosition().z;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
    public float redirectYaw(ClientPlayerEntity entity) {
        return lastPlayerNetworkUpdateEvent.getRotation().x;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
    public float redirectPitch(ClientPlayerEntity entity) {
        return lastPlayerNetworkUpdateEvent.getRotation().y;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnGround()Z"))
    public boolean redirectIsOnGround(ClientPlayerEntity entity) {
        return lastPlayerNetworkUpdateEvent.isOnGround();
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSneaking()Z"))
    public boolean redirectIsSneaking(ClientPlayerEntity entity) {
        return lastPlayerNetworkUpdateEvent.isSneaking();
    }

    @Redirect(method = "sendSprintingPacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSprinting()Z"))
    public boolean redirectIsSprinting(ClientPlayerEntity entity) {
        PlayerNetworkSprintEvent event = new PlayerNetworkSprintEvent(entity.isSprinting());
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);
        return event.isSprinting();
    }

    @Inject(at = @At("RETURN"), method = "sendMovementPackets")
    public void postSendMovementPackets(CallbackInfo ci) {
        lastPlayerNetworkUpdateEvent = null;
    }

    @ModifyArg(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"), index = 1)
    public Vec3d move(Vec3d movement) {
        PlayerMoveEvent event = new PlayerMoveEvent(movement);
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);
        return event.getVelocity();
    }



    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasVehicle()Z", ordinal = 0))
    public boolean hasVehicleSlowDownCall(ClientPlayerEntity entity) {
        PlayerSlowDownEvent event = new PlayerSlowDownEvent(entity.isUsingItem());
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);
        return entity.hasVehicle() || !event.isSlowDown() && !MinecraftClient.getInstance().player.isSneaking();
    }

    @Redirect(method = "canStartSprinting", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    public boolean isUsingItemRedirect(ClientPlayerEntity entity) {
        return entity.isUsingItem();
    }

    @Override
    public float getLastYaw() {
        return lastYaw;
    }

    @Override
    public float getLastPitch() {
        return lastPitch;
    }
}
