package cc.sunshine.mixin.impl;

import cc.sunshine.mixin.interfaces.IKeyBindingMixin;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyBinding.class)
public final class KeyBindingMixin implements IKeyBindingMixin {
    @Shadow
    private InputUtil.Key boundKey;

    @Override
    public InputUtil.Key getBoundKey() {
        return boundKey;
    }
}
