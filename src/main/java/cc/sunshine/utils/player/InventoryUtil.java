package cc.sunshine.utils.player;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class InventoryUtil {

    public static final int ONLY_HOT_BAR_BEGIN = 36;
    public static final int END = 45;

    public static boolean validateBlock(final Block block, final BlockAction action) {
        //if (block instanceof BlockConta) return false;
        //final Material material = block.getMaterial();

        BlockState state = Objects.requireNonNull(MinecraftClient.getInstance().world).getBlockState(new BlockPos(
                (int) MinecraftClient.getInstance().player.getX(), (int) (MinecraftClient.getInstance().player.getY() - 1),
                (int) MinecraftClient.getInstance().player.getZ()));

        if(state.hasBlockEntity()) return false;

        switch (action) {
            case PLACE:
                return false;
            case REPLACE:
                return true;
            case PLACE_ON:
                return state.isAir();
        }

        return false;
    }

    public enum BlockAction {
        PLACE, REPLACE, PLACE_ON
    }

}
