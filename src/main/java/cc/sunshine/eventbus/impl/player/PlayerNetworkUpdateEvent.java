package cc.sunshine.eventbus.impl.player;

import cc.sunshine.eventbus.CancellableEvent;
import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public final class PlayerNetworkUpdateEvent extends CancellableEvent {
    private Vec3d position;
    private Vec2f rotation;
    private boolean onGround, sneaking;

    public PlayerNetworkUpdateEvent(Vec3d position, Vec2f rotation, boolean onGround, boolean sneaking) {
        this.position = position;
        this.rotation = rotation;
        this.onGround = onGround;
        this.sneaking = sneaking;
    }

    public Vec3d getPosition() {
        return position;
    }

    public Vec2f getRotation() {
        return rotation;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setPosition(Vec3d position) {
        this.position = position;
    }

    public void setRotation(Vec2f rotation) {
        this.rotation = rotation;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }


}
