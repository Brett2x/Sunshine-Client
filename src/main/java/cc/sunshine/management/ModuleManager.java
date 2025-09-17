package cc.sunshine.management;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.game.GameKeyEvent;
import cc.sunshine.eventbus.interfaces.IHandler;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.impl.combat.*;
import cc.sunshine.feature.module.impl.exploit.*;
import cc.sunshine.feature.module.impl.move.*;
import cc.sunshine.feature.module.impl.visual.AltViewModule;
import cc.sunshine.feature.module.impl.visual.ClickGuiModule;
import cc.sunshine.feature.module.impl.visual.HudModule;
import cc.sunshine.feature.module.impl.visual.StreamerMode;
import cc.sunshine.feature.module.impl.world.BlinkModule;
import cc.sunshine.feature.module.impl.world.GameSpeedModule;
import cc.sunshine.feature.module.impl.world.GarbageCollectModule;
import cc.sunshine.feature.property.AbstractProperty;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ModuleManager implements IHandler {
    private static final Class<? extends AbstractModule>[] MODULE_CLASSES = new Class[] {
            KeepSprintModule.class, RegenModule.class, FastEatModule.class,
            VelocityModule.class, NoSlowdownModule.class, SprintModule.class, KillAuraModule.class,
            ClickGuiModule.class, HudModule.class, GameSpeedModule.class, FastBowModule.class, FlightModule.class,
            BlinkModule.class, ScaffoldModule.class, AltViewModule.class, PreviousHitModule.class,
            ClickerModule.class, GrimTickVelocityModule.class, GarbageCollectModule.class, StreamerMode.class, MitigationModule.class,
            RetardModule.class
    };

    private final Map<Class<? extends AbstractModule>, AbstractModule> moduleMap = new HashMap<>();

    public ModuleManager() {
        for (Class<? extends AbstractModule> moduleClass : MODULE_CLASSES) {
            try {
                AbstractModule module = moduleClass.getConstructor().newInstance();

                moduleMap.put(moduleClass, module);
                ExampleMod.INSTANCE.getEventBus().registerHandler(module);

                for (Field declaredField : module.getClass().getDeclaredFields()) {
                    if(declaredField.getType().getSuperclass() != null && declaredField.getType().getSuperclass().equals(AbstractProperty.class)) {
                        if(!declaredField.trySetAccessible()) {
                            ExampleMod.LOGGER.warn("Couldn't get field " + declaredField.getName() + " from module " + module.getName());
                        }

                        ExampleMod.INSTANCE.getPropertyManager().addProperty(module, (AbstractProperty<?>) declaredField.get(module));
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException("Failed to register " + moduleClass.getName(), ex);
            }
        }

        ExampleMod.INSTANCE.getEventBus().registerHandler(this);
    }

    public Collection<AbstractModule> getModules() {
        return moduleMap.values();
    }

    public AbstractModule getModule(Class<? extends AbstractModule> moduleClass) {
        return moduleMap.get(moduleClass);
    }

    public AbstractModule getModuleByName(String name) {
        List<AbstractModule> names = moduleMap.values().stream().filter(module -> module.getName().equalsIgnoreCase(name)).toList();
        if(names.size() != 0)
            return names.get(0);

        return null;
    }

    public List<AbstractModule> getModulesByCategory(ModuleCategory category) {
        return moduleMap.values().stream().filter(module -> module.getCategory() == category).collect(Collectors.toList());
    }

    @SubscribeEvent
    private final Listener<GameKeyEvent> gameKeyEventListener = event -> {
        moduleMap.values().stream().filter(module -> module.getKey() == event.getKey()).forEach(AbstractModule::toggle);
    };
}
