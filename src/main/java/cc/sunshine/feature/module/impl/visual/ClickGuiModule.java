package cc.sunshine.feature.module.impl.visual;

import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.ui.clickgui.ImGuiClickGui;
import org.lwjgl.glfw.GLFW;

@ModuleData(name = "Click GUI", category = ModuleCategory.VISUAL, key = GLFW.GLFW_KEY_RIGHT_SHIFT)
public final class ClickGuiModule extends AbstractModule {
    @Override
    protected void onEnable() {
        mc.setScreen(ImGuiClickGui.INSTANCE);
        toggle();
    }
}
