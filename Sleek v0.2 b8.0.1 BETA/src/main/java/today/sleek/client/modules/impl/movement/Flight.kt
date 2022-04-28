package today.sleek.client.modules.impl.movement

import com.google.common.eventbus.Subscribe
import lombok.Getter
import net.minecraft.potion.Potion
import today.sleek.base.event.impl.BlockCollisionEvent
import today.sleek.base.event.impl.MoveEvent
import today.sleek.base.event.impl.PacketEvent
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.modules.ModuleData
import today.sleek.base.value.value.BooleanValue
import today.sleek.base.value.value.ModeValue
import today.sleek.base.value.value.NumberValue
import today.sleek.client.modules.impl.Module
import today.sleek.client.modules.impl.movement.flight.FlightMode
import today.sleek.client.utils.java.ReflectUtils
import today.sleek.client.utils.math.Stopwatch
import today.sleek.client.utils.player.TimerUtil

@Getter
@ModuleData(name = "Flight", category = ModuleCategory.MOVEMENT, description = "Allows you to fly")
class Flight : Module() {

    private val modes = ReflectUtils.getReflects("${this.javaClass.`package`.name}.flight", FlightMode::class.java).map { it.newInstance() as FlightMode }.sortedBy { it.name }
    val currentMode: FlightMode get() = modes.find { mode.equals(it.name) } ?: throw NullPointerException() // this should not happen
    private val mode = ModeValue(
        "Mode",
        this,
        *modes.map { it.name }.toTypedArray()
    )

    val speed = NumberValue("Speed", this, 1.0, 0.0, 10.0, 0.1)
    val antikick = BooleanValue("AntiKick", this, true, mode, "BridgerLand (TP)")
    val timer = NumberValue("Timer", this, 1.0, 1.0, 5.0, 0.1, mode, "Mush", "Test 2", "Hypixel", "Hypixel2", "Test", "Vulcan")
    val blink = BooleanValue("Blink", this, true, mode, "Mush")
    val viewbob = BooleanValue("View Bobbing", this, true)
    val spoofy = BooleanValue("Spoof Y", this, false)
    private val stopwatch = Stopwatch()
    var ticks = 0f
    var prevFOV = 0f
    override fun onEnable() {
        currentMode.onEnable()
    }

    override fun onDisable() {
        mc.thePlayer.motionX = 0.0
        mc.thePlayer.motionY = 0.0
        mc.thePlayer.motionZ = 0.0
        TimerUtil.Reset()
        currentMode.onDisable()
    }

    @Subscribe
    fun onUpdate(event: UpdateEvent?) {
        if (spoofy.value) {
            mc.thePlayer.posY = mc.thePlayer.prevPosY
        }
        if (viewbob.value && mc.thePlayer.isMoving) {
            mc.thePlayer.cameraYaw = 0.1f
        } else {
            mc.thePlayer.cameraYaw = 0f
        }
        currentMode.onUpdate(event)
        if (mc.thePlayer.ticksExisted < 5) {
            toggle();
        }
    }

    @Subscribe
    fun onMove(event: MoveEvent?) {
        currentMode.onMove(event)
    }

    @Subscribe
    fun onPacket(event: PacketEvent?) {
        currentMode.onPacket(event)
    }

    @Subscribe
    fun onCollide(event: BlockCollisionEvent?) {
        currentMode.onCollide(event)
    }

    private val baseMoveSpeed: Double
        private get() {
            var n = 0.2873
            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                n *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).amplifier + 1)
            }
            return n
        }

    override fun getSuffix(): String {
        return " " + mode.valueAsString
    }
}