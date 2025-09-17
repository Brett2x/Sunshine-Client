package cc.sunshine.feature.module.impl.combat;

import cc.sunshine.eventbus.CancellableEvent;
import cc.sunshine.eventbus.impl.player.PlayerKeepSprintEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;

@ModuleData(name = "Keep Sprint", category = ModuleCategory.COMBAT)
public final class KeepSprintModule extends AbstractModule {
    @SubscribeEvent
    private final Listener<PlayerKeepSprintEvent> playerKeepSprintEventListener = CancellableEvent::cancel;
}
