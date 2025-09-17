package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.player.PlayerAttackEvent;
import cc.sunshine.mixin.interfaces.IPlayerInteractEntityC2SPacketMixin;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerInteractEntityC2SPacket.class)
public final class PlayerInteractEntityC2SPacketMixin implements IPlayerInteractEntityC2SPacketMixin {
    @Shadow
    private int entityId;

    @Override
    public int getEntityId() {
        return entityId;
    }

    @Inject(method = "attack", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void attack(Entity entity, boolean playerSneaking, CallbackInfoReturnable<PlayerInteractEntityC2SPacket> cir) {
        PlayerAttackEvent event = new PlayerAttackEvent(entity);
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);
    }
}
