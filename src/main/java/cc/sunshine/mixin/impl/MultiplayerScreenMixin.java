package cc.sunshine.mixin.impl;

import cc.sunshine.ui.screen.CookieLoginScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public abstract class MultiplayerScreenMixin extends Screen
{
    private MultiplayerScreenMixin(Text title)
    {
        super(title);
    }

    /**
     * Injects into the creation of the screen and adds the authentication button.
     *
     * @param ci injection callback info
     */
    @Inject(method = "init", at = @At("HEAD"))
    private void init(CallbackInfo ci)
    {
        // Create and add the button to the screen
        addDrawableChild(
                new ButtonWidget.Builder(
                        Text.of("Cookie login"),
                        (button) -> client.setScreen(new CookieLoginScreen(Text.of("Cookie login")))
                ).position(100, 10).size(100, 20).build());
    }
}
