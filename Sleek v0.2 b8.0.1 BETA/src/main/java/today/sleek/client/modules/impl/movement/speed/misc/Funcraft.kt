package today.sleek.client.modules.impl.movement.speed.misc

import today.sleek.base.event.impl.MoveEvent
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.client.modules.impl.movement.speed.SpeedMode
import today.sleek.client.utils.player.PlayerUtil

class Funcraft : SpeedMode("Funcraft") {

    private var speed = 0.3

    override fun onUpdate(event: UpdateEvent) {
        super.onUpdate(event)
    }

    override fun onMove(event: MoveEvent) {
        speed = PlayerUtil.getVerusBaseSpeed();
        if (mc.thePlayer.isMovingOnGround) {
            mc.timer.timerSpeed = 1.1f
            event.motionY = 0.4025; also { mc.thePlayer.motionY = 0.4025 }
        }
        if (!mc.thePlayer.onGround) {
            mc.timer.timerSpeed = 1.2f
            //speed -= speed / 152
            mc.thePlayer.motionX *= 0.9
            mc.thePlayer.motionZ *= 0.9
            if (mc.thePlayer.moveStrafing != 0.0F) {
                mc.thePlayer.motionX *= 0.22
                mc.thePlayer.motionZ *= 0.22
            }
        }
        PlayerUtil.setMotion(event, speed)
    }
}