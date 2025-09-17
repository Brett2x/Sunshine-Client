package cc.sunshine.feature.property.impl;

import cc.sunshine.feature.property.AbstractProperty;
import cc.sunshine.feature.property.impl.interfaces.IEnumProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class EnumProperty<T extends IEnumProperty> extends AbstractProperty<T> {
    private final List<IEnumProperty> values;

    public EnumProperty(String name, T value) {
        super(name, value);
        this.values = new ArrayList<>(Arrays.asList(value.getClass().getEnumConstants()));
    }

    public void setCastedValue(IEnumProperty property) {
        setValue((T) property);
    }

    public T getValueByIndex(int index) {
        return (T) values.get(index);
    }

    public List<IEnumProperty> getValues() {
        return values;
    }
}
