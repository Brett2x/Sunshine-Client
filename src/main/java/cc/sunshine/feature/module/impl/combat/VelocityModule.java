package cc.sunshine.feature.module.impl.combat;

import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.impl.combat.velocity.GrimVelocity;
import cc.sunshine.feature.module.impl.combat.velocity.PacketVelocity;
import cc.sunshine.feature.module.impl.combat.velocity.TickVelocity;
import cc.sunshine.feature.module.impl.combat.velocity.WatchdogVelocity;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.feature.property.impl.IntProperty;
import cc.sunshine.feature.property.impl.ModeProperty;

@ModuleData(name = "Velocity", category = ModuleCategory.COMBAT)
public class VelocityModule extends AbstractModule {

    public static IntProperty horizontal = new IntProperty("Horizontal", 0, 0, 100);
    public static IntProperty vertical = new IntProperty("Vertical", 0, 0, 100);


    private final ModeProperty mode = new ModeProperty("Mode", "Grim", new GrimVelocity(this, "Grim"),
            new TickVelocity(this, "Tick"), new WatchdogVelocity(this, "Watchdog"),
            new PacketVelocity(this, "Packet"), new GrimVelocity(this, "GrimTick"));
}
