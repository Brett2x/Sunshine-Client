package cc.sunshine.management;

import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.property.AbstractProperty;

import java.util.*;

public final class PropertyManager {
    private final Map<AbstractModule, List<AbstractProperty<?>>> properties = new HashMap<>();

    public Map<AbstractModule, List<AbstractProperty<?>>> getProperties() {
        return properties;
    }

    public List<AbstractProperty<?>> getPropertiesByModule(AbstractModule module) {
        return properties.getOrDefault(module, Collections.emptyList());
    }

    public void addProperty(AbstractModule module, AbstractProperty<?> property) {
        properties.putIfAbsent(module, new ArrayList<>());
        properties.get(module).add(property);
    }
}
