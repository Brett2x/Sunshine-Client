package cc.sunshine.feature.property.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.feature.module.mode.AbstractModuleMode;
import cc.sunshine.feature.property.AbstractProperty;

import java.lang.reflect.Field;
import java.util.*;

public final class ModeProperty extends AbstractProperty<AbstractModuleMode<?>> {
    private final List<AbstractModuleMode<?>> modes = new ArrayList<>();
    public ModeProperty(String name, String value, AbstractModuleMode<?>... modes) {
        super(name, new ArrayList<>(Arrays.asList(modes)).stream().filter(mode -> mode.getName().equals(value)).findAny().orElseThrow());

        for (AbstractModuleMode<?> mode : modes) {
            mode.setSupplier(() -> mode.getModule().isEnabled() && mode.equals(getValue()));

            this.modes.add(mode);
            ExampleMod.INSTANCE.getEventBus().registerHandler(mode);

            for (Field declaredField : mode.getClass().getDeclaredFields()) {
                if(declaredField.getType().getSuperclass() != null && declaredField.getType().getSuperclass().equals(AbstractProperty.class)) {
                    if(!declaredField.trySetAccessible()) {
                        ExampleMod.LOGGER.warn("Couldn't get field " + declaredField.getName() + " from mode " + mode.getName());
                    }

                    try {
                        mode.getProperties().add((AbstractProperty<?>) declaredField.get(mode));
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    public List<AbstractModuleMode<?>> getModes() {
        return modes;
    }
}
