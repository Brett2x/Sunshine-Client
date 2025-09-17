package cc.sunshine.feature.module.impl.move;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.Event;
import cc.sunshine.eventbus.impl.PlayerUpdateEvent;
import cc.sunshine.eventbus.impl.packet.impl.PacketSendEvent;
import cc.sunshine.eventbus.impl.player.PlayerMoveEvent;
import cc.sunshine.eventbus.impl.player.PlayerNetworkUpdateEvent;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;
import cc.sunshine.feature.module.AbstractModule;
import cc.sunshine.feature.module.enums.ModuleCategory;
import cc.sunshine.feature.module.interfaces.ModuleData;
import cc.sunshine.feature.property.impl.BooleanProperty;
import cc.sunshine.feature.property.impl.EnumProperty;
import cc.sunshine.feature.property.impl.ModeProperty;
import cc.sunshine.feature.property.impl.interfaces.IEnumProperty;
import cc.sunshine.management.RotationManager;
import cc.sunshine.mixin.interfaces.IPlayerInteractEntityC2SPacketMixin;
import cc.sunshine.mixin.interfaces.IPlayerMoveC2SPacketMixin;
import cc.sunshine.utils.packet.PacketUtil;
import cc.sunshine.utils.player.InventoryUtil;
import cc.sunshine.utils.player.MoveUtil;
import cc.sunshine.utils.raycast.RayCastUtil;
import cc.sunshine.utils.rotation.RotationUtil;
import cc.sunshine.utils.tuple.Tuple;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.EmptyBlockView;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.Optional;

@ModuleData(name = "Scaffold", category = ModuleCategory.WORLD, key = GLFW.GLFW_KEY_R)
public final class ScaffoldModule extends AbstractModule {
    private final EnumProperty<RotationManager.MovementFixMode> movementFixMode = new EnumProperty<>("Movement fix", RotationManager.MovementFixMode.OFF);
    private final EnumProperty<RotationMode> rotationMode = new EnumProperty<>("Rotation mode", RotationMode.SMART);
    /*private final ModeProperty sprintMode = new ModeProperty("Sprint mode", "Off",
            new NullMode(this, "Off"),
            new ServerSideNoSprintSprintScaffold(this, "Server side no sprint"),
            new NoSprintSprintScaffold(this, "No Sprint"),
            new VulcanSprintScaffold(this, "Vulcan"));*/

    private final BooleanProperty keepRotation = new BooleanProperty("Keep rotation", false);
    private final BooleanProperty rayCast = new BooleanProperty("Ray cast", false);
    private final BooleanProperty noSwing = new BooleanProperty("No swing", false);
    private final BooleanProperty keepY = new BooleanProperty("Keep y", false);
    private final BooleanProperty sensitivityFix = new BooleanProperty("Sensitivity fix", false);

    private Vec2f rotation;
    private Integer y;

    @Override
    protected void onEnable() {
        rotation = null;
        y = null;
    }

    @Override
    protected void onDisable() {
        //LimeClient.INSTANCE.getSlotManager().resetSlot();
    }

    @SubscribeEvent
    private final Listener<PlayerUpdateEvent> playerUpdateEventListener = event -> {
        mc.player.setSprinting(false);
        //MoveUtil.strafe(0.15);
        if(event.getState() == PlayerUpdateEvent.State.POST) return;
        if (keepRotation.getValue() && rotation != null)
            ExampleMod.INSTANCE.getRotationManager().setNextRotation(rotation, RotationManager.MovementFixMode.ADVANCED);

        mc.player.setSneaking(System.currentTimeMillis() % 150 == 0);

        Optional<ItemStack> optBlockItemStack = getValidBlock();
        

        if (y == null || !MoveUtil.isMoving()) {
            y = (int) mc.player.getY() - 1;
        }

        if (!mc.world.getBlockState(mc.player.getBlockPos().down()).isAir()) {
            return;
        }

        Tuple<BlockPos, Direction> tuple = getNearestBlockData(new Vec3d(mc.player.getBlockX(), keepY.getValue() ? y : mc.player.getBlockY() - 1, mc.player.getBlockZ()));

        if (tuple == null) {
            return;
        }


        int newSlot = -1;
        for (int i = 0; i < 9; i++) {
            // filter out non-block items
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem))
                continue;

