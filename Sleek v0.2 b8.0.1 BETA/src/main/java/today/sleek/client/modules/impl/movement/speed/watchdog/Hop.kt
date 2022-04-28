package today.sleek.client.modules.impl.movement.speed.watchdog

import today.sleek.base.event.impl.MoveEvent
import today.sleek.client.modules.impl.movement.speed.SpeedMode
import today.sleek.client.utils.player.PlayerUtil

class Hop : SpeedMode("Watchdog (New)") {

    override fun onMove(event: MoveEvent) {
        if (mc.thePlayer.isMovingOnGround) {
            mc.timer.timerSpeed = 1.0f
            event.motionY = 0.35.also { mc.thePlayer.motionY = it }
        }
        if (mc.thePlayer.fallDistance > 0) {
            event.motionY = (-0.42).also { mc.thePlayer.motionY = it }
        }
        if (!mc.thePlayer.onGround) {
            mc.timer.timerSpeed = speed.timer.value
            //speed -= speed / 152
            mc.thePlayer.motionX *= 0.9
            mc.thePlayer.motionZ *= 0.9
        }
        val speed = if (mc.thePlayer.hurtTime > 0) PlayerUtil.getBaseSpeed() * 1.4 else PlayerUtil.getBaseSpeed() * 0.875
        PlayerUtil.setMotion(event, speed.toDouble())
    }

}