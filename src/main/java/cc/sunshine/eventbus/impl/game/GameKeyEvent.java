package cc.sunshine.eventbus.impl.game;

import cc.sunshine.eventbus.Event;

public final class GameKeyEvent extends Event {
    private final int key;

    public GameKeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
