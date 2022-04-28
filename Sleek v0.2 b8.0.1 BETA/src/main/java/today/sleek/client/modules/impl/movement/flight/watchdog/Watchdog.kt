package today.sleek.client.modules.impl.movement.flight.watchdog

import net.minecraft.network.Packet
import net.minecraft.network.play.client.C00PacketKeepAlive
import net.minecraft.network.play.client.C03PacketPlayer
import net.minecraft.network.play.client.C0FPacketConfirmTransaction
import net.minecraft.network.play.server.S08PacketPlayerPosLook
import today.sleek.base.event.impl.MoveEvent
import today.sleek.base.event.impl.PacketEvent
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.client.modules.impl.movement.flight.FlightMode
import today.sleek.client.utils.network.PacketUtil
import today.sleek.client.utils.player.PlayerUtil

class Watchdog : FlightMode("Hypixel") {

    var dontgo = true
    var waiting = false
    var blinking = false
    var list = mutableListOf<Packet<*>>()

    override fun onUpdate(event: UpdateEvent?) {
        if (event!!.isPre) {
            if ((dontgo && !waiting) && mc.thePlayer.onGround) {
                event.posY = mc.thePlayer.posY + 0.017
                mc.thePlayer.motionY = 0.4 - 0.22
                waiting = true
                blinking = false
                mc.thePlayer.onGround = false
            }
            if (waiting && mc.thePlayer.onGround) {
                mc.thePlayer.motionY -= 1.0E-7
                event.posY += mc.thePlayer.motionY
                event.isOnGround = true
                blinking = false
            }
            if (!waiting && !dontgo) {
                mc.thePlayer.motionY = 0.0
                if (mc.thePlayer.ticksExisted % 11 == 0) {
                    for (packet in list) {
                        PacketUtil.sendPacketNoEvent(packet)
                    }
                    list.clear()
                }
                if (mc.timer.timerSpeed > 1.1)
                    mc.timer.timerSpeed -= mc.timer.timerSpeed / (259f * mc.timer.timerSpeed)
            } else {
                mc.thePlayer.motionX = 0.0
            }
        }
    }

    override fun onMove(event: MoveEvent?) {
        if (!waiting && !dontgo) {
            PlayerUtil.setMotion(event, PlayerUtil.getBaseSpeed().toDouble())
//            PlayerUtil.setMotion(event, PlayerUtil.getBaseSpeed() + (0.1 * if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier else 0))
        } else {
            event!!.motionX = 0.0.also { mc.thePlayer.motionX = it }
            event.motionZ = 0.0.also { mc.thePlayer.motionZ = it }
        }
    }

    override fun onPacket(event: PacketEvent?) {
        val packet = event!!.getPacket<Packet<*>>()
        if (event!!.getPacket<Packet<*>>() is S08PacketPlayerPosLook) {
            waiting = false
            dontgo = false
            blinking = true
            mc.timer.timerSpeed = flight.timer.value.toFloat()
        }
        if (blinking) {
            when (packet) {
                is C00PacketKeepAlive -> {
                    event.isCancelled = true
                    list.add(packet)
                }
                is C03PacketPlayer -> {
                    event.isCancelled = true
                    list.add(packet)

                }
                is C0FPacketConfirmTransaction -> {
                    event.isCancelled = true
                    list.add(packet)
                }
            }
        }
    }

    override fun onEnable() {
        if (!mc.thePlayer.onGround) {
            flight.toggle()
        }
        dontgo = true
        waiting = false
        blinking = false
    }

    override fun onDisable() {
        for (packet in list) {
            PacketUtil.sendPacketNoEvent(packet)
        }
        list.clear()
    }
}