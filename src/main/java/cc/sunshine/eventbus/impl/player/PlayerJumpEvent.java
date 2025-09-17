package cc.sunshine.eventbus.impl.player;

import cc.sunshine.eventbus.Event;

public final class PlayerJumpEvent extends Event {
    private float yaw;

    public PlayerJumpEvent(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}
