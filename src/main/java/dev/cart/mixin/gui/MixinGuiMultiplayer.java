package dev.cart.mixin.gui;

import dev.cart.smartproxy.SmartProxy;
import dev.cart.smartproxy.SmartProxyGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public class MixinGuiMultiplayer extends GuiScreen {
    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGuiHook(CallbackInfo ci) {
        this.buttonList.add(new GuiButton(8085, 5, height - 25, 100, 20, "SmartProxy"));
    }

    @Inject(method = "actionPerformed", at = @At("TAIL"))
    public void actionPerformedHook(GuiButton button, CallbackInfo ci) {
        if (button.id == 8085) {
            mc.displayGuiScreen(new SmartProxyGui(this));
        }
    }
}
