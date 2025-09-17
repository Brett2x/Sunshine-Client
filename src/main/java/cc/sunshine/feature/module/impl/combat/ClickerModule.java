package cc.sunshine.feature.module.impl.combat;

import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.mixin.interfaces.IMouseMixin;
import cc.sunshine.utils.time.Stopwatch;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.KeyBinding;

import java.util.Random;

@ModuleData(name = "Clicker", description = "Clicks", category = ModuleCategory.COMBAT)
public class ClickerModule extends AbstractModule {

    private final Stopwatch stopwatch = new Stopwatch();

    @SubscribeEvent
    private final Listener<PlayerUpdateEvent> playerUpdateEventListener = event -> {
        final Random random = new Random();

        leftClick(false);
        if(stopwatch.reached(60 + random.nextInt(-50, 50)) && mc.options.forwardKey.isPressed() && !mc.options.useKey.isPressed()) {
            assert mc.player != null;
            if (mc.player.isSprinting()) {
                KeyBinding.onKeyPressed(mc.options.attackKey.getDefaultKey());

                stopwatch.reset();
            }
        }

    };

    private void leftClick(boolean state) {
        final Mouse mouse = new Mouse(mc);
        ((IMouseMixin) mouse).setLeftClick(state);}

}
