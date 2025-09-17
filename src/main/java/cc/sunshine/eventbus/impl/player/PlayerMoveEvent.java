package cc.sunshine.eventbus.impl.player;

import cc.sunshine.eventbus.Event;
import cc.sunshine.utils.player.MoveUtil;
import net.minecraft.util.math.Vec3d;

public final class PlayerMoveEvent extends Event {
    private Vec3d velocity;

    public PlayerMoveEvent(Vec3d velocity) {
        this.velocity = velocity;
    }

    public Vec3d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec3d velocity) {
        this.velocity = velocity;
    }

    public void setY(double y) {
        this.velocity = new Vec3d(velocity.x, y, velocity.z);
    }

    public void strafe(double moveSpeed) {
        float direction = MoveUtil.direction();
        setVelocity(new Vec3d(-Math.sin(direction) * moveSpeed, velocity.getY(), Math.cos(direction) * moveSpeed));
    }
}
