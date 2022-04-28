package today.sleek.client.modules.impl.visuals.hud.arraylist

import today.sleek.Sleek
import today.sleek.base.event.impl.RenderOverlayEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.client.modules.impl.Module
import today.sleek.client.modules.impl.visuals.HUD
import today.sleek.client.modules.impl.visuals.hud.ArrayListMode
import today.sleek.client.utils.render.ColorUtils
import java.awt.Color


/**
 * @author Kansio
 */
class Left : ArrayListMode("Left") {

    override fun onRenderOverlay(event: RenderOverlayEvent) {
        val hud = hud
        HUD.notifications = hud.noti.value && hud.isToggled
        var y = hud.arrayListY.value.toInt()
        var index = 0
        var color: Color
        val sorted = Sleek.getInstance().moduleManager.getModulesSorted(mc.fontRendererObj) as ArrayList<Module>
        sorted.removeIf { m: Module -> !m.isToggled }

        if (hud.hideRender.value)
            sorted.removeIf { m: Module -> m.category == ModuleCategory.VISUALS }

        for (mod in sorted) {
            if (!mod.isToggled) continue
            index++
            color = ColorUtils.getColorFromHud(y)
            val name = mod.name + "§f" + mod.formattedSuffix

            mc.fontRendererObj.drawStringWithShadow(name, getHud().arrayListX.value.toFloat(), (0.5 + y).toFloat(), color.rgb)
            y += 11
        }
    }
}