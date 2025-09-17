package cc.sunshine.feature.module.impl.move;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.impl.combat.KillAuraModule;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.management.RotationManager;
import cc.sunshine.utils.rotation.RotationUtil;
import net.minecraft.util.math.Vec2f;
import org.lwjgl.glfw.GLFW;

@ModuleData(name = "Retard", description = "retards for you", category = ModuleCategory.MOVE, key = GLFW.GLFW_KEY_N)
public class RetardModule extends AbstractModule {

    @Override
    protected void onEnable() {
        super.onEnable();
        //mc.player.setYaw(90);
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }

    @SubscribeEvent
    private final Listener<PlayerUpdateEvent> playerUpdateEventListener = event -> {
        if(mc.player.isOnGround()) {
            ExampleMod.INSTANCE.getRotationManager().forceNextRotation(new Vec2f(mc.player.getYaw(), mc.player.getPitch()), RotationManager.MovementFixMode.BASIC);
            mc.options.jumpKey.setPressed(true);

                mc.options.leftKey.setPressed(false);
        }  else {
            if(ExampleMod.INSTANCE.getModuleManager().getModule(KillAuraModule.class).isEnabled()) return;
            mc.options.leftKey.setPressed(true);
            mc.options.jumpKey.setPressed(false);
            ExampleMod.INSTANCE.getRotationManager().forceNextRotation(new Vec2f(mc.player.getYaw() + 46, mc.player.getPitch()), RotationManager.MovementFixMode.BASIC);
        }
    };

}
