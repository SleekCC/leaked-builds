package today.sleek.client.modules.impl.movement.flight.misc

import net.minecraft.network.play.client.C03PacketPlayer
import net.minecraft.network.Packet
import today.sleek.Sleek
import today.sleek.base.event.impl.MoveEvent
import today.sleek.base.event.impl.PacketEvent
import today.sleek.client.modules.impl.Module
import today.sleek.client.modules.impl.exploit.Disabler
import today.sleek.client.modules.impl.movement.flight.FlightMode
import today.sleek.client.utils.player.PlayerUtil
import javax.vecmath.Vector3d

class TestDisablerShid: FlightMode("Test2 Disabler") {

    override fun onMove(event: MoveEvent?) {
        event!!.motionY = 0.0.also { mc.thePlayer.motionY = it }
        PlayerUtil.setMotion(event, 0.6)
    }

    override fun onPacket(event: PacketEvent?) {
        val packet = event!!.getPacket<Packet<*>>()
        if (packet is C03PacketPlayer) {
            event.isCancelled = true
        }
    }

    override fun onDisable() {
        val funny = Sleek.getInstance().moduleManager.getModuleByName("Disabler") as Disabler
        funny.repeatPos = Vector3d(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)
    }
}