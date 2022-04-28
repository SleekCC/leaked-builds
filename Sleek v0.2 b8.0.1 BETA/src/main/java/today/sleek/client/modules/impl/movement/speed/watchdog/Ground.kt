package today.sleek.client.modules.impl.movement.speed.watchdog

import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition
import today.sleek.base.event.impl.MoveEvent
import today.sleek.client.modules.impl.movement.speed.SpeedMode
import today.sleek.client.utils.network.PacketUtil
import today.sleek.client.utils.player.PlayerUtil


/**
 * @author Kansio
 */
class Ground : SpeedMode("Watchdog Ground") {

    override fun onMove(event: MoveEvent) {
        if (mc.thePlayer.isMoving && mc.thePlayer.onGround && !mc.thePlayer.isCollidedHorizontally) {
            PacketUtil.sendPacket(
                C04PacketPlayerPosition(
                    mc.thePlayer.posX + event.motionX,
                    mc.thePlayer.posY,
                    mc.thePlayer.posZ + event.motionZ,
                    true
                )
            )

            event.motionX = event.motionX * 2.0
            event.motionZ = event.motionZ * 2.0

            PlayerUtil.setMotion(event, PlayerUtil.getBaseMoveSpeed(mc.thePlayer) * 2.0)
        }
    }
}