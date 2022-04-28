package today.sleek.client.modules.impl.visuals

import com.google.common.eventbus.Subscribe
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.monster.EntityGolem
import net.minecraft.entity.monster.EntityMob
import net.minecraft.entity.monster.EntitySlime
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.entity.passive.EntityVillager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntityChest
import net.minecraft.util.AxisAlignedBB
import org.lwjgl.opengl.GL11
import today.sleek.base.event.impl.RenderOverlayEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.modules.ModuleData
import today.sleek.base.value.value.BooleanValue
import today.sleek.base.value.value.NumberValue
import today.sleek.client.modules.impl.Module
import today.sleek.client.utils.font.Fonts
import today.sleek.client.utils.math.MathUtil
import today.sleek.client.utils.render.RenderUtil
import java.awt.Color
import java.util.function.Consumer
import javax.vecmath.Vector3d
import javax.vecmath.Vector4d

@ModuleData(
    name = "ESP",
    category = ModuleCategory.VISUALS,
    description = "Shows player locations"
)
class ESP : Module() {

    private val players = BooleanValue("Players", this, true)
    private val animals = BooleanValue("Animals", this, true)
    private val mobs = BooleanValue("Mobs", this, false)
    private val invis = BooleanValue("Invisibles", this, false)
    private val passive = BooleanValue("Passive", this, true)
    private val chests = BooleanValue("Chests", this, true)
    private val items = BooleanValue("Items", this, true)

    private val filled = BooleanValue("Filled", this, false)

    private val r: NumberValue<*> = NumberValue("Red", this, 255, 1, 255, 1)
    private val g: NumberValue<*> = NumberValue("Green", this, 255, 1, 255, 1)
    private val b: NumberValue<*> = NumberValue("Blue", this, 255, 1, 255, 1)

    val collectedEntities: List<Entity> = ArrayList()

