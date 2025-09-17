package cc.sunshine.utils.player;

import cc.sunshine.utils.raycast.Vec3;
import lombok.Getter;
import net.minecraft.util.math.Direction;

@Getter
public class EnumFacingOffset {
    public Direction enumFacing;
    private final Vec3 offset;

    public EnumFacingOffset(final Direction enumFacing, final Vec3 offset) {
        this.enumFacing = enumFacing;
        this.offset = offset;
    }
}
