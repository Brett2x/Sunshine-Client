package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.render.Render3DEvent;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(at = @At("RETURN"), method = "renderWorld", locals = LocalCapture.CAPTURE_FAILHARD)
    public void renderWorld(float tickDelta, long limitTIme, MatrixStack matrices, CallbackInfo ci, boolean bl, Camera camera, MatrixStack stack, float d, float f, float g, Matrix4f matrix4f, Matrix3f matrix3f) {
        Render3DEvent event = new Render3DEvent(matrices, camera, tickDelta);
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);

        System.out.println("call");
    }
}
