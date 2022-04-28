package today.sleek.client.modules.impl.movement.speed.watchdog

import net.minecraft.potion.Potion
import today.sleek.client.modules.impl.movement.speed.SpeedMode
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.base.event.impl.MoveEvent
import today.sleek.client.utils.player.PlayerUtil

class LowHop : SpeedMode("Watchdog (LowHop)") {
    var ticks = 0
    override fun onEnable() {
        ticks = 0
    }
    override fun onUpdate(event: UpdateEvent) {
        if (event.isPre) {
            //if (MovementUtils.isMoving()) {
                if (mc.thePlayer.onGround) {
                    ticks = 0
                    mc.thePlayer.jump()
                    var speed = if (PlayerUtil.getSpeed() < 0.72f) PlayerUtil.getSpeed() * 1.03f else 0.72f
                    if (mc.thePlayer.onGround && mc.thePlayer.isPotionActive(Potion.moveSpeed)) speed *= (1f + 0.125f * (1 + mc.thePlayer.getActivePotionEffect(
                        Potion.moveSpeed).amplifier))
                    PlayerUtil.strafe(speed.toFloat())
                    return
                } else {
                    ticks++
                    if (ticks == 4) {
                        mc.thePlayer.motionY = -.09
                    }
                    if(ticks < 4){
                        mc.timer.timerSpeed = 1.3f
                    }else{
                        mc.timer.timerSpeed = 1.1f
                    }
                }
            //}
        }
    }

    override fun onMove(event: MoveEvent) {}
}

private operator fun Number.timesAssign(fl: Float) {

}
