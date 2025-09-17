package cc.sunshine.eventbus.impl.player;

import cc.sunshine.eventbus.Event;

public final class PlayerSlowDownEvent extends Event {
    private boolean slowDown;

    public PlayerSlowDownEvent(boolean slowDown) {
        this.slowDown = slowDown;
    }

    public boolean isSlowDown() {
        return slowDown;
    }

    public void setSlowDown(boolean slowDown) {
        this.slowDown = slowDown;
    }
}
