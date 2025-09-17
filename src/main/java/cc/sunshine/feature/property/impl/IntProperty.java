package cc.sunshine.feature.property.impl;

import cc.sunshine.feature.property.AbstractProperty;

public final class IntProperty extends AbstractProperty<Integer> {
    private final int min, max;

    public IntProperty(String name, int value, int min, int max) {
        super(name, value);
        this.min = min;
        this.max = max;}

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
