package today.sleek.client.modules.impl.player

import today.sleek.base.modules.ModuleData
import today.sleek.base.modules.ModuleCategory
import com.google.common.eventbus.Subscribe
import net.minecraft.network.Packet
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.base.event.impl.PacketEvent
import net.minecraft.network.play.client.C0FPacketConfirmTransaction
import today.sleek.base.value.value.BooleanValue
import today.sleek.client.modules.impl.Module
import today.sleek.client.utils.chat.ChatUtil

@ModuleData(
    name = "Transactions",
    category = ModuleCategory.PLAYER,
    description = "Checks if the server is using transactions to count ping"
)
class TransactionCounter : Module() {

    private val currTime = System.currentTimeMillis()
    private var lastC0F: Long = -1
    private var transactions = 0
    private val debugC0F = BooleanValue("Test C0F", this, true)
    private var startTime: Long = 0

    override fun onEnable() {
        startTime = System.currentTimeMillis()
    }

    @Subscribe
    fun onUpdate(event: UpdateEvent?) {
        val start = System.currentTimeMillis() - startTime
        if (start >= 20000) {
            if (transactions >= 10) {
                ChatUtil.log("This server seems to use transaction packets to count your ping.")
                ChatUtil.log("Using a transaction delaying or cancelling disabler is recommended.")
                toggle()
            } else {
                ChatUtil.log("It looks like this server doesn't use transaction packets to count your ping.")
                toggle()
            }
            ChatUtil.log("You've sent §c$transactions §ftransactions to the server.")
        }
    }

    @Subscribe
    fun onPacket(event: PacketEvent) {
        if (debugC0F.value && event.getPacket<Packet<*>>() is C0FPacketConfirmTransaction) {
            if (lastC0F == -1L) {
                lastC0F = System.currentTimeMillis()
                ChatUtil.log("Received first ConfirmTransaction packet")
            } else {
                ChatUtil.log("Received ConfirmTransaction (" + (System.currentTimeMillis() - lastC0F) + "ms since last transaction)")
            }
            transactions++
            lastC0F = System.currentTimeMillis()
            if (transactions > 10) {
            }
        }
    }
}