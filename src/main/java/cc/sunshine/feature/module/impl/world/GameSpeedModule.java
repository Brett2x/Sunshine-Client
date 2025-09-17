package cc.sunshine.feature.module.impl.world;

import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.feature.property.impl.FloatProperty;
import org.lwjgl.glfw.GLFW;

@ModuleData(name = "Game Speed", category = ModuleCategory.WORLD, key = GLFW.GLFW_KEY_Z)
public final class GameSpeedModule extends AbstractModule {
    private final FloatProperty speed = new FloatProperty("Speed", 1.5F, 0.1F, 10F);

    public float getSpeed() {
        return isEnabled() ? speed.getValue() : 1;
    }
}
