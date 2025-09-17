package cc.sunshine.feature.property.impl;

import cc.sunshine.feature.property.AbstractProperty;

public final class FloatProperty extends AbstractProperty<Float> {
    private final float min, max;

    public FloatProperty(String name, float value, float min, float max) {
        super(name, value);
        this.min = min;
        this.max = max;}

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }
}
