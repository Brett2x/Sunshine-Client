package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.player.PlayerKeepSprintEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public final class PlayerEntityMixin {
    @Redirect(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setSprinting(Z)V"))
    public void redirectSetSprinting(PlayerEntity entity, boolean b) {
        PlayerKeepSprintEvent event = new PlayerKeepSprintEvent();
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);

        if(!event.isCancelled()) {
            entity.setSprinting(false);
        }
    }

    @Redirect(method = "attack(Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;multiply(DDD)Lnet/minecraft/util/math/Vec3d;"))
    public Vec3d redirectMultiply(Vec3d vec, double x, double y, double z) {
        PlayerKeepSprintEvent event = new PlayerKeepSprintEvent();
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);
        return event.isCancelled() ? vec : vec.multiply(0.6, 1, 0.6);
    }
}
