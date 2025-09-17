package cc.sunshine.feature.module.impl.move.noslowdown;

import cc.sunshine.eventbus.impl.player.PlayerSlowDownEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.impl.move.NoSlowdownModule;
import cc.sunshine.feature.module.mode.AbstractModuleMode;

public final class VanillaNoSlowdown extends AbstractModuleMode<NoSlowdownModule> {
    public VanillaNoSlowdown(NoSlowdownModule module, String name) {
        super(module, name);
    }



    @SubscribeEvent
    private final Listener<PlayerSlowDownEvent> onPlayerSlowDown = event -> {
        assert mc.player != null;
        if(mc.player.isSneaking()) return; // Lascia lo SlowDown dello Sneak per evitare di flaggare

        event.setSlowDown(false);
    };
}