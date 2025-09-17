package cc.sunshine.mixin.impl;

import cc.sunshine.mixin.interfaces.IPlayerMoveC2SPacketMixin;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerMoveC2SPacket.class)
public abstract class PlayerMoveC2SPacketMixin implements IPlayerMoveC2SPacketMixin {

    //@Shadow() @Final protected float yaw;

    @Shadow @Final protected float pitch;
    private float yaw;

    private float newYaw;

    public void changeYaw() {
        newYaw = yaw;
    }

    @Accessor("yaw") @Mutable
    public abstract void setYaw(float newerYaw);

    @Accessor("onGround") @Mutable
    public abstract void setGround(boolean ground);

    @Accessor("pitch") @Mutable
    public abstract void setPitch(float pitch);
    @Accessor("x") @Mutable
    public abstract void setX(double x);
    @Accessor("y") @Mutable
    public abstract void setY(double y);
    @Accessor("z") @Mutable
    public abstract void setZ(double z);

    @Override
    public void yawSetter(float yaw) {
        setYaw(yaw);
    }

    @Override
    public void pitchSetter(float pitch) {
        setPitch(pitch);
    }

    @Override
    public void groundSetter(boolean ground) {
        setGround(ground);
    }

    @Override
    public void xSetter(double x) {
        setX(x);
    }

    @Override
    public void ySetter(double y) {
        setY(y);
    }

    @Override
    public void zSetter(double z) {
        setZ(z);
    }
}
