package today.sleek.client.modules.impl.movement

import com.google.common.eventbus.Subscribe
import net.minecraft.network.Packet
import net.minecraft.network.play.client.C03PacketPlayer
import today.sleek.base.event.impl.MoveEvent
import today.sleek.base.event.impl.PacketEvent
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.modules.ModuleData
import today.sleek.base.value.value.BooleanValue
import today.sleek.base.value.value.ModeValue
import today.sleek.base.value.value.NumberValue
import today.sleek.client.modules.impl.Module
import today.sleek.client.utils.math.Stopwatch
import today.sleek.client.utils.player.PlayerUtil
import today.sleek.client.utils.player.TimerUtil

@ModuleData(name = "Long Jump", category = ModuleCategory.MOVEMENT, description = "Jump further than normal")
class LongJump : Module() {
    //Verus Highjump Variables
    var launched = false
    var wasLaunched = false
    var jumped = false
    private val mode = ModeValue("Mode", this, "Verus", "Viper", "Vanilla", "Hypixel")

    //verus boost stuff
    private val vertical = NumberValue("Vertical Boost", this, 0.8, 0.05, 6.0, 0.1)
    private val boost = NumberValue("Speed", this, 1.45, 0.05, 10.0, 0.1)
    private val damageWaiterThing = Stopwatch()
    val spoofy = BooleanValue("Spoof Y", this, false)
    var speed = 0.8
    var ticks = 0;
    var hurt = false;
    override fun onEnable() {
        ticks = 0
        hurt = false
        launched = false
        wasLaunched = false
        jumped = false
        damageWaiterThing.resetTime()
        if ("Verus" == mode.value) {
            if (!mc.thePlayer.onGround) {
                toggle()
                return
            }
            TimerUtil.setTimer(0.3f)
            PlayerUtil.damageVerus()
        }
    }

    override fun onDisable() {
        TimerUtil.Reset()
        jumped = false
    }

    @Subscribe
    fun onUpdate(event: UpdateEvent?) {
        if (spoofy.value) {
            mc.thePlayer.posY = mc.thePlayer.prevPosY
        }
        when (mode.value) {
            "Verus" -> {
                if (mc.thePlayer.hurtTime > 1 && !launched) {
                    launched = true
                }
                if (launched) {
                    if (!jumped) {
                        mc.thePlayer.motionY = vertical.value
                        jumped = true
                    }
                    PlayerUtil.setMotion(boost.value.toFloat().toDouble())
                    launched = false
                    wasLaunched = true
                    toggle()
                }
            }
            "Vanilla" -> {
                if (mc.thePlayer.isMoving) {
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = vertical.value
                    }
                    PlayerUtil.setMotion(boost.value.toFloat().toDouble())
                }
            }

            "Hypixel" -> {
                if (event!!.isPre) {
                    ++ticks
                    if (mc.thePlayer.onGround && hurt) {
                        toggle()
                    }

                    if (ticks == 21) {
                        PlayerUtil.hypixelDamage();
                        mc.timer.timerSpeed = 0.334f;
                        mc.thePlayer.motionY = 0.67;
                        PlayerUtil.setMotion(0.2783 * 1.7);
                        hurt = true;
                    } else {
                        if (ticks == 22) {
                            mc.thePlayer.motionY += 0.05;
                            PlayerUtil.setMotion(0.2783 * 1.55);
                        }
                        if (ticks == 23) {
                            PlayerUtil.setMotion(0.2783 * 1.48);
                        }
                        mc.timer.timerSpeed = 1.0f;
                    }
                    if (hurt && mc.thePlayer.fallDistance > 0.1) {
                        mc.thePlayer.motionY += 0.015;
                    }
                }
            }
        }
    }

    @Subscribe
    fun onMove(event: MoveEvent?) {
        when (mode.value) {
            "Viper" -> {
                if (!mc.thePlayer.onGround) return
                TimerUtil.setTimer(0.3f)
                if (mc.thePlayer.isMoving) {
                    var i = 0
                    while (i < 17) {
                        PlayerUtil.TPGROUND(event, 0.32, 0.0)
                        ++i
                    }
                }
            }
            "Hypixel" -> {
                if (ticks < 21) {
                    event!!.isCancelled = true
                }
            }
        }
    }

    @Subscribe
    fun onPacket(event: PacketEvent) {
        if (mode.value == "Hypixel") {
            if (event.getPacket<Packet<*>>() is C03PacketPlayer) {
                if (ticks < 21) {
                    event.isCancelled = true
                }
            }
        }
    }

    override fun getSuffix(): String {
        return " " + mode.valueAsString
    }
}