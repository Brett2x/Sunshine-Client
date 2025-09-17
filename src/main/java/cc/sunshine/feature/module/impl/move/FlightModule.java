package cc.sunshine.feature.module.impl.move;

import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.impl.move.flight.GrimFlight;
import cc.sunshine.feature.module.impl.move.flight.PacketFlight;
import cc.sunshine.feature.module.impl.move.flight.VanillaFlight;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.feature.property.impl.ModeProperty;
import org.lwjgl.glfw.GLFW;

@ModuleData(name = "Flight", category = ModuleCategory.MOVE, key = GLFW.GLFW_KEY_G)
public final class FlightModule extends AbstractModule {

    private final ModeProperty mode = new ModeProperty("Mode", "Grim", new GrimFlight(this, "Grim"),
            new VanillaFlight(this, "Vanilla"), new PacketFlight(this, "Packet"));

}
