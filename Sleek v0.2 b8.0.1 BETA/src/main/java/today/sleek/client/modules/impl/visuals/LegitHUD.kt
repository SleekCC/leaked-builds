package today.sleek.client.modules.impl.visuals

import com.google.common.eventbus.Subscribe
import today.sleek.Sleek
import today.sleek.base.event.impl.RenderOverlayEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.modules.ModuleData
import today.sleek.base.value.value.BooleanValue
import today.sleek.base.value.value.NumberValue
import today.sleek.client.modules.impl.Module


/**
 * @author Kansio
 */

@ModuleData(
    name = "Legit HUD",
    category = ModuleCategory.VISUALS,
    description = "''Legit'' looking hud"
)
class LegitHUD : Module() {

    private val toggleSneak = BooleanValue("Toggle Sprint", this, true)
    private val tsX = NumberValue("Toggle Sprint X", this, 5, 0, 3000, 1, toggleSneak)
    private val tsY = NumberValue("Toggle Sprint Y", this, 5, 0, 3000, 1, toggleSneak)

    @Subscribe
    fun onRender(event: RenderOverlayEvent) {
        if (toggleSneak.value) {
            if (!Sleek.getInstance().moduleManager.getModuleByName("Sprint").isToggled) {
                if (mc.thePlayer.isSprinting) {
                    mc.fontRendererObj.drawStringWithShadow(
                        "[Sprinting (Key Held)]",
                        tsX.value.toFloat(),
                        tsY.value.toFloat(),
                        -1
                    )
                }
            } else {
                mc.fontRendererObj.drawStringWithShadow(
                    "[Sprinting (Toggled)]",
                    tsX.value.toFloat(),
                    tsY.value.toFloat(),
                    -1
                )
            }
        }
    }

}