package cc.sunshine.utils.chat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class ChatUtil {

    public static void prefixMessage(final String message) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(Formatting.YELLOW.getColorValue() +
                Formatting.BOLD.getColorValue() + "[☀] " + Formatting.RESET.getColorValue() + Formatting.BOLD.getColorValue() + message));
    }

}
