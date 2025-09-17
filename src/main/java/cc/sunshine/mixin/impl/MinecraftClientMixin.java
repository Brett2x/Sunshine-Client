package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.ui.clickgui.ImGuiClickGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.util.Window;
import net.minecraft.client.world.ClientWorld;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow
    private ClientPlayerInteractionManager interactionManager;

    @Shadow
    private ClientPlayerEntity player;
    @Shadow
    public ClientWorld world;

    @Shadow protected abstract void openChatScreen(String text);

    @Shadow @Final private Window window;
    private boolean initialized;

    @Inject(at = @At("HEAD"), method = "render(Z)V")
    private void render(boolean tick, CallbackInfo ci) {
        if(!initialized) {
            ImGuiClickGui.INSTANCE.initialize();
            initialized = true;
        }
    }

    @Inject(at = @At("TAIL"), method = "run")
    private void run(CallbackInfo ci) {
        ExampleMod.INSTANCE.getEventBus();
    }



}
