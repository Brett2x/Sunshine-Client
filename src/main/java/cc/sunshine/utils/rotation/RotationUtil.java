package cc.sunshine.utils.rotation;

import cc.sunshine.mixin.interfaces.IClientPlayerEntityMixin;
import cc.sunshine.utils.IMinecraft;
import cc.sunshine.utils.raycast.RayCastUtil;
import cc.sunshine.utils.tuple.Tuple;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSplitter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class RotationUtil implements IMinecraft {
    public static Vec3d getVectorForRotation(final float yaw, final float pitch)
    {
        final double f = Math.cos(Math.toRadians(-yaw) - Math.PI);
        final double f1 = Math.sin(Math.toRadians(-yaw) - Math.PI);
        final double f2 = -Math.cos(Math.toRadians(-pitch));
        final double f3 = Math.sin(Math.toRadians(-pitch));
        return new Vec3d((f1 * f2), f3, (f * f2));
    }

    public static float bypassGCD(float fl) {
        float gcdSens = 99;
        float m = (float) (0.005 * gcdSens);
        float gcd = (float) (m * m * m * 1.2);
        fl -= fl % gcd;
        return fl;
    }


    public static Vec2f applySensitivityPatch(final Vec2f rotation) {
        final Vector2f previousRotation = new Vector2f(mc.player.prevYaw, mc.player.prevPitch);
        final float mouseSensitivity = (float) (mc.options.getMouseSensitivity().getValue() * (1 + Math.random() / 10000000) * 0.6F + 0.2F);
        final double multiplier = mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F * 0.15D;
        final float yaw = previousRotation.getX() + (float) (Math.round((rotation.x - previousRotation.getX()) / multiplier) * multiplier);
        final float pitch = previousRotation.getX() + (float) (Math.round((rotation.y - previousRotation.getY()) / multiplier) * multiplier);
        return new Vec2f(yaw, MathHelper.clamp(pitch, -90, 90));
    }

    public static Vec2f aimTarget(Entity entity) {
        SecureRandom secureRandom = new SecureRandom();

        double x = entity.prevX + (entity.getX() - entity.prevX) - mc.player.getX();
        double y = entity.prevY + (entity.getY() - entity.prevY) + entity.getEyeHeight(entity.getPose()) - mc.player.getY() - mc.player.getEyeHeight(mc.player.getPose());
        double z = entity.prevZ + (entity.getZ() - entity.prevZ) - mc.player.getZ();

        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

        float yaw = (float) ((float) Math.toDegrees(Math.acos(z / distance)));
        float pitch = (float) -Math.toDegrees(Math.atan2(y, distance));

        if(x > 0) {
            yaw = -yaw;
        }

        Vec2f rotz =  new Vec2f(mc.player.getYaw() + MathHelper.wrapDegrees(yaw - mc.player.getYaw()), mc.player.getPitch() + MathHelper.wrapDegrees(pitch - mc.player.getPitch()));
        return (rotz);
    }

    public static Vec2f aimBlock(final Tuple<BlockPos, Direction> blockData) {
        double x = blockData.a.getX() + 0.5 - mc.player.getX() + blockData.b.getOffsetX() / 2D;
        double z = blockData.a.getZ() + 0.5 - mc.player.getZ() + blockData.b.getOffsetZ() / 2D;
        double y = blockData.a.getY() + 0.5;

        double deltaY = mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()) - y;
        double distance = Math.sqrt(x * x + z * z);

        float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90;
        float pitch = (float) Math.toDegrees(Math.atan2(deltaY, distance));

        if (yaw < 0) {
            yaw += 360;
        }

        float lastYaw = ((IClientPlayerEntityMixin) mc.player).getLastYaw();

        return new Vec2f(lastYaw + MathHelper.wrapDegrees(yaw - lastYaw), pitch);
    }

    // goofy ahhh method technology
    // TODO: Make it work with UP/DOWN directions
    // TODO: Avoid the 3600 for loop
    public static Optional<Vec2f> calculateRotation(Tuple<BlockPos, Direction> data) {
        double deltaY = mc.player.getEyeY() - data.a.getY() - 0.1;
        Vec3d position = mc.player.getEyePos().add(0, -deltaY, 0);

        for (float yaw = mc.player.getYaw() - 180; yaw <= mc.player.getYaw() + 180; yaw += 0.1F) {
            BlockHitResult result = RayCastUtil.rayCastBlock(position, new Vec2f(yaw, 0), 3, false);

            if(result.getType() != HitResult.Type.BLOCK || !result.getBlockPos().equals(data.a) || !result.getSide().equals(data.b)) {
                continue;
            }

            Vec3d difference = result.getPos().subtract(mc.player.getEyePos());
            double distance = Math.sqrt(Math.pow(difference.x, 2) + Math.pow(difference.z, 2));

            float calculatedYaw = (float) Math.toDegrees(Math.atan2(difference.z, difference.x)) - 90F;
            float calculatedPitch = (float) -Math.toDegrees(Math.atan2(difference.y, distance));

            float lastYaw = ((IClientPlayerEntityMixin) mc.player).getLastYaw();
            return Optional.of(new Vec2f(lastYaw + MathHelper.wrapDegrees(calculatedYaw - lastYaw), calculatedPitch));
        }

        return Optional.empty();
    }

    public static Optional<Vec2f> bruteforceRotation(Tuple<BlockPos, Direction> data) {

        for (float yaw = mc.player.getYaw() - 180; yaw <= mc.player.getYaw() + 180; yaw += 45) {
            Optional<Float> optionalPitch = bruteforcePitch(data, yaw);

            if(optionalPitch.isEmpty()) {
                continue;
            }

            float pitch = optionalPitch.get();

            if(RayCastUtil.rayCastBlock(data, new Vec2f(yaw, pitch), true)) {
                return Optional.of(new Vec2f(yaw, pitch));
            }
        }

        return Optional.empty();
    }

    public static Optional<Float> bruteforcePitch(final Tuple<BlockPos, Direction> data, final float yaw) {
        List<Float> pitches = new ArrayList<>();
        boolean under = data.a.getY() < mc.player.getBlockY();

        for (float f = under ? 70 : 0; f < 90; f += 0.25) {
            BlockHitResult result = RayCastUtil.rayCastBlock(new Vec2f(yaw, f), 3, true);

            if(result != null && result.getType() == HitResult.Type.BLOCK && result.getBlockPos().equals(data.a) && result.getSide() == data.b) {
                pitches.add(f);
            }
        }

        if(pitches.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(pitches.get(Math.min(pitches.size() - 1, 3)));
    }
}
