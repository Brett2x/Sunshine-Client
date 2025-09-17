package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.render.Render2DEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public final class InGameHudMixin {
    @Inject(at = @At("RETURN"), method = "render")
    public void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        ExampleMod.INSTANCE.getEventBus().publishEvent(new Render2DEvent(context, tickCounter));
    }
}
