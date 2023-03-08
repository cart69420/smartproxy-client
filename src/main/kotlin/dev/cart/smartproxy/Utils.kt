package dev.cart.smartproxy

import net.minecraft.client.Minecraft
import net.minecraft.util.text.ChatType
import net.minecraft.util.text.TextComponentString
import java.net.HttpURLConnection
import java.net.URL

inline val mc get() = Minecraft.getMinecraft()

fun info(str: String) = mc.ingameGUI.addChatMessage(ChatType.CHAT, TextComponentString("[SmartProxy] $str"))

fun isHovered(x: Float, y: Float, width: Float, height: Float, mouseX: Int, mouseY: Int): Boolean =
    mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height

fun httpGetRequest(url: String, func: (statusCode: Int) -> Unit) {
    var conn: HttpURLConnection? = null
    Thread {
        try {
            conn = URL(url).openConnection() as HttpURLConnection
            func(conn!!.responseCode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        conn!!.disconnect()
    }
}