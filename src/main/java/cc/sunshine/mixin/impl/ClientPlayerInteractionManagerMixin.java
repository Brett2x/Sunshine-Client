package cc.sunshine.mixin.impl;

import cc.sunshine.mixin.interfaces.IClientPlayerInteractionManagerMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin implements IClientPlayerInteractionManagerMixin {
    @Shadow
    public void syncSelectedSlot(){}

    @Shadow
    public abstract ActionResult interactItem(PlayerEntity player, Hand hand);

    @Shadow
    public abstract ActionResult interactBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult);

    @Shadow
    private void sendSequencedPacket(ClientWorld world,
                                     SequencedPacketCreator packetCreator)
    {

    }

    @Override
    public void syncSlot() {
        syncSelectedSlot();
    }

    @Override
    public void rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec) {
        rightClickBlock(pos, side, hitVec, Hand.MAIN_HAND);
    }

    @Override
    public void rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec, Hand hand) {
        BlockHitResult hitResult = new BlockHitResult(hitVec, side, pos, false);
        interactBlock(MinecraftClient.getInstance().player, hand, hitResult);
        interactItem(MinecraftClient.getInstance().player, hand);
    }

    @Override
    public void sendPlayerActionC2SPacket(PlayerActionC2SPacket.Action action, BlockPos blockPos, Direction direction) {
        sendSequencedPacket(MinecraftClient.getInstance().world,
                i -> new PlayerActionC2SPacket(action, blockPos, direction, i));
    }
}
