package today.sleek.client.modules.impl.visuals.hud.watermark

import net.minecraft.client.Minecraft
import today.sleek.base.event.impl.RenderOverlayEvent
import today.sleek.client.modules.impl.visuals.hud.WaterMarkMode
import today.sleek.client.utils.network.UserUtil

/**
 * @author Kansio
 */
class Simple : WaterMarkMode("Simple") {

    override fun onRenderOverlay(event: RenderOverlayEvent) {
        mc.fontRendererObj.drawStringWithShadow("§7" + hud.clientName.value + " §f- §d(${UserUtil.getBuildType()}) §7Build",
            4.0F, 4.0F, -1)
        mc.fontRendererObj.drawStringWithShadow("§7FPS: ${Minecraft.getDebugFPS()}",
            4.0F, 14.0F, -1)
    }
}