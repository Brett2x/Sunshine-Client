package cc.sunshine.mixin.impl;

import cc.sunshine.mixin.interfaces.IEntityVelocityUpdateS2CPacketMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityVelocityUpdateS2CPacket.class)
public abstract class EntityVelocityUpdateS2CPacketMixin implements IEntityVelocityUpdateS2CPacketMixin {

    @Accessor("velocityX") @Mutable
    public abstract void setX(int x);

    @Accessor("velocityY") @Mutable
    public abstract void setY(int x);

    @Accessor("velocityZ")@Mutable
    public abstract void setZ(int x);

    @Override
    public void setXVelo(int x) {
        System.out.println("X");
        setX(x);
    }

    @Override
    public void setYVelo(int y) {
        setY(y);
    }

    @Override
    public void setZVelo(int z) {
        System.out.println("Z");
        setZ(z);
    }
}