    @Subscribe
    fun onRenderOverlay(event: RenderOverlayEvent) {
        if (chests.value) {
            mc.theWorld.loadedTileEntityList.forEach {
                if (it is TileEntityChest) {
                    val chest = it as TileEntityChest
                    val posX = RenderUtil.interpolate(chest.pos.x.toDouble() + 1, chest.pos.x.toDouble() + 1, mc.timer.renderPartialTicks.toDouble())
                    val posY = RenderUtil.interpolate(chest.pos.y.toDouble() + 0.5, chest.pos.y.toDouble() + 0.5, mc.timer.renderPartialTicks.toDouble())
                    val posZ = RenderUtil.interpolate(chest.pos.z.toDouble() + 1, chest.pos.z.toDouble() + 1, mc.timer.renderPartialTicks.toDouble())

                    val width = 0.0
                    val height = 0.0

                    val bb = AxisAlignedBB(
                        posX - width,
                        posY,
                        posZ - width,
                        posX + width,
                        posY + height + 0.05,
                        posZ + width
                    )

                    val vectors = listOf(
                        Vector3d(bb.minX, bb.minY, bb.minZ),
                        Vector3d(bb.minX, bb.maxY, bb.minZ),
                        Vector3d(bb.maxX, bb.minY, bb.minZ),
                        Vector3d(bb.maxX, bb.maxY, bb.minZ),
                        Vector3d(bb.minX, bb.minY, bb.maxZ),
                        Vector3d(bb.minX, bb.maxY, bb.maxZ),
                        Vector3d(bb.maxX, bb.minY, bb.maxZ),
                        Vector3d(bb.maxX, bb.maxY, bb.maxZ)
                    )

                    mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0)
                    var pos: Vector4d? = null

                    for (vector in vectors) {
                        var nVector = vector
                        nVector = RenderUtil.project(
                            vector.x - mc.getRenderManager().viewerPosX,
                            vector.y - mc.getRenderManager().viewerPosY,
                            vector.z - mc.getRenderManager().viewerPosZ
                        )
                        if (nVector != null && nVector.z >= 0.0 && nVector.z < 1.0) {
                            if (pos == null) {
                                pos = Vector4d(nVector.x, nVector.y, nVector.z, 0.0)
                            }
                            pos.x = nVector.x.coerceAtMost(pos.x)
                            pos.y = nVector.y.coerceAtMost(pos.y)
                            pos.z = nVector.x.coerceAtLeast(pos.z)
                            pos.w = nVector.y.coerceAtLeast(pos.w)
                        }
                    }

                    mc.entityRenderer.setupOverlayRendering()

                    if (pos != null) {

                        //GL11.glPushMatrix()

                        val boxX = pos.x.toFloat()
                        val boxWidth = pos.z.toFloat() - boxX
                        val boxY = pos.y.toFloat() + 3
                        val boxHeight = pos.w.toFloat() - boxY


                        Fonts.Arial14.drawString("Chest ${MathUtil.round(mc.thePlayer.getDistance(chest.pos.x.toDouble(),
                            chest.pos.y.toDouble(), chest.pos.z.toDouble()
                        ), 1)}m", boxX, boxY, Color(255, 255, 0).rgb)

                        //GL11.glPopMatrix()
                    }
                }
            }
        }


        if (items.value) {
            mc.theWorld.loadedEntityList.forEach {
                if (it is EntityItem) {
                    val ent = it
                    val posX = RenderUtil.interpolate(ent.posX, ent.lastTickPosX, mc.timer.renderPartialTicks.toDouble())
                    val posY = RenderUtil.interpolate(ent.posY, ent.lastTickPosY, mc.timer.renderPartialTicks.toDouble())
                    val posZ = RenderUtil.interpolate(ent.posZ, ent.lastTickPosZ, mc.timer.renderPartialTicks.toDouble())

                    val width = 0.0
                    val height = 0.0

                    val bb = AxisAlignedBB(
                        posX - width,
                        posY,
                        posZ - width,
                        posX + width,
                        posY + height + 0.05,
                        posZ + width
                    )

                    val vectors = listOf(
                        Vector3d(bb.minX, bb.minY, bb.minZ),
                        Vector3d(bb.minX, bb.maxY, bb.minZ),
                        Vector3d(bb.maxX, bb.minY, bb.minZ),
                        Vector3d(bb.maxX, bb.maxY, bb.minZ),
                        Vector3d(bb.minX, bb.minY, bb.maxZ),
                        Vector3d(bb.minX, bb.maxY, bb.maxZ),
                        Vector3d(bb.maxX, bb.minY, bb.maxZ),
                        Vector3d(bb.maxX, bb.maxY, bb.maxZ)
                    )

                    mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0)
                    var vecPos: Vector4d? = null

                    for (vector in vectors) {
                        var nVector = vector
                        nVector = RenderUtil.project(
                            vector.x - mc.getRenderManager().viewerPosX,
                            vector.y - mc.getRenderManager().viewerPosY,
                            vector.z - mc.getRenderManager().viewerPosZ
                        )
                        if (nVector != null && nVector.z >= 0.0 && nVector.z < 1.0) {
                            if (vecPos == null) {
                                vecPos = Vector4d(nVector.x, nVector.y, nVector.z, 0.0)
                            }
                            vecPos.x = nVector.x.coerceAtMost(vecPos.x)
                            vecPos.y = nVector.y.coerceAtMost(vecPos.y)
                            vecPos.z = nVector.x.coerceAtLeast(vecPos.z)
                            vecPos.w = nVector.y.coerceAtLeast(vecPos.w)
                        }
                    }

                    mc.entityRenderer.setupOverlayRendering()

                    if (vecPos != null) {

                        //GL11.glPushMatrix()

                        val boxX = vecPos.x.toFloat()
                        val boxWidth = vecPos.z.toFloat() - boxX
                        val boxY = vecPos.y.toFloat() + 3
                        val boxHeight = vecPos.w.toFloat() - boxY


                        Fonts.Arial12.drawString("${ent.entityItem.displayName} ${MathUtil.round(mc.thePlayer.getDistance(ent.posX.toDouble(),
                            ent.posY, ent.posZ
                        ), 1)}m", boxX, boxY, Color(255, 255, 255).rgb)

                        //GL11.glPopMatrix()
                    }
                }
            }
        }

        mc.theWorld.loadedEntityList.forEach(Consumer { entity: Entity ->
            if (entity is EntityLivingBase) {
                if (isValid(entity) && RenderUtil.isInViewFrustrum(entity)) {
                    val posX =
                        RenderUtil.interpolate(entity.posX, entity.lastTickPosX, mc.timer.renderPartialTicks.toDouble())
                    val posY =
                        RenderUtil.interpolate(entity.posY, entity.lastTickPosY, mc.timer.renderPartialTicks.toDouble())
                    val posZ =
                        RenderUtil.interpolate(entity.posZ, entity.lastTickPosZ, mc.timer.renderPartialTicks.toDouble())

                    val width = entity.width / 1.5
                    val height = entity.height + if (entity.isSneaking()) -0.3 else 0.2

                    val bb = AxisAlignedBB(
                        posX - width,
                        posY,
                        posZ - width,
                        posX + width,
                        posY + height + 0.05,
                        posZ + width
                    )

                    val vectors = listOf(
                        Vector3d(bb.minX, bb.minY, bb.minZ),
                        Vector3d(bb.minX, bb.maxY, bb.minZ),
                        Vector3d(bb.maxX, bb.minY, bb.minZ),
                        Vector3d(bb.maxX, bb.maxY, bb.minZ),
                        Vector3d(bb.minX, bb.minY, bb.maxZ),
                        Vector3d(bb.minX, bb.maxY, bb.maxZ),
                        Vector3d(bb.maxX, bb.minY, bb.maxZ),
                        Vector3d(bb.maxX, bb.maxY, bb.maxZ)
                    )

                    mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0)
                    var position: Vector4d? = null

                    for (vector in vectors) {
                        var nVector = vector
                        nVector = RenderUtil.project(
                            vector.x - mc.getRenderManager().viewerPosX,
                            vector.y - mc.getRenderManager().viewerPosY,
                            vector.z - mc.getRenderManager().viewerPosZ
                        )
                        if (nVector != null && nVector.z >= 0.0 && nVector.z < 1.0) {
                            if (position == null) {
                                position = Vector4d(nVector.x, nVector.y, nVector.z, 0.0)
                            }
                            position.x = nVector.x.coerceAtMost(position.x)
                            position.y = nVector.y.coerceAtMost(position.y)
                            position.z = nVector.x.coerceAtLeast(position.z)
                            position.w = nVector.y.coerceAtLeast(position.w)
                        }
                    }

                    mc.entityRenderer.setupOverlayRendering()

                    if (position == null) {
                        return@Consumer
                    }

                    GL11.glPushMatrix()

                    val boxX = position.x.toFloat()
                    val boxWidth = position.z.toFloat() - boxX
                    val boxY = position.y.toFloat() + 3
                    val boxHeight = position.w.toFloat() - boxY

                    RenderUtil.drawBorderedRect(
                        (boxX - 1).toDouble(),
                        (boxY - 1).toDouble(),
                        (boxWidth + 2).toDouble(),
                        (boxHeight + 2).toDouble(),
                        1.0,
                        Color(r.value.toInt(), g.value.toInt(), b.value.toInt()).rgb,
                        if (filled.value) Color(0, 0, 0, 90).rgb else 0
                    )

                    GL11.glPopMatrix()

                }
            }
        })
    }

    private fun isValid(entity: EntityLivingBase): Boolean {
        return mc.thePlayer !== entity && entity.entityId != -1488 && isValidType(entity) && entity.isEntityAlive && (!entity.isInvisible || invis.value)
    }

    private fun isValidType(entity: EntityLivingBase): Boolean {
        return players.value && entity is EntityPlayer || mobs.value && (entity is EntityMob || entity is EntitySlime) || passive.value && (entity is EntityVillager || entity is EntityGolem) || animals.value && entity is EntityAnimal
    }
}