package cc.sunshine.eventbus.impl.player;

import cc.sunshine.eventbus.Event;

public final class PlayerStrafeEvent extends Event {
    private float moveSpeed;
    private float yaw;

    public PlayerStrafeEvent(float moveSpeed, float yaw) {
        this.moveSpeed = moveSpeed;
        this.yaw = yaw;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getYaw() {
        return yaw;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
}
