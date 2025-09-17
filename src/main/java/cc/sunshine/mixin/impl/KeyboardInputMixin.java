package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.player.PlayerMovementInputEvent;
import cc.sunshine.eventbus.impl.player.PlayerSlowDownEvent;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public final class KeyboardInputMixin {
    @Inject(at = @At("RETURN"), method = "tick(ZF)V")
    public void tick(boolean slowDown, float slowDownFactor, CallbackInfo ci) {
        ExampleMod.INSTANCE.getEventBus().publishEvent(new PlayerMovementInputEvent());
    }

    @ModifyVariable(method = "tick(ZF)V", at = @At("HEAD"), ordinal = 0)
    public boolean modifySlowDownTick(boolean slowDown) {
        PlayerSlowDownEvent event = new PlayerSlowDownEvent(slowDown);
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);
        return event.isSlowDown();
    }
}
