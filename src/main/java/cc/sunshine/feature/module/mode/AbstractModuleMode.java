package cc.sunshine.feature.module.mode;

import cc.sunshine.eventbus.interfaces.IHandler;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.property.AbstractProperty;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public abstract class AbstractModuleMode<T extends AbstractModule> implements IHandler {
    protected final MinecraftClient mc = MinecraftClient.getInstance();

    private final List<AbstractProperty<?>> properties = new ArrayList<>();

    protected final T module;
    private BooleanSupplier supplier;
    private final String name;
    private boolean lastState;

    public AbstractModuleMode(T module, String name) {
        this.module = module;
        this.name = name;
    }

    protected void onEnable() {}
    protected void onDisable() {}

    public T getModule() {
        return module;
    }

    public String getName() {
        return name;
    }

    public void setSupplier(BooleanSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean listening() {
        boolean flag = module.isEnabled() && supplier.getAsBoolean();

        if(lastState != flag) {
            if(flag) {
                onEnable();
            } else {
                onDisable();
            }

            lastState = flag;
        }

        return flag;
    }

    public List<AbstractProperty<?>> getProperties() {
        return properties;
    }
}
