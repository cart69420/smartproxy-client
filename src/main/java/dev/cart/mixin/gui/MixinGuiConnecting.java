package dev.cart.mixin.gui;

import dev.cart.smartproxy.Communicator;
import net.minecraft.client.multiplayer.GuiConnecting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiConnecting.class)
public abstract class MixinGuiConnecting {
    @Inject(method = "connect", at = @At("HEAD"))
    public void connectHook(String ip, int port, CallbackInfo ci) {
        if (Communicator.INSTANCE.getENABLED()) {
            if (Communicator.INSTANCE.getTokenLogin())
                Communicator.INSTANCE.sendAccessToken();
            Communicator.INSTANCE.sendServerData(ip, port);
        }
    }

    @ModifyVariable(method = "connect", at = @At("HEAD"), index = 1, argsOnly = true)
    public String connectProxyIPHook(String value) {
        return Communicator.INSTANCE.getENABLED() ? Communicator.INSTANCE.getIP() : value;
    }

    @ModifyVariable(method = "connect", at = @At("HEAD"), index = 2, argsOnly = true)
    public int connectProxyPortHook(int value) {
        return Communicator.INSTANCE.getENABLED() ? Communicator.INSTANCE.getPPORT() : value;
    }
}