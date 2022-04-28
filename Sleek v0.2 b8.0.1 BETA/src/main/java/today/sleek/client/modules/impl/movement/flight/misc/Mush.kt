package today.sleek.client.modules.impl.movement.flight.misc

import net.minecraft.block.BlockAir
import net.minecraft.network.Packet
import net.minecraft.network.play.client.C03PacketPlayer
import today.sleek.base.event.impl.BlockCollisionEvent
import today.sleek.base.event.impl.MoveEvent
import today.sleek.base.event.impl.PacketEvent
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.client.modules.impl.movement.flight.FlightMode
import today.sleek.client.utils.network.PacketUtil
import today.sleek.client.utils.player.PlayerUtil
import today.sleek.client.utils.player.TimerUtil

class Mush : FlightMode("Mush") {
    var speedy = 2.5
    var blinking = false

    private val c03Packets: ArrayList<out C03PacketPlayer> = ArrayList()

    override fun onUpdate(event: UpdateEvent) {
        if (mc.thePlayer.ticksExisted % 18 == 0) {
            mc.timer.timerSpeed = 1.0F
            stopBlink()
        }
        if (mc.thePlayer.isMoving) {

            if (speedy > 0.22) {
                speedy -= 0.01
            }
        } else {
            TimerUtil.Reset()
            speedy = 0.0
        }
    }

    override fun onMove(event: MoveEvent) {
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            event.motionY = 0.22.also {
                mc.thePlayer.motionY = it
                mc.thePlayer.setPosition(
                    mc.thePlayer.posX, mc.thePlayer.posY + 8E-6, mc.thePlayer.posZ
                )
            }
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            event.motionY = (-0.22).also {
                mc.thePlayer.motionY = it
                mc.thePlayer.setPosition(
                    mc.thePlayer.posX, mc.thePlayer.posY - 8.2E-6, mc.thePlayer.posZ
                )
            }
        } else {
            event.motionY = 0.0.also {
                mc.thePlayer.motionY = it
                mc.thePlayer.setPosition(
                    mc.thePlayer.posX, mc.thePlayer.posY - 8E-6, mc.thePlayer.posZ
                )
            }
        }
        PlayerUtil.setMotion(Math.max(speedy, PlayerUtil.getVerusBaseSpeed()))
    }

    override fun onPacket(event: PacketEvent) {
        if (blinking) {
            if (event.getPacket<Packet<*>>() is C03PacketPlayer) {
                c03Packets.add(event.getPacket())
            }
        }
    }

    override fun onEnable() {
        speedy = flight.speed.value
        mc.timer.timerSpeed = flight.timer.value.toFloat()
        blinking = flight.blink.value
    }

    fun stopBlink() {
        for (packetPlayer in c03Packets) {
            PacketUtil.sendPacketNoEvent(packetPlayer)
        }
        c03Packets.clear()
        blinking = false
    }

    override fun onDisable() {
        super.onDisable()
        stopBlink()
    }

    override fun onCollide(event: BlockCollisionEvent) {
        if (event.block is BlockAir) {
            if (mc.thePlayer.isSneaking) return
            val x = event.x.toDouble()
            val y = event.y.toDouble()
            val z = event.z.toDouble()
            if (y < mc.thePlayer.posY - 8E-6) {
//                event.axisAlignedBB = AxisAlignedBB.fromBounds(-5.0, -1.0, -5.0, 5.0, 1.0, 5.0).offset(x, y, z)
            }
        }
    }
}