package cc.sunshine.ui.clickgui;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.render.Render2DEvent;
import cc.sunshine.eventbus.interfaces.IHandler;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import imgui.app.Application;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ImGuiClickGui extends Screen implements IHandler {
    public static final ImGuiClickGui INSTANCE = new ImGuiClickGui();

    private final ImGuiApplication application = new ImGuiApplication();

    public ImGuiClickGui() {
        super(Text.of("ImGui"));
        ExampleMod.INSTANCE.getEventBus().registerHandler(this);
    }

    public void initialize() {
        Application.launch(application);
    }

    @Override
    public void blur() {

    }

    @Override
    protected void applyBlur(float delta) {
        //super.applyBlur(0);
    }

    @SubscribeEvent
    private final Listener<Render2DEvent> render2DEventListener = event -> {
        if(MinecraftClient.getInstance().currentScreen != this) return;
        application.runFrame();
    };
}
