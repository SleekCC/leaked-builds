package today.sleek.client.modules.impl.visuals

import com.google.common.eventbus.Subscribe
import org.lwjgl.opengl.Display
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.modules.ModuleData
import today.sleek.base.value.value.BooleanValue
import today.sleek.client.modules.impl.Module
import today.sleek.client.utils.chat.ChatUtil


/**
 * @author Kansio
 */
@ModuleData(
    name = "Perspective",
    description = "The perspective mod, you know what it is....",
    category = ModuleCategory.VISUALS
)
class Perspective : Module() {

    private var perspectiveToggled = false

    private var cameraYaw = 0F
    private var cameraPitch = 0F

    private var previousPerspective = 0 //previous f5 state

    private var called = false
    private var hasPressed = false

    private var onPress = BooleanValue("Toggle", this, true)

    @Subscribe
    fun onUpdate(event: UpdateEvent) {
        if (onPress.value) {
            if (mc.gameSettings.togglePerspective.isPressed) {
                if (!called) {
                    perspectiveToggled = !perspectiveToggled

                    cameraYaw = mc.thePlayer.rotationYaw
                    cameraPitch = mc.thePlayer.rotationPitch

                    if (perspectiveToggled) {
                        previousPerspective = mc.gameSettings.thirdPersonView
                        mc.gameSettings.thirdPersonView = 1
                    } else {
                        mc.gameSettings.thirdPersonView = 0
                    }

                    called = true;
                }
            } else {
                called = false
            }
        } else {
            if (mc.gameSettings.togglePerspective.pressed) {
                hasPressed = true
                if (!called) {
                    perspectiveToggled = !perspectiveToggled

                    cameraYaw = mc.thePlayer.rotationYaw
                    cameraPitch = mc.thePlayer.rotationPitch

                    if (perspectiveToggled) {
                        previousPerspective = mc.gameSettings.thirdPersonView
                        mc.gameSettings.thirdPersonView = 1
                    } else {
                        mc.gameSettings.thirdPersonView = 0
                    }

                    called = true
                }
                return
            } else if (hasPressed) {
                called = false
                hasPressed = false
                perspectiveToggled = false
                mc.gameSettings.thirdPersonView = 0
            }
        }
    }

    fun getCameraYaw(): Float {
        return if (perspectiveToggled) cameraYaw else mc.thePlayer.rotationYaw
    }

    fun getCameraPitch(): Float {
        return if (perspectiveToggled) cameraPitch else mc.thePlayer.rotationPitch
    }

    fun overrideMouse(): Boolean {
        if (mc.inGameHasFocus && Display.isActive()) {

            if (!perspectiveToggled) {
                return true
            }

            mc.mouseHelper.mouseXYChange()
            val f1 = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f
            val f2 = f1 * f1 * f1 * 8.0f
            val f3 = mc.mouseHelper.deltaX.toFloat() * f2
            val f4 = mc.mouseHelper.deltaY.toFloat() * f2

            cameraYaw += f3 * 0.15f
            cameraPitch += f4 * 0.15f

            if (cameraPitch > 90) cameraPitch = 90f
            if (cameraPitch < -90) cameraPitch = -90f

        }

        return false
    }

}