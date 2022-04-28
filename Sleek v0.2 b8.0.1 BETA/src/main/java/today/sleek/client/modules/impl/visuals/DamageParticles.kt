package today.sleek.client.modules.impl.visuals

import com.google.common.eventbus.Subscribe
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.BlockPos
import org.lwjgl.opengl.GL11
import today.sleek.base.event.impl.Render3DEvent
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.modules.ModuleData
import today.sleek.client.modules.impl.Module
import today.sleek.client.modules.impl.combat.KillAura
import today.sleek.client.utils.chat.ChatUtil
import java.awt.Color
import java.text.DecimalFormat
import java.util.concurrent.ThreadLocalRandom


/**
 * @author Kansio
 */

@ModuleData(
    name = "Damage Particles",
    description = "Damage particles",
    category = ModuleCategory.VISUALS
)
class DamageParticles : Module() {

    private val hits = ArrayList<Hit>()
    private var lastHealth = 0f
    private var lastTarget: EntityLivingBase? = null

    @Subscribe
    fun onUpdate(event: UpdateEvent) {
        if (KillAura.target == null) {
            lastHealth = 20f
            lastTarget = null
            return
        }
        if (lastTarget == null || KillAura.target !== lastTarget) {
            lastTarget = KillAura.target
            lastHealth = KillAura.target!!.health
            return
        }
        if (KillAura.target!!.health != lastHealth) {
            if (KillAura.target!!.health < lastHealth) {
                hits.add(
                    Hit(
                        KillAura.target!!.position.add(
                            ThreadLocalRandom.current().nextDouble(-0.5, 0.5),
                            ThreadLocalRandom.current().nextDouble(1.0, 1.5),
                            ThreadLocalRandom.current().nextDouble(-0.5, 0.5)
                        ),
                        (lastHealth - KillAura.target!!.health).toDouble()
                    )
                )
            }
            lastHealth = KillAura.target!!.health
        }
    }

    @Subscribe
    fun on2D(event: Render3DEvent) {
        try {
            for (h in hits) {
                if (h.isFinished) {
                    hits.remove(h)
                } else {
                    h.onRender()
                }
            }
        } catch (ignored: Exception) {
        }
    }
}

internal class Hit(pos: BlockPos, healthVal: Double) {
    private var startTime = System.currentTimeMillis()
    private val pos: BlockPos
    private val healthVal: Double
    private val maxTime: Long = 1000

    fun onRender() {
        val x = pos.x + (pos.x - pos.x) * mc.timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosX
        val y = pos.y + (pos.y - pos.y) * mc.timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosY
        val z = pos.z + (pos.z - pos.z) * mc.timer.renderPartialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosZ
        val var10001 = if (mc.gameSettings.thirdPersonView == 2) -1.0f else 1.0f
        val size = 1.5
        GL11.glPushMatrix()
        GL11.glEnable(3042)
        GL11.glEnable(3042)
        GL11.glBlendFunc(770, 771)
        GL11.glEnable(2848)
        GL11.glDisable(3553)
        GL11.glDisable(2929)
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0)
        GL11.glTranslated(x, y, z)
        GL11.glNormal3f(0.0f, 1.0f, 0.0f)
        GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f)
        GL11.glRotatef(mc.getRenderManager().playerViewX, var10001, 0.0f, 0.0f)
        GL11.glScaled(-0.01666666753590107 * size, -0.01666666753590107 * size, 0.01666666753590107 * size)
        var sizePercentage = 1f
        val timeLeft = startTime + maxTime - System.currentTimeMillis()
        var yPercentage = 0f
        if (timeLeft < 75) {
            sizePercentage = Math.min(timeLeft.toFloat() / 75f, 1f)
            yPercentage = Math.min(timeLeft.toFloat() / 75f, 1f)
        } else {
            sizePercentage = Math.min((System.currentTimeMillis() - startTime).toFloat() / 300f, 1f)
            yPercentage = Math.min((System.currentTimeMillis() - startTime).toFloat() / 600f, 1f)
        }
        GlStateManager.scale(2 * sizePercentage, 2 * sizePercentage, 2 * sizePercentage)
        Gui.drawRect(-100, -100, 100, 100, Color(255, 0, 0, 0).rgb)
        var render = Color(0, 255, 0)
        if (healthVal < 3 && healthVal > 1) {
            render = Color(255, 255, 0)
        } else if (healthVal <= 1) {
            render = Color(255, 0, 0)
        }
        mc.fontRendererObj.drawStringWithShadow(
            "-" + DecimalFormat("#.#").format(healthVal),
            0f,
            -(yPercentage * 4),
            render.rgb
        )
        GL11.glDisable(3042)
        GL11.glEnable(3553)
        GL11.glDisable(2848)
        GL11.glDisable(3042)
        GL11.glEnable(2929)
        GlStateManager.color(1.0f, 1.0f, 1.0f)
        GlStateManager.popMatrix()
    }

    val isFinished: Boolean
        get() = System.currentTimeMillis() - startTime >= maxTime

    companion object {
        protected var mc = Minecraft.getMinecraft()
    }

    init {
        startTime = System.currentTimeMillis()
        this.pos = pos
        this.healthVal = healthVal
    }
}