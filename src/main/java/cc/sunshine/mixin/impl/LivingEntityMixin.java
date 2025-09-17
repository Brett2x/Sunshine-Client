package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.player.PlayerJumpEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Redirect(method = "jump", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getYaw()F"))
    public float redirectYaw(LivingEntity entity) {
        if(entity == MinecraftClient.getInstance().player) {
            PlayerJumpEvent event = new PlayerJumpEvent(entity.getYaw());
            ExampleMod.INSTANCE.getEventBus().publishEvent(event);
            return event.getYaw();
        }

        return entity.getYaw();
    }

    @Inject(at = @At("HEAD"), method = "isBlocking()Z", cancellable = true)
    public void isBlocking(CallbackInfoReturnable<Boolean> cir) {
        // TODO: Killaura
    }
}
