package cc.sunshine.eventbus.impl.player;

import cc.sunshine.eventbus.Event;

public final class PlayerNetworkSprintEvent extends Event {
    private boolean sprinting;

    public PlayerNetworkSprintEvent(boolean sprinting) {
        this.sprinting = sprinting;
    }

    public boolean isSprinting() {
        return sprinting;
    }

    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
    }
}
