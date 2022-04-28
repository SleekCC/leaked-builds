package today.sleek.client.modules.impl.world

import com.google.common.eventbus.Subscribe
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.modules.ModuleData
import today.sleek.base.value.value.NumberValue
import today.sleek.client.modules.impl.Module


/**
 * @author Kansio
 */
@ModuleData(
    name = "Fast Place",
    description = "Makes you place stuff faster",
    category = ModuleCategory.WORLD
)
class FastPlace : Module() {

    private val delay = NumberValue("Delay", this, 0, 0, 6, 1)

    @Subscribe
    fun onUpdate(event: UpdateEvent) {
        mc.rightClickDelayTimer = delay.value
    }

    override fun onDisable() {
        mc.rightClickDelayTimer = 6
    }
}