package today.sleek.client.modules.impl.movement.flight.misc

import net.minecraft.block.Block
import net.minecraft.network.Packet
import net.minecraft.network.play.client.C03PacketPlayer
import net.minecraft.network.play.server.S08PacketPlayerPosLook
import net.minecraft.stats.StatList
import net.minecraft.util.BlockPos
import org.lwjgl.input.Keyboard
import today.sleek.base.event.impl.PacketEvent
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.client.modules.impl.movement.flight.FlightMode
import today.sleek.client.utils.chat.ChatUtil
import today.sleek.client.utils.player.PlayerUtil


/**
 * @author Kansio
 */
class Vulcan : FlightMode("Vulcan (Old)") {

    private var stage = Stage.WAITING

    private var x = 0.0
    private var z = 0.0

    private var startY = 0.0

    fun getBlock(pos: BlockPos?): Block? {
        return mc.theWorld.getBlockState(pos).block
    }

    override fun onEnable() {
        startY = mc.thePlayer.posY

        if (mc.thePlayer.posY % 1 != 0.0) {
            return
        }
        stage = Stage.WAITING

        ChatUtil.log("Press CONTROL to land")
    }

    override fun onUpdate(event: UpdateEvent) {
        when (stage) {
            Stage.WAITING -> {
                mc.thePlayer.motionY = 0.0

                if (mc.thePlayer.ticksExisted % 3 == 0) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, startY + 0.5, mc.thePlayer.posZ)
                } else {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, startY, mc.thePlayer.posZ)
                }
            }
            Stage.GO -> {
                mc.thePlayer.motionY = 0.0
                mc.timer.timerSpeed = flight.timer.value.toFloat()


                if (mc.thePlayer.ticksExisted % 3 == 0) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, startY + 0.5, mc.thePlayer.posZ)
                } else {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, startY, mc.thePlayer.posZ)
                }

                PlayerUtil.strafe()

                if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && mc.thePlayer.ticksExisted % 3 == 0 ) {
                    val y = mc.thePlayer.posY - (mc.thePlayer.posY % 1)

                    val block = getBlock(BlockPos(mc.thePlayer.posX, y - 1, mc.thePlayer.posZ)) ?: return

                    //check if you're standing on a full block
                    if (block.isFullBlock) {
                        stage = Stage.TOGGLE

                        mc.thePlayer.isAirBorne = true
                        mc.thePlayer.triggerAchievement(StatList.jumpStat)

                        mc.netHandler.addToSendQueue(
                            C03PacketPlayer.C04PacketPlayerPosition(
                                mc.thePlayer.posX,
                                y,
                                mc.thePlayer.posZ,
                                true
                            )
                        )

                        mc.netHandler.addToSendQueue(
                            C03PacketPlayer.C04PacketPlayerPosition(
                                mc.thePlayer.posX,
                                y + 0.49238484238,
                                mc.thePlayer.posZ,
                                true
                            )
                        )

                        mc.netHandler.addToSendQueue(
                            C03PacketPlayer.C04PacketPlayerPosition(
                                mc.thePlayer.posX,
                                y + 0.7323472437,
                                mc.thePlayer.posZ,
                                true
                            )
                        )
                    }
                }
            }
            Stage.TOGGLE -> {
                mc.timer.timerSpeed = 1f

                mc.thePlayer.motionX = 0.0
                mc.thePlayer.motionY = 0.0
                mc.thePlayer.motionZ = 0.0

                val y = mc.thePlayer.posY - (mc.thePlayer.posY % 1)
                mc.netHandler.addToSendQueue(
                    C03PacketPlayer.C04PacketPlayerPosition(
                        mc.thePlayer.posX,
                        y + 1,
                        mc.thePlayer.posZ,
                        true
                    )
                )
            }
        }


        mc.thePlayer.posY = startY
    }

    override fun onPacket(event: PacketEvent) {
        val packet = event.getPacket<Packet<*>>()

        if (packet is C03PacketPlayer) {
            packet.onGround = true
        } else if (packet is S08PacketPlayerPosLook) {
            if (stage == Stage.WAITING) {
                mc.thePlayer.setPosition(mc.thePlayer.posX, startY, mc.thePlayer.posZ)
                stage = Stage.GO
            } else if (stage == Stage.TOGGLE) {
                if (packet.x != x && packet.z != z) {
                    flight.toggle()
                    return
                }
            } else {
                x = packet.x
                z = packet.z
            }
            event.isCancelled = true
        }
    }

    enum class Stage {
        WAITING,
        GO,
        TOGGLE
    }

}