            // filter out non-solid blocks
            Block block = Block.getBlockFromItem(stack.getItem());
            BlockState state = block.getDefaultState();
            if (!state.isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN))
                continue;

            // filter out blocks that would fall
            if (block instanceof FallingBlock && FallingBlock
                    .canFallThrough(mc.world.getBlockState(mc.player.getBlockPos().down())))
                continue;

            newSlot = i;
            break;
        }

        // check if any blocks were found
        if (newSlot == -1)
            return;

        // set slot
        int oldSlot = mc.player.getInventory().selectedSlot;
        mc.player.getInventory().selectedSlot = newSlot;

        if(!(mc.player.getInventory().getMainHandStack().getItem() instanceof BlockItem)) return;

        if (rotationMode.getValue() != RotationMode.NONE) {
            Optional<Vec2f> optionalRotation = rotationMode.getValue().rotationCallback.getRotation(tuple);

            if (optionalRotation.isEmpty()) {
                optionalRotation = Optional.of(RotationUtil.aimBlock(tuple));
            }

            Vec2f rotation = optionalRotation.get();

            if (sensitivityFix.getValue()) {
                Vec2f currentRotation = this.rotation == null ? new Vec2f(mc.player.getYaw(), mc.player.getPitch()) : this.rotation;
                rotation = RotationUtil.applySensitivityPatch(currentRotation);
            }

            this.rotation = rotation;

            if (rayCast.getValue() && !RayCastUtil.rayCastBlock(tuple, rotation, true)) {
                return;
            }

            Vec2f rots = new Vec2f(mc.player.getYaw(), 90);

            if (rotationMode.getValue() != RotationMode.INVISIBLE)
                ExampleMod.INSTANCE.getRotationManager().forceNextRotation(rots, movementFixMode.getValue());
                //ExampleMod.INSTANCE.getRotationManager().forceNextRotation(new Vec2f(mc.player.getYaw() - 180, rotation.y), RotationManager.MovementFixMode.ADVANCED);

            else
                PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(),
                        mc.player.getY(), mc.player.getZ(),
                        rotation.x, rotation.y, mc.player.isOnGround()));



        }

        Vec3d pos = new Vec3d(tuple.a.getX(), tuple.a.getY(), tuple.a.getZ());
        BlockHitResult result = new BlockHitResult(pos, tuple.b, tuple.a, false);

        ExampleMod.INSTANCE.getEventBus().publishEvent(new PlayerScaffoldPlaceBlockEvent(false));

        if (mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, result) != ActionResult.SUCCESS) {
            return;
        }

        if (noSwing.getValue()) {
            mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
        } else {
            mc.player.swingHand(Hand.MAIN_HAND);
        }

        ExampleMod.INSTANCE.getEventBus().publishEvent(new PlayerScaffoldPlaceBlockEvent(true));

        if (rotationMode.getValue() == RotationMode.INVISIBLE)
            PacketUtil.sendPacketNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(),
                    mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
        //mc.player.getInventory().selectedSlot = oldSlot;
    };

    private Optional<ItemStack> getValidBlock() {
        ItemStack offHandStack = mc.player.getOffHandStack();

        if (!offHandStack.isEmpty() && offHandStack.getItem() instanceof BlockItem) {
            return Optional.of(offHandStack);
        }

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.isEmpty() || !(stack.getItem() instanceof BlockItem)) {
                continue;
            }

            return Optional.of(stack);
        }

        return Optional.empty();
    }

    private Tuple<BlockPos, Direction> getNearestBlockData(Vec3d posVec) {
        int radius = 3;

        BlockPos closestBlockPos = null;
        Direction closestFacing = null;
        double closestDistanceSq = Double.POSITIVE_INFINITY;


        for (int distance = -radius; distance < radius; distance++) {
            for (BlockPos offsetPos : BlockPos.iterate(new BlockPos(-distance, -distance, -distance), new BlockPos(distance, distance, distance))) {
                Vec3d vec = posVec.add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
                Direction facing = Direction.getFacing(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ()).getOpposite();

                if(facing == Direction.DOWN) continue;

                BlockPos blockPos = new BlockPos((int) vec.getX(), (int) vec.getY(), (int) vec.getZ());

                if (mc.world.getBlockState(blockPos).isAir()) {
                    continue;
                }

                double distanceSq = posVec.distanceTo(vec.offset(facing, 1));

                if (distanceSq < closestDistanceSq) {
                    closestBlockPos = blockPos;
                    closestFacing = facing;
                    closestDistanceSq = distanceSq;
                }


            }
        }

        if (closestBlockPos == null || closestFacing == null) {
            return null;
        }

        return new Tuple<>(closestBlockPos, closestFacing);
    }

    public static class PlayerScaffoldPlaceBlockEvent extends Event {
        private final boolean post;

        public PlayerScaffoldPlaceBlockEvent(boolean post) {
            this.post = post;
        }

        public boolean isPost() {
            return post;
        }
    }

    private enum RotationMode implements IEnumProperty {
        NONE("NONE", null),
        SMART("Smart", RotationUtil::bruteforceRotation),
        VULCAN("Vulcan", tuple -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            return Optional.of(new Vec2f(Direction.fromRotation(Math.toDegrees(MoveUtil.direction())).asRotation() - 180, player.getPitch()));
        }),
        INVISIBLE("Invis", RotationUtil::bruteforceRotation);

        private final String name;
        private final IRotationMode rotationCallback;

        RotationMode(String name, IRotationMode rotationCallback) {
            this.name = name;
            this.rotationCallback = rotationCallback;
        }

        public String getName() {
            return name;
        }
    }

    private interface IRotationMode {
        Optional<Vec2f> getRotation(Tuple<BlockPos, Direction> tuple);
    }
}


