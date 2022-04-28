package today.sleek.client.irc

import com.google.gson.GsonBuilder
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import today.sleek.Sleek
import today.sleek.client.utils.chat.ChatUtil
import java.net.URI
import java.nio.charset.StandardCharsets
import java.util.*

//class IRCClient : WebSocketClient(URI("ws://zerotwoclient.xyz:6534")) {
class IRCClient : WebSocketClient(URI("ws://irc.sleek.today")) {
    init {
        setAttachment("&${Sleek.getInstance().rank.color.formattingCode}${Sleek.getInstance().username}")
        addHeader("name", getAttachment())
        addHeader("uid", Sleek.getInstance().uid)
        connectionLostTimeout = 100
    }

    override fun onOpen(serverHandshake: ServerHandshake) {
        println("IRC Connected")
        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§7[§bIRC§7] §fConnected"))
    }

    override fun onMessage(s: String) {
        val decoded = base64Decode(s)
        println(decoded)
        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§7[§bIRC§7] §f» ${ChatUtil.translateColorCodes(decoded)}"))
        //»
    }

    override fun onClose(i: Int, s: String, b: Boolean) {
        println("IRC Disconnect ($i $s $b)")
        Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("§7[§bIRC§7] §fDisconnected"))
    }

    override fun onError(e: Exception) {
        e.printStackTrace()
    }

    fun base64Encode(input: String): String {
        return String(Base64.getEncoder().encode(input.toByteArray(StandardCharsets.UTF_8)))
    }

    fun base64Decode(input: String): String {
        return String(Base64.getDecoder().decode(input.toByteArray(StandardCharsets.UTF_8)))
    }

    fun base64Encode(input: ByteArray): ByteArray {
        return Base64.getEncoder().encode(input)
    }

    fun base64Decode(input: ByteArray): ByteArray {
        return Base64.getDecoder().decode(input)
    }

    fun sendMessage(msg: String) {
        val message = Message("&${EnumChatFormatting.WHITE.formattingCode}${msg}", Author(getAttachment(), Sleek.instance().uid))
        val gson = GsonBuilder().setPrettyPrinting().create()
        val nigga = gson.toJson(message)
        val encoded = base64Encode(nigga)
        send(encoded)
    }

}

data class Message(val message: String, val author: Author)
data class Author(val name: String, val uid: String)