package cc.sunshine.eventbus.impl.chat;

import cc.sunshine.eventbus.CancellableEvent;
import cc.sunshine.eventbus.Event;
import net.minecraft.text.Text;

public class ChatSendEvent extends CancellableEvent {

    private final String text;

    public ChatSendEvent(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
