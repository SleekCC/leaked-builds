package today.sleek.client.modules.impl.movement.flight.misc

import today.sleek.base.event.impl.MoveEvent
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.client.modules.impl.movement.flight.FlightMode
import today.sleek.client.utils.chat.ChatUtil
import today.sleek.client.utils.player.PlayerUtil
import kotlin.math.sqrt

// speed -= speed / 152
class OldNCP : FlightMode("test") {
    var thing = 0
    var speed = 2.5
    var boosted = false
    var lastDist = 0.0;

    override fun onUpdate(event: UpdateEvent?) {
        if (!event!!.isPre) {
            lastDist = sqrt((mc.thePlayer.posX - mc.thePlayer.prevPosX) * (mc.thePlayer.posX - mc.thePlayer.prevPosX) + (mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (mc.thePlayer.posZ - mc.thePlayer.prevPosZ))
        }
        mc.thePlayer.motionY = 0.0.also {
            if (thing > 1) {
                mc.thePlayer.setPosition(
                    mc.thePlayer.posX, mc.thePlayer.posY - 8E-6, mc.thePlayer.posZ
                )
            }
        }
        if (mc.thePlayer.onGround && mc.thePlayer.isMoving) {
            if (!boosted) {
                PlayerUtil.damagePlayer()
                boosted = true
            }
        }
        if (mc.thePlayer.isMoving && !mc.thePlayer.isCollidedHorizontally) {
            if (boosted || thing > 10) {
//                speed -= speed / 152
                mc.timer.timerSpeed = 1.8f

            }
            if (thing < 16) {
                mc.timer.timerSpeed = 1.34f

            }
            if (thing > 20) {
                mc.timer.timerSpeed = 1.0f;
            }
            if (!boosted) {
                speed = 0.0
            }
        } else {
            speed = 0.0
        }
    }

    override fun onMove(event: MoveEvent?) {


        when (thing) {
            1 -> {

                if (mc.thePlayer.hurtResistantTime == 19) {
                    thing = 2
                    event!!.motionY = 0.42.also { mc.thePlayer.motionY = it }

                    speed = 1.8 * PlayerUtil.getBaseSpeed()
                }

            }
            2 -> {
                ++thing
                speed *= flight.speed.value + 0.5
            }
            3 -> {
                ++thing
                val diff = 0.1 * (lastDist - PlayerUtil.getBaseSpeed())
                speed = lastDist - diff
            }
            else -> {
                ++thing
                if (mc.thePlayer.isCollidedVertically || mc.theWorld.getCollidingBoundingBoxes(
                        mc.thePlayer, mc.thePlayer.entityBoundingBox.offset(0.0, mc.thePlayer.motionY, 0.0)
                    ).size > 0
                )
                    thing = 1;

                speed -= speed / 152
            }
        }
        PlayerUtil.setMotion(event, if (mc.thePlayer.hurtResistantTime != 19 && thing == 1) 0.011 else PlayerUtil.getBaseSpeed().toDouble().coerceAtLeast(speed))


    }

    override fun onEnable() {
        boosted = false
        lastDist = 0.0
        thing = 0
    }
}