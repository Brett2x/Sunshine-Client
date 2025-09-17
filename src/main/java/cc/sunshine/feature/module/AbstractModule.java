package cc.sunshine.feature.module;

import cc.sunshine.eventbus.interfaces.IHandler;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import net.minecraft.client.MinecraftClient;

import java.util.Optional;

public abstract class AbstractModule implements IHandler {
    protected final MinecraftClient mc = MinecraftClient.getInstance();
    private final String name, description;
    private final ModuleCategory category;
    private int key;
    private boolean enabled;

    public AbstractModule() {
        ModuleData moduleData = this.getClass().getDeclaredAnnotation(ModuleData.class);

        this.name = moduleData.name();
        this.description = moduleData.description();
        this.category = moduleData.category();
        this.key = moduleData.key();
        this.enabled = moduleData.enabled();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Optional<String> getSuffix() {
        return Optional.empty();
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void setEnabled(boolean enabled) {
        if(this.enabled == enabled) {
            return;
        }

        this.enabled = enabled;

        if(enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    protected void onEnable() {}
    protected void onDisable() {}

    @Override
    public boolean listening() {
        return enabled;
    }
}
