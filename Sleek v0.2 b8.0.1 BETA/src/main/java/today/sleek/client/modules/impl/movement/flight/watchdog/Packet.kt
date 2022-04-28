package today.sleek.client.modules.impl.movement.flight.watchdog

import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition
import net.minecraft.network.play.server.S08PacketPlayerPosLook
import today.sleek.base.event.impl.MoveEvent
import today.sleek.base.event.impl.PacketEvent
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.client.modules.impl.movement.flight.FlightMode
import today.sleek.client.utils.chat.ChatUtil
import today.sleek.client.utils.math.MathUtil
import today.sleek.client.utils.network.PacketUtil
import today.sleek.client.utils.player.PlayerUtil
import java.util.*

class Packet: FlightMode("Watchdog Packet") {
    var ready = false;
    override fun onUpdate(event: UpdateEvent?) {
        val x = mc.thePlayer.posX
        val y = mc.thePlayer.posY
        val z = mc.thePlayer.posZ
        if (!ready && mc.thePlayer.onGround) {
            PacketUtil.sendPacketNoEvent(C04PacketPlayerPosition(x, y, z, true))
            var jumpvals = listOf(0.41999998688698, 0.7531999805212,  1.00133597911214, 1.16610926093821, 1.24918707874468, 1.24918707874468, 1.1707870772188, 1.0155550727022, 0.78502770378923, 0.48071087633169, 0.10408037809304)
            for (jumpval in jumpvals) {
                ChatUtil.log("${y + jumpval}")
                PacketUtil.sendPacketNoEvent(C04PacketPlayerPosition(x, y + jumpval, z, false))
            }
            PacketUtil.sendPacketNoEvent(C04PacketPlayerPosition(x, y, z, true))
            event!!.posY -= 0.0784F + MathUtil.getRandomInRange(0.0005f, 0.0154f)
            event?.isOnGround = true
            ready = true
        }
    }

    override fun onMove(event: MoveEvent?) {
        if (ready) {
            event?.motionY = 0.0.also {mc.thePlayer.motionY = it}
            PlayerUtil.setMotion(event, PlayerUtil.getBaseSpeed().toDouble() * 0.8)
        } else {
            event!!.motionX = 0.0.also {mc.thePlayer.motionX = it}
            event.motionZ = 0.0.also {mc.thePlayer.motionZ = it}
        }
    }

    override fun onPacket(event: PacketEvent?) {
        val packet = event!!.getPacket<net.minecraft.network.Packet<*>>()
        if (packet is S08PacketPlayerPosLook) {
            mc.thePlayer.performHurtAnimation()
            ready = true
        }
    }

    override fun onEnable() {
        ready = false
    }
}