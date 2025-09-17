 package cc.sunshine;

import cc.sunshine.eventbus.EventBus;
import cc.sunshine.management.CommandManager;
import cc.sunshine.management.ModuleManager;
import cc.sunshine.management.PropertyManager;
import cc.sunshine.management.RotationManager;
import cc.sunshine.ui.clickgui.ImGuiClickGui;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Fatto con amore e bestemmie da:
 * - Wykt (BASE), Baldo (Bypasses), Flame e Curtii (Bestemmie)
 */
public class ExampleMod implements ModInitializer{

    public static final Logger LOGGER = LoggerFactory.getLogger("sunshine");
	public static ExampleMod INSTANCE;

	private final EventBus eventBus = new EventBus();

	private final PropertyManager propertyManager;
	private final ModuleManager moduleManager;
	private final RotationManager rotationManager;
	private final CommandManager commandManager;

	public ExampleMod() {
		INSTANCE = this;

		this.propertyManager = new PropertyManager();
		this.moduleManager = new ModuleManager();
		this.rotationManager = new RotationManager();
		this.commandManager = new CommandManager();
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public PropertyManager getPropertyManager() {
		return propertyManager;
	}

	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	public RotationManager getRotationManager() {
		return rotationManager;
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}


	@Override
	public void onInitialize() {

	}
}