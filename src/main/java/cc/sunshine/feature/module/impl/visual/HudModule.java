package cc.sunshine.feature.module.impl.visual;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.render.Render2DEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.ui.clickgui.ImGuiClickGui;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ModuleData(name = "HUD", category = ModuleCategory.VISUAL, enabled = true)
public final class HudModule extends AbstractModule {

    private ArrayList<Color> colors;

    @SubscribeEvent
    private final Listener<Render2DEvent> render2DEventListener = event -> {


        event.getContext().drawTextWithShadow(mc.textRenderer, "☀", 3, 3, new Color(255, 255, 0).getRGB());

        //event.getContext().drawTextWithShadow(mc.textRenderer, "GOONING", 300, 30, new Color(255, 255, 0).getRGB());
//
        //event.getContext().fill(20, 20, 400, 400, new Color(5, 5, 5).getRGB());

        List<AbstractModule> modules = ExampleMod.INSTANCE.getModuleManager().getModules()
                .stream()
                .filter(AbstractModule::isEnabled)
                .sorted(Comparator.comparingInt(module -> -mc.textRenderer.getWidth(module.getName() + module.getSuffix().orElse(""))))
                .toList();

        AtomicInteger index = new AtomicInteger();
        modules.forEach(module -> {
            StringBuilder moduleSb = new StringBuilder();

            moduleSb.append(module.getName());

            if(module.getSuffix().isPresent()) {
                moduleSb.append(" ");
                moduleSb.append(Formatting.GRAY);
                moduleSb.append(module.getSuffix().get());
            }

            event.getContext().drawTextWithShadow(mc.textRenderer, moduleSb.toString(), mc.getWindow().getScaledWidth() - 3 - mc.textRenderer.getWidth(moduleSb.toString()),  3 + index.get() * mc.textRenderer.fontHeight, -1);
            index.getAndIncrement();
        });
    };
}
