package cc.sunshine.feature.command;

import cc.sunshine.eventbus.interfaces.IHandler;
import cc.sunshine.feature.command.interfaces.CommandData;
import net.minecraft.client.MinecraftClient;

public abstract class AbstractCommand implements IHandler {

    private final String[] alias;
    private final String name, description;
    protected final MinecraftClient mc = MinecraftClient.getInstance();

    public AbstractCommand() {
        this.alias = getClass().getAnnotation(CommandData.class).alias();
        this.name = getClass().getAnnotation(CommandData.class).name();
        this.description = getClass().getAnnotation(CommandData.class).description();
    }

    public String[] getAlias() {
        return alias;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void run(String[] args);
}
