package cc.sunshine.mixin.impl;

import cc.sunshine.ExampleMod;
import cc.sunshine.eventbus.impl.packet.impl.PacketReceiveEvent;
import cc.sunshine.eventbus.impl.packet.impl.PacketSendEvent;
import cc.sunshine.eventbus.impl.packet.impl.PostPacketReceiveEvent;
import cc.sunshine.eventbus.impl.packet.impl.PostPacketSendEvent;
import cc.sunshine.mixin.interfaces.IClientConnectionMixin;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin implements IClientConnectionMixin {
    @Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V", cancellable = true)
    public void send(Packet<?> packet, PacketCallbacks callbacks, CallbackInfo ci) {
        PacketSendEvent event = new PacketSendEvent(packet);
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);

        if(event.isCancelled()) {
            ci.cancel();
            return;
        }

        // TODO: This does not work, use @ModifyArg
        packet = event.getPacket();
    }

    @Inject(at = @At("HEAD"), method = "channelRead0", cancellable = true)
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        PacketReceiveEvent event = new PacketReceiveEvent(packet);
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);

        if(event.isCancelled()) {
            ci.cancel();
            return;
        }

        // TODO: This does not work, use @ModifyArg
        packet = event.getPacket();
    }

    @Inject(at = @At("RETURN"), method = "channelRead0")
    protected void postChannelRead0(ChannelHandlerContext ctx, Packet<?> packet, CallbackInfo ci) {
        PostPacketReceiveEvent event = new PostPacketReceiveEvent(packet);
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);
    }

    @Inject(at = @At("RETURN"), method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V", cancellable = true)
    public void postSend(Packet<?> packet, PacketCallbacks callbacks, CallbackInfo ci) {
        PostPacketSendEvent event = new PostPacketSendEvent(packet);
        ExampleMod.INSTANCE.getEventBus().publishEvent(event);
    }

    @Shadow
    private void sendImmediately(Packet<?> packet, @Nullable PacketCallbacks callbacks, boolean flush) {}

    @Shadow
    private void handleQueuedTasks(){}

    @Shadow private PacketListener packetListener;

    @Shadow
    protected static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener) {
    }

    @Override
    public void sendPacketNoEvent(Packet<?> packet, PacketCallbacks callbacks) {
        handleQueuedTasks();
        sendImmediately(packet, callbacks, true);
    }

    @Override
    public void sendPacket(Packet<?> packet, PacketCallbacks callbacks) {
        sendImmediately(packet, callbacks, true);
    }

    @Override
    public void apply(Packet<?> listener) {
        handlePacket(listener, packetListener);
    }
}
