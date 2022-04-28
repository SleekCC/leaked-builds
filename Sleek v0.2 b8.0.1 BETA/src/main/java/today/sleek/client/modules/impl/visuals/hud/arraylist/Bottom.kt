package today.sleek.client.modules.impl.visuals.hud.arraylist

import net.minecraft.client.gui.Gui
import today.sleek.Sleek
import today.sleek.base.event.impl.RenderOverlayEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.client.modules.impl.Module
import today.sleek.client.modules.impl.visuals.HUD
import today.sleek.client.modules.impl.visuals.hud.ArrayListMode
import today.sleek.client.utils.render.ColorUtils
import today.sleek.client.utils.render.RenderUtils
import java.awt.Color


/**
 * @author Kansio
 */

class Bottom : ArrayListMode("Bottom") {

    override fun onRenderOverlay(event: RenderOverlayEvent) {
        val hud = hud
        HUD.notifications = hud.noti.value && hud.isToggled
        var y = RenderUtils.getResolution().scaledHeight - (hud.arrayListY.value.toInt() + 15)
        var index = 0
        var color: Color
        val sorted = Sleek.getInstance().moduleManager.getModulesSorted(mc.fontRendererObj)

        sorted.removeIf { m: Module -> !m.isToggled }

        if (hud.hideRender.value) sorted.removeIf { m: Module -> m.category == ModuleCategory.VISUALS }

        for (mod in sorted) {
            if (!mod.isToggled) continue
            index++
            color = ColorUtils.getColorFromHud(y)
            val name = mod.name + "§7" + mod.formattedSuffix
            val xPos = (event.sr.scaledWidth - mc.fontRendererObj.getStringWidth(name) - 6).toFloat()
            Gui.drawRect(
                xPos - 1.5,
                (y - 1).toDouble(),
                event.sr.scaledWidth.toDouble(),
                (mc.fontRendererObj.FONT_HEIGHT + y + 1).toDouble(),
                Color(0, 0, 0, hud.bgalpha.value).rgb
            )
            when (getHud().line.value) {
                "None" -> {}
                "Wrapped" -> Gui.drawRect(
                    xPos - 2.5,
                    (y - 1).toDouble(),
                    xPos - 1.5,
                    (mc.fontRendererObj.FONT_HEIGHT + y + 1).toDouble(),
                    color.rgb
                )
            }
            if (sorted.size > index) {
                val nextMod = sorted[index]
                val nextName = nextMod.name + "§7" + nextMod.formattedSuffix
                val nextxPos = (event.sr.scaledWidth - mc.fontRendererObj.getStringWidth(nextName) - 7.5).toFloat()
                when (getHud().line.value) {
                    "None" -> {}
                    "Wrapped" -> Gui.drawRect(
                        xPos - 2.5,
                        (y - mc.fontRendererObj.FONT_HEIGHT + 7).toDouble(),
                        nextxPos.toDouble(),
                        (y - mc.fontRendererObj.FONT_HEIGHT + 8).toDouble(),
                        color.rgb
                    )
                }
            } else {
                when (getHud().line.value) {
                    "None" -> {}
                    "Wrapped" -> Gui.drawRect(
                        xPos - 2.5,
                        (y - mc.fontRendererObj.FONT_HEIGHT + 7).toDouble(),
                        (xPos + 100).toDouble(),
                        (y - mc.fontRendererObj.FONT_HEIGHT + 8).toDouble(),
                        color.rgb
                    )
                }
            }
            mc.fontRendererObj.drawStringWithShadow(name, (xPos + 1.5).toFloat(), (0.5 + y).toFloat(), color.rgb)
            y -= 11
        }
    }
}
