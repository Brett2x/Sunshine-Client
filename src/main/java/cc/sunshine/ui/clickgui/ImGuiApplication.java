package cc.sunshine.ui.clickgui;

import cc.sunshine.ExampleMod;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.mode.AbstractModuleMode;
import cc.sunshine.feature.property.AbstractProperty;
import cc.sunshine.feature.property.impl.*;
import imgui.ImFont;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImInt;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class ImGuiApplication extends Application {


    @Override
    public void process() {
        if(ImGui.begin("Sunshine Client", ImGuiWindowFlags.NoCollapse)) {
            if(ImGui.beginTabBar("Categories")) {
                for (ModuleCategory value : ModuleCategory.values()) {
                    if(ImGui.beginTabItem(value.getName())) {
                        for (AbstractModule abstractModule : ExampleMod.INSTANCE.getModuleManager().getModulesByCategory(value)) {
                            ImGui.pushID(abstractModule.getName());
                            boolean opened = ImGui.collapsingHeader(abstractModule.getName());

                            if(opened) {
                                if(ImGui.checkbox("Enabled", abstractModule.isEnabled())) {
                                    abstractModule.toggle();
                                }

                                List<AbstractProperty<?>> properties = ExampleMod.INSTANCE.getPropertyManager().getPropertiesByModule(abstractModule);

                                if(!properties.isEmpty()) {
                                    ImGui.separator();

                                    for (AbstractProperty<?> property : properties) {
                                        renderSetting(property);
                                    }

                                    for (AbstractProperty<?> property : properties) {
                                        if(property instanceof ModeProperty modeProperty) {
                                            if(modeProperty.getValue().getProperties().isEmpty()) {
                                                continue;
                                            }

                                            AbstractModuleMode<?> mode = modeProperty.getValue();

                                            ImGui.separator();
                                            ImGui.text(modeProperty.getName() + " " + mode.getName() + " settings");
                                            mode.getProperties().forEach(this::renderSetting);
                                            ImGui.separator();
                                        }
                                    }
                                }
                            }
                            ImGui.popID();
                        }
                        ImGui.endTabItem();
                    }
                }

                ImGui.endTabBar();
            }
            ImGui.end();
        }
    }

    private void renderSetting(AbstractProperty<?> property) {
        if(property instanceof BooleanProperty booleanProperty) {
            if(ImGui.checkbox(property.getName(), booleanProperty.getValue())) {
                booleanProperty.setValue(!booleanProperty.getValue());
            }
        }

        if(property instanceof IntProperty intProperty) {
            int[] ints = new int[]{intProperty.getValue()};
            ImGui.sliderInt(property.getName(), ints, intProperty.getMin(), intProperty.getMax());
            intProperty.setValue(ints[0]);
        }

        if(property instanceof FloatProperty floatProperty) {
            float[] floats = new float[]{floatProperty.getValue()};
            ImGui.sliderFloat(property.getName(), floats, floatProperty.getMin(), floatProperty.getMax());
            floatProperty.setValue(floats[0]);
        }

        if(property instanceof ModeProperty modeProperty) {
            String[] combo = new String[modeProperty.getModes().size()];
            ImInt index = new ImInt();

            for (int i = 0; i < modeProperty.getModes().size(); i++) {
                AbstractModuleMode<?> mode = modeProperty.getModes().get(i);
                combo[i] = mode.getName();
                if(mode.equals(modeProperty.getValue())) {
                    index.set(i);
                }
            }

            ImGui.combo(modeProperty.getName(), index, combo);
            modeProperty.setValue(modeProperty.getModes().get(index.get()));
        }

        if(property instanceof EnumProperty<?> enumProperty) {
            String[] combo = new String[enumProperty.getValues().size()];
            ImInt index = new ImInt();

            for (int i = 0; i < enumProperty.getValues().size(); i++) {
                combo[i] = enumProperty.getValueByIndex(i).getName();

                if(combo[i].equals(enumProperty.getValue().getName())) {
                    index.set(i);
                }
            }

            ImGui.combo(enumProperty.getName(), index, combo);
            enumProperty.setCastedValue(enumProperty.getValueByIndex(index.get()));
        }
    }

    @Override
    protected void initWindow(Configuration config) {
        this.handle = MinecraftClient.getInstance().getWindow().getHandle();
    }

    @Override
    public void runFrame() {
        this.startFrame();
        this.preProcess();
        this.process();
        this.postProcess();
        ImGui.render();
        this.imGuiGl3.renderDrawData(ImGui.getDrawData());
    }
}