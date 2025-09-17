package cc.sunshine.ui.screen;

import cc.sunshine.utils.login.MicrosoftCookieLogin;
import me.axieum.mcmod.authme.api.util.SessionUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;

import net.minecraft.client.session.Session;
import net.minecraft.text.Text;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class CookieLoginScreen extends Screen {
    public CookieLoginScreen(Text title) {
        super(title);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void filesDragged(List<Path> paths) {
        try {
            MicrosoftCookieLogin.LoginData loginData = MicrosoftCookieLogin.loginWithCookie(paths.get(0).toFile());
            SessionUtils.setSession(new Session(loginData.username, UUID.fromString(loginData.uuid) , loginData.mcToken, Optional.empty(), Optional.empty(), Session.AccountType.MSA));
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
