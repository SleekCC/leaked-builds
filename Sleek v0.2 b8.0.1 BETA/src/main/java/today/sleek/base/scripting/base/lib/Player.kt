package today.sleek.base.scripting.base.lib

import net.minecraft.client.Minecraft
import net.minecraft.network.Packet
import net.minecraft.util.MovementInput
import today.sleek.Sleek
import today.sleek.base.event.impl.MoveEvent
import today.sleek.client.modules.impl.combat.KillAura
import today.sleek.client.modules.impl.combat.TargetStrafe
import today.sleek.client.utils.rotations.AimUtil
import kotlin.math.cos
import kotlin.math.sin
import com.google.gson.Gson

object Player {

    private val mc = Minecraft.getMinecraft()

    fun getMovementInput(): MovementInput {
        return mc.thePlayer.movementInput
    }
    var posX: Double
        get() {
            return mc.thePlayer.posX
        }
        set(value) {
            mc.thePlayer.posX = value
        }
    var posY: Double
        get() {
            return mc.thePlayer.posY
        }
        set(value) {
            mc.thePlayer.posY = value
        }
    var posZ: Double
        get() {
            return mc.thePlayer.posZ
        }
        set(value) {
            mc.thePlayer.posZ = value
        }

    var onGround: Boolean
        get() {
            return mc.thePlayer.onGround
        }
        set(value) {
            mc.thePlayer.onGround = value
        }

    var motionY: Double
        get() {
            return mc.thePlayer.motionY
        }
        set(value) {
            mc.thePlayer.motionY = value
        }
    var motionZ: Double
        get() {
            return mc.thePlayer.motionZ
        }
        set(value) {
            mc.thePlayer.motionZ = value
        }
    var motionX: Double
        get() {
            return mc.thePlayer.motionX
        }
        set(value) {
            mc.thePlayer.motionX = value
        }


    fun sendPacket(packet: Packet<*>) {
        mc.thePlayer.sendQueue.addToSendQueue(packet)
    }

    fun setSpeed(moveSpeed: Double) {
        //EntityLivingBase entity = KillAura.currentTarget;
        val entity = KillAura.target
        val targetStrafeClass = Sleek.getInstance().moduleManager.getModuleByName("Target Strafe") as TargetStrafe
        val targetStrafe = targetStrafeClass.canStrafe()
        val movementInput = mc.thePlayer.movementInput
        var moveForward: Double =
            if (targetStrafe) if (mc.thePlayer.getDistanceToEntity(entity) <= targetStrafeClass.radius.value.toFloat()) 0.0 else 1.0 else movementInput.moveForward.toDouble()
        var moveStrafe = if (targetStrafe) TargetStrafe.dir else movementInput.moveStrafe.toDouble()
        var rotationYaw =
            if (targetStrafe) AimUtil.getRotationsRandom(entity).rotationYaw.toDouble() else mc.thePlayer.rotationYaw.toDouble()
        if (moveForward == 0.0 && moveStrafe == 0.0) {
            mc.thePlayer.motionZ = 0.0
            mc.thePlayer.motionX = mc.thePlayer.motionZ
        } else {
            if (moveStrafe > 0) {
                moveStrafe = 1.0
            } else if (moveStrafe < 0) {
                moveStrafe = -1.0
            }
            if (moveForward != 0.0) {
                if (moveStrafe > 0.0) {
                    rotationYaw += (if (moveForward > 0.0) -45 else 45).toDouble()
                } else if (moveStrafe < 0.0) {
                    rotationYaw += (if (moveForward > 0.0) 45 else -45).toDouble()
                }
                moveStrafe = 0.0
                if (moveForward > 0.0) {
                    moveForward = 1.0
                } else if (moveForward < 0.0) {
                    moveForward = -1.0
                }
            }
            val cos = Math.cos(Math.toRadians(rotationYaw + 90.0f))
            val sin = Math.sin(Math.toRadians(rotationYaw + 90.0f))
            mc.thePlayer.motionX = moveForward * moveSpeed * cos + moveStrafe * moveSpeed * sin
            mc.thePlayer.motionZ = moveForward * moveSpeed * sin - moveStrafe * moveSpeed * cos
        }
    }

    fun setSpeed(event: MoveEvent, moveSpeed: Double) {
        //EntityLivingBase entity = KillAura.currentTarget;
        val entity = KillAura.target
        val targetStrafeClass = Sleek.getInstance().moduleManager.getModuleByName("Target Strafe") as TargetStrafe
        val targetStrafe = targetStrafeClass.canStrafe()
        val movementInput = mc.thePlayer.movementInput
        var moveForward: Double =
            if (targetStrafe) if (mc.thePlayer.getDistanceToEntity(entity) <= targetStrafeClass.radius.value.toFloat()) 0.0 else 1.0 else movementInput.moveForward.toDouble()
        var moveStrafe = if (targetStrafe) TargetStrafe.dir else movementInput.moveStrafe.toDouble()
        var rotationYaw =
            if (targetStrafe) AimUtil.getRotationsRandom(entity).rotationYaw.toDouble() else mc.thePlayer.rotationYaw.toDouble()
        event.strafeSpeed = moveSpeed
        if (moveForward == 0.0 && moveStrafe == 0.0) {
            event.motionX = 0.0
            event.motionZ = 0.0
        } else {
            if (moveStrafe > 0) {
                moveStrafe = 1.0
            } else if (moveStrafe < 0) {
                moveStrafe = -1.0
            }
            if (moveForward != 0.0) {
                if (moveStrafe > 0.0) {
                    rotationYaw += if (moveForward > 0.0) -45.0 else 45.0
                } else if (moveStrafe < 0.0) {
                    rotationYaw += if (moveForward > 0.0) 45.0 else -45.0
                }
                moveStrafe = 0.0
                if (moveForward > 0.0) {
                    moveForward = 1.0
                } else if (moveForward < 0.0) {
                    moveForward = -1.0
                }
            }
            val cos = Math.cos(Math.toRadians(rotationYaw + 90.0f))
            val sin = Math.sin(Math.toRadians(rotationYaw + 90.0f))
            event.motionX = moveForward * moveSpeed * cos + moveStrafe * moveSpeed * sin
            event.motionZ = moveForward * moveSpeed * sin - moveStrafe * moveSpeed * cos
        }
    }

    fun getDirection(): Double {
        var rotationYaw = mc.thePlayer.rotationYaw
        if (mc.thePlayer.moveForward < 0f) rotationYaw += 180f
        var forward = 1f
        if (mc.thePlayer.moveForward < 0f) forward = -0.5f else if (mc.thePlayer.moveForward > 0f) forward =
            0.5f
        if (mc.thePlayer.moveStrafing > 0f) rotationYaw -= 90f * forward
        if (mc.thePlayer.moveStrafing < 0f) rotationYaw += 90f * forward
        return rotationYaw.toDouble()
    }

    fun setSpeedDumb(moveSpeed: Double) {
        if (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) {
            mc.thePlayer.motionX = -sin(getDirection()) * moveSpeed
            mc.thePlayer.motionX = cos(getDirection()) * moveSpeed
        }
    }

}