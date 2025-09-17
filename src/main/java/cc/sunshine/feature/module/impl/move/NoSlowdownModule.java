package cc.sunshine.feature.module.impl.move;

import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.impl.move.noslowdown.VanillaNoSlowdown;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.feature.property.impl.ModeProperty;

@ModuleData(name = "No Slowdown", category = ModuleCategory.MOVE)
public final class NoSlowdownModule extends AbstractModule {

    private final ModeProperty mode = new ModeProperty("Mode", "Vanilla", new VanillaNoSlowdown(this, "Vanilla"));
}
