package cc.sunshine.feature.property;

import java.util.function.BooleanSupplier;

public abstract class AbstractProperty<T> {
    private final String name;
    private T value;

    private BooleanSupplier supplier;

    public AbstractProperty(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean shouldHide() {
        return supplier != null && !supplier.getAsBoolean();
    }

    public <V extends AbstractProperty<?>> V supplyIf(BooleanSupplier supplier) {
        this.supplier = supplier;
        return (V) this;
    }
}
