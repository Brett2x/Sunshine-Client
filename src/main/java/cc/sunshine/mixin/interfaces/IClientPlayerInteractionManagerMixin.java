package cc.sunshine.mixin.interfaces;

import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public interface IClientPlayerInteractionManagerMixin {
    void syncSlot();
    void rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec);
    void rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec,
                                Hand hand);

    void sendPlayerActionC2SPacket(PlayerActionC2SPacket.Action action,
                                   BlockPos blockPos, Direction direction);
}
