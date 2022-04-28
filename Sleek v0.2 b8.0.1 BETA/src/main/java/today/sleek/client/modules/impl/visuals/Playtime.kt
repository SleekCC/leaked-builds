package today.sleek.client.modules.impl.visuals

import today.sleek.base.modules.ModuleData
import today.sleek.base.modules.ModuleCategory
import com.google.common.eventbus.Subscribe
import net.minecraft.client.gui.Gui
import net.minecraft.network.Packet
import today.sleek.base.event.impl.PacketEvent
import net.minecraft.network.handshake.client.C00Handshake
import net.minecraft.network.play.server.S02PacketChat
import today.sleek.base.event.impl.RenderOverlayEvent
import today.sleek.client.modules.impl.Module
import today.sleek.client.utils.render.ColorUtils
import today.sleek.client.utils.server.ServerUtil
import java.awt.Color

@ModuleData(name = "Session Info", category = ModuleCategory.VISUALS, description = "Shows how long you've been on for")
class Playtime : Module() {
    var startTime: Long = 0
    var totalKills = 0
    var totalDeaths = 0

    override fun onEnable() {
    }

    @Subscribe
    fun onPacket(event: PacketEvent) {
        if (event.getPacket<Packet<*>>() is C00Handshake) {
            startTime = System.currentTimeMillis()
        }
        if (event.getPacket<Packet<*>>() is S02PacketChat) {
            var packet: S02PacketChat = event.getPacket()
            var msg: String = packet.chatComponent.unformattedText

            if (ServerUtil.getServer() == "Menu") return

            when (ServerUtil.getServer().lowercase()) {
                "ghostly.live" -> {
                    if (msg.startsWith(mc.thePlayer.name) && msg.contains("was slain by")) {
                        totalDeaths++
                        return
                    }
                    if (msg.contains("was slain by") && msg.contains(mc.thePlayer.name)) {
                        totalKills++
                    }
                }
                "blocksmc.com" -> {
                    if (msg.startsWith(mc.thePlayer.name) && msg.contains("was killed by")) {
                        totalDeaths++
                        return
                    }
                    if (msg.contains("was killed by") && msg.contains(mc.thePlayer.name)) {
                        totalKills++
                    }
                }
            }
        }
    }

    @Subscribe
    fun onRender(event: RenderOverlayEvent) {
        var startY = 64
        var y = startY

        Gui.drawRect(5.0, startY + 5.0, 136.0, y + 68.0, Color(0, 0, 0, 105).rgb)
        Gui.drawRect(5.0, startY + 5.0, 136.0, startY + 6.0, ColorUtils.getColorFromHud(1).rgb)
        Gui.drawRect(9.0, startY + 15 + 9.0, 132.0, startY + 15 + 8.0, Color(67, 67, 67).rgb)

        var durationInMillis: Long = System.currentTimeMillis() - startTime

        val second: Long = durationInMillis / 1000 % 60
        val minute: Long = durationInMillis / (1000 * 60) % 60
        val hour: Long = durationInMillis / (1000 * 60 * 60) % 24

        val time = String.format("%02d:%02d:%02d", hour, minute, second)

        mc.fontRendererObj.drawString("Session Information: ", 8.0.toFloat(), (startY + 10.0).toFloat(), -1)
        mc.fontRendererObj.drawString("Playtime: $time", 8.0.toFloat(), (startY + 32.0).toFloat(), -1)
        mc.fontRendererObj.drawString("Total Kills: $totalKills", 8.0.toFloat(), (startY + 32.0 + 10).toFloat(), -1)
        mc.fontRendererObj.drawString(
            "Total Deaths: $totalDeaths",
            8.0.toFloat(),
            (startY + 32.0 + 10 + 10).toFloat(),
            -1
        )
    }
}