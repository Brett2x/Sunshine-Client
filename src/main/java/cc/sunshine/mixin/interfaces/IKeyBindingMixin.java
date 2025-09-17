package cc.sunshine.mixin.interfaces;

import net.minecraft.client.util.InputUtil;

public interface IKeyBindingMixin {
    InputUtil.Key getBoundKey();
}
