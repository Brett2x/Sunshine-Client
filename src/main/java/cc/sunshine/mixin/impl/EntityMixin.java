package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.player.PlayerStrafeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    private static Vec3d movementInputToVelocity(Vec3d movementInput, float speed, float yaw){return null;}

    @Shadow
    public abstract float getYaw();

    @Shadow
    public abstract Vec3d getVelocity();

    @Shadow
    public abstract void setVelocity(Vec3d velocity);

    @Overwrite
    public void updateVelocity(float speed, Vec3d movementInput) {
        PlayerStrafeEvent event = new PlayerStrafeEvent(speed, getYaw());
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);
        Vec3d vec3d = movementInputToVelocity(movementInput, event.getMoveSpeed(), event.getYaw());
        this.setVelocity(this.getVelocity().add(vec3d));
    }
}
