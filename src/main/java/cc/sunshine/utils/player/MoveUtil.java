package cc.sunshine.utils.player;

import cc.sunshine.utils.IMinecraft;
import cc.sunshine.utils.math.MathUtil;
import net.minecraft.client.input.Input;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import static cc.sunshine.utils.math.MathUtil.wrappedDifference;

public final class MoveUtil implements IMinecraft {

    public static void stop() {
        mc.player.input.movementForward = 0;
        mc.player.input.movementSideways = 0;
    }

    public static float direction(float rotationYaw, final double moveForward, final double moveStrafing) {
        if (moveForward < 0F) rotationYaw += 180F;

        float forward = 1F;

        if (moveForward < 0F) forward = -0.5F;
        else if (moveForward > 0F) forward = 0.5F;

        if (moveStrafing > 0F) rotationYaw -= 90F * forward;
        if (moveStrafing < 0F) rotationYaw += 90F * forward;

        return (float) Math.toRadians(rotationYaw);
    }

    public static float direction() {
        return direction(mc.player.getYaw(), mc.player.input.movementForward, mc.player.input.movementSideways);
    }



    public static void fixMovement(final float yaw) {
        if (!isMoving()) {
            return;
        }

        float forwardInput = mc.player.input.movementForward;
        float strafeInput = mc.player.input.movementSideways;

        // Calculate the magnitude of the input vector
        float magnitude = (float) Math.sqrt(forwardInput * forwardInput + strafeInput * strafeInput);

        // If not moving, exit early
        if (magnitude == 0) {
            return;
        }

        // Preserve the direction by normalizing inputs
        float forwardRatio = forwardInput / magnitude;
        float strafeRatio = strafeInput / magnitude;

        // Apply a speed multiplier based on conditions
        float speedMultiplier = 1.0F;


        // Scale the inputs by the speed multiplier
        mc.player.input.movementForward = forwardRatio * speedMultiplier * magnitude;
        mc.player.input.movementSideways = strafeRatio * speedMultiplier * magnitude;
    }

    public static void strafe(double speed) {
        float direction = direction();
        Vec3d velocity = mc.player.getVelocity();

        mc.player.setVelocity(-Math.sin(direction) * speed, velocity.y, Math.cos(direction) * speed);
    }

    public static void strafe(float yaw, double speed) {
        float direction = (float) Math.toRadians(yaw);
        Vec3d velocity = mc.player.getVelocity();

        mc.player.setVelocity(-Math.sin(direction) * speed, velocity.y, Math.cos(direction) * speed);
    }

    public static boolean isMoving() {
        return mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0;
    }

    public static double getBaseSpeed(double baseSpeed) {
        StatusEffectInstance speedEffect = mc.player.getStatusEffect(StatusEffects.SPEED);
        StatusEffectInstance slowdownEffect = mc.player.getStatusEffect(StatusEffects.SLOWNESS);

        if(speedEffect != null && speedEffect.getEffectType() == StatusEffects.SPEED) {
            baseSpeed *= 1 + 0.2 * (speedEffect.getAmplifier() + 1);
        }

        if(slowdownEffect != null && slowdownEffect.getEffectType() == StatusEffects.SLOWNESS) {
            baseSpeed /= 1 + 0.2 * (slowdownEffect.getAmplifier() + 1);
        }

        return baseSpeed;
    }
}
