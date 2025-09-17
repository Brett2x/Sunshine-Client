package cc.sunshine.utils.player;

import cc.sunshine.utils.IMinecraft;
import cc.sunshine.utils.raycast.Vec3;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class PlayerUtil implements IMinecraft {
    public static void setVelocityY(double velocityY) {
        mc.player.setVelocity(mc.player.getVelocity().x, velocityY, mc.player.getVelocity().z);
    }

    public static boolean canBlock() {
        /*if(ProtocolHack.getTargetVersion().isOlderThanOrEqualTo(VersionEnum.r1_8)) {
            ItemStack stack = mc.player.getMainHandStack();

            if(!stack.isEmpty() && stack.getItem() instanceof SwordItem) {
                return true;
            }
        }*/

        ItemStack offHandStack = mc.player.getOffHandStack();
        return !offHandStack.isEmpty() && offHandStack.getItem() instanceof ShieldItem;
    }
}
