package cc.sunshine.mixin.impl;

import cc.sunshine.mixin.interfaces.IMouseMixin;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Mouse.class)
public abstract class MouseMixin implements IMouseMixin {

    @Accessor("leftButtonClicked")
    public abstract void setLeftButtonClicked(boolean clicked);

    @Override
    public void setLeftClick(boolean clicked) {
        setLeftButtonClicked(clicked);
    }
}
