package cc.sunshine.feature.module.impl.move;

import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import org.lwjgl.glfw.GLFW;

@ModuleData(name = "Speed", category = ModuleCategory.MOVE, description = "Speeds you", key = GLFW.GLFW_KEY_Z)
public class SpeedModule extends AbstractModule {
}
