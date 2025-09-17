package cc.sunshine.utils.raycast;

import cc.sunshine.utils.IMinecraft;
import cc.sunshine.utils.rotation.RotationUtil;
import cc.sunshine.utils.tuple.Tuple;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.joml.Vector2d;

public final class RayCastUtil implements IMinecraft {
    public static BlockHitResult rayCastBlock(Vec3d position, Vec2f rotation, double maxDistance, boolean includeFluids) {
        Vec3d vec3d2 = RotationUtil.getVectorForRotation(rotation.x, rotation.y);
        Vec3d vec3d3 = position.add(vec3d2.x * maxDistance, vec3d2.y * maxDistance, vec3d2.z * maxDistance);
        return mc.world.raycast(new RaycastContext(position, vec3d3, RaycastContext.ShapeType.OUTLINE, includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, mc.player));
    }

    public static BlockHitResult rayCastBlock(Vec2f rotation, double maxDistance, boolean includeFluids) {
        Vec3d vec3d = mc.player.getCameraPosVec(0);
        Vec3d vec3d2 = RotationUtil.getVectorForRotation(rotation.x, rotation.y);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * maxDistance, vec3d2.y * maxDistance, vec3d2.z * maxDistance);
        return mc.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, mc.player));
    }

    public static BlockHitResult rayCastBlock(Vector2d rotation, double maxDistance, boolean includeFluids) {
        Vec3d vec3d = mc.player.getCameraPosVec(0);
        Vec3d vec3d2 = RotationUtil.getVectorForRotation((float) rotation.x, (float) rotation.y);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * maxDistance, vec3d2.y * maxDistance, vec3d2.z * maxDistance);
        return mc.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, mc.player));
    }

    public static boolean rayCastBlock(Tuple<BlockPos, Direction> tuple, Vec2f rotation, boolean strict) {
        BlockHitResult result = RayCastUtil.rayCastBlock(rotation, 3, false);
        return result.getBlockPos().equals(tuple.a) && (!strict || result.getSide().equals(tuple.b));
    }

    public static boolean rayCastBlock(Tuple<BlockPos, Direction> tuple, Vec3d position, Vec2f rotation, boolean strict) {
        BlockHitResult result = RayCastUtil.rayCastBlock(position, rotation, 3, false);
        return result.getBlockPos().equals(tuple.a) && (!strict || result.getSide().equals(tuple.b));
    }
}
