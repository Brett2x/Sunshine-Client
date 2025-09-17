package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.game.GameKeyEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(at = @At("HEAD"), method = "onKey")
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();

        if(window != windowHandle) {
            return;
        }

        if(action != 1) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if(client.player != null && client.currentScreen == null) {
            GameKeyEvent event = new GameKeyEvent(key);
            ExampleMod.INSTANCE.getEventBus().publishEvent(event);
        }
    }
}
