package dev.cart.smartproxy

import com.mojang.realmsclient.gui.ChatFormatting
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.GuiTextField
import org.lwjgl.input.Keyboard
import java.awt.Color

class SmartProxyGui(private val lastScreen: GuiScreen?) : GuiScreen() {
    private var enabled = false
    private var doLoginWithToken = false
    private var fieldIP: GuiTextField? = null
    private var fieldProxyPort: GuiTextField? = null
    private var fieldSocketPort: GuiTextField? = null

    override fun initGui() {
        fieldIP = GuiTextField(0, mc.fontRenderer, width / 2 - 50, height / 2 - 90, 100, 15)
        fieldProxyPort = GuiTextField(0, mc.fontRenderer, width / 2 - 50, height / 2 - 65, 100, 15)
        fieldSocketPort = GuiTextField(0, mc.fontRenderer, width / 2 - 50, height / 2 - 35, 100, 15)

        enabled = Communicator.ENABLED
        doLoginWithToken = Communicator.tokenLogin
        fieldIP?.text = Communicator.IP
        fieldProxyPort?.text = Communicator.pPORT.toString()
        fieldSocketPort?.text = Communicator.sPORT.toString()

        buttonList.add(GuiButton(0, 5, 5, 50, 20, "Back"))
        buttonList.add(GuiButton(1, width / 2 - 60, height / 2, 120, 20, ""))
        buttonList.add(GuiButton(2, width / 2 - 75, height / 2 + 30, 150, 20, ""))
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        drawCenteredString(mc.fontRenderer, "SmartProxy - 1.0", width / 2, 5, Color.WHITE.rgb)

        buttonList[1].displayString = "Communication: ${if (enabled) ChatFormatting.GREEN.toString() + "ON" else ChatFormatting.RED.toString() + "OFF"}"
        buttonList[2].displayString = "Session Token Login: ${if (doLoginWithToken) ChatFormatting.GREEN.toString() + "ON" else ChatFormatting.RED.toString() + "OFF"}"

        (fieldIP ?: return).drawTextBox()
        (fieldProxyPort ?: return).drawTextBox()
        (fieldSocketPort ?: return).drawTextBox()

        if ((fieldIP ?: return).text.isEmpty() && (fieldIP ?: return).isFocused) {
            drawString(mc.fontRenderer, "Proxy IP", width / 2 - 47, height / 2 - 88, Color.GRAY.rgb)
        }

        if ((fieldProxyPort ?: return).text.isEmpty() && (fieldProxyPort ?: return).isFocused) {
            drawString(mc.fontRenderer, "Proxy Port", width / 2 - 47, height / 2 - 63, Color.GRAY.rgb)
        }

        if ((fieldSocketPort ?: return).text.isEmpty() && (fieldSocketPort ?: return).isFocused) {
            drawString(mc.fontRenderer, "Socket Port", width / 2 - 47, height / 2 - 33, Color.GRAY.rgb)
        }

        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        fieldIP?.textboxKeyTyped(typedChar, keyCode)
        fieldProxyPort?.textboxKeyTyped(typedChar, keyCode)
        fieldSocketPort?.textboxKeyTyped(typedChar, keyCode)

        if (keyCode == Keyboard.KEY_ESCAPE) actionPerformed(buttonList[0])
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        fieldIP?.mouseClicked(mouseX, mouseY, mouseButton)
        fieldProxyPort?.mouseClicked(mouseX, mouseY, mouseButton)
        fieldSocketPort?.mouseClicked(mouseX, mouseY, mouseButton)
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun actionPerformed(button: GuiButton) {
        when (button.id) {
            0 -> {
                Communicator.ENABLED = enabled
                Communicator.tokenLogin = doLoginWithToken
                Communicator.IP = fieldIP?.text ?: "localhost"
                Communicator.pPORT = fieldProxyPort?.text?.toIntOrNull() ?: 25566
                Communicator.sPORT = fieldSocketPort?.text?.toIntOrNull() ?: 6969

                mc.displayGuiScreen(lastScreen)
            }
            1 -> enabled = !enabled
            2 -> doLoginWithToken = !doLoginWithToken
        }
    }
}