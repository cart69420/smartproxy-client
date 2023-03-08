package dev.cart.mixin.network;

import dev.cart.smartproxy.SmartProxy;
import dev.cart.smartproxy.event.network.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "channelRead0*", at = @At("HEAD"), cancellable = true)
    public void channelRead0Hook(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_, CallbackInfo ci) {
        PacketEvent.Receive per = new PacketEvent.Receive(p_channelRead0_2_);
        SmartProxy.INSTANCE.getBUS().post(per);
        if (per.getCancelled()) ci.cancel();
    }

    @Inject(method = "sendPacket*", at = @At("HEAD"), cancellable = true)
    public void sendPacketHook(Packet<?> packetIn, CallbackInfo ci) {
        PacketEvent.Send pes = new PacketEvent.Send(packetIn);
        SmartProxy.INSTANCE.getBUS().post(pes);
        if (pes.getCancelled()) ci.cancel();
    }
}
