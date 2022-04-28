package today.sleek.client.modules.impl.player

import com.google.common.eventbus.Subscribe
import net.minecraft.network.Packet
import net.minecraft.network.play.client.C0DPacketCloseWindow
import today.sleek.base.event.impl.PacketEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.modules.ModuleData
import today.sleek.client.modules.impl.Module


/**
 * @author Kansio
 */
@ModuleData(
    name = "More Inventory",
    description = "Allows you to use your crafting slots to store items",
    category = ModuleCategory.PLAYER
)
class MoreInventory : Module() {

    @Subscribe
    fun onPacket(event: PacketEvent) {
        if (event.getPacket<Packet<*>>() is C0DPacketCloseWindow) {
            event.isCancelled = true
        }
    }

}