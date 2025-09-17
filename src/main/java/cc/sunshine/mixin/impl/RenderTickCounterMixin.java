package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.feature.module.impl.world.GameSpeedModule;
import net.minecraft.client.render.RenderTickCounter;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderTickCounter.Dynamic.class)
public final class RenderTickCounterMixin {
    /*@Shadow
    private float lastFrameDuration;

    @Inject(method = "beginRenderTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderTickCounter;prevTimeMillis:J", opcode = Opcodes.PUTFIELD))
    private void onBeginRenderTick(long timeMillis, CallbackInfoReturnable<Integer> ci) {
        GameSpeedModule module = (GameSpeedModule) ExampleMod.INSTANCE.getModuleManager().getModule(GameSpeedModule.class);
        lastFrameDuration *= module.getSpeed();
    }*/
}
