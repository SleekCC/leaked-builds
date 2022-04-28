package today.sleek.client.modules.impl.world

import com.google.common.eventbus.Subscribe
import net.minecraft.block.*
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemAnvilBlock
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraft.network.play.client.C09PacketHeldItemChange
import net.minecraft.network.play.client.C0APacketAnimation
import net.minecraft.potion.Potion
import net.minecraft.util.*
import today.sleek.Sleek
import today.sleek.base.event.impl.KeyboardEvent
import today.sleek.base.event.impl.RenderOverlayEvent
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.modules.ModuleData
import today.sleek.base.value.value.BooleanValue
import today.sleek.base.value.value.ModeValue
import today.sleek.base.value.value.NumberValue
import today.sleek.client.modules.impl.Module
import today.sleek.client.modules.impl.combat.KillAura
import today.sleek.client.modules.impl.visuals.HUD
import today.sleek.client.utils.chat.ChatUtil
import today.sleek.client.utils.font.Fonts
import today.sleek.client.utils.math.MathUtil
import today.sleek.client.utils.math.Stopwatch
import today.sleek.client.utils.player.PlayerUtil
import today.sleek.client.utils.render.ColorPalette
import today.sleek.client.utils.render.RenderUtils
import today.sleek.client.utils.rotations.RotationUtil
import java.awt.Color
import javax.vecmath.Vector2f

@ModuleData(name = "Scaffold", category = ModuleCategory.PLAYER, description = "Places blocks under you")
class Scaffold : Module() {
    private val delayTimer = Stopwatch()
    private val towerTimer = Stopwatch()
    private val sneakTimer = Stopwatch()
    var safewalk = BooleanValue("Safewalk", this, true)
    var keepY = BooleanValue("Keep Y", this, false)
    private val modeValue = ModeValue("Mode", this, "Verus", "New", "NCP", "Vulcan", "Dev")
    private val swing = BooleanValue("Swing", this, true)
    val sprint = BooleanValue("Sprint", this, false)
    private val tower = BooleanValue("Tower", this, true)
    private val info = BooleanValue("Show Info", this, true)
    private val delay = NumberValue("Delay", this, 0, 0, 9000, 1)
    private val expansion = NumberValue("Expansion", this, 4, 1, 6, 1)
    val auraToggle = BooleanValue("Disable Aura", this, true)
    private var autoJump = BooleanValue("Auto Jump", this, true, modeValue, "NCP")

    private var animation = 0
    private var blockEntry: BlockEntry? = null
    private var lastBlockEntry: BlockEntry? = null
    private var startSlot = 0
    private var lastSlot = 0
    var ything = 0
    private var didPlaceBlock = false

    override fun onEnable() {
        delayTimer.resetTime()
        val scaledResolution = RenderUtils.getResolution()
        animation = 0
        when (modeValue.valueAsString) {
            "Verus" -> {
                blockEntry = null
                didPlaceBlock = false
                startSlot = mc.thePlayer.inventory.currentItem
                if (slotWithBlock > -1) {
                    mc.thePlayer.inventory.currentItem = slotWithBlock
                }
                lastSlot = slotWithBlock
            }
            "Dev", "New", "NCP" -> {
                blockEntry = null
                startSlot = mc.thePlayer.inventory.currentItem
                if (slotWithBlock > -1 && mc.thePlayer.inventory.currentItem != slotWithBlock) {
                    mc.thePlayer.sendQueue.addToSendQueue(
                        C09PacketHeldItemChange(
                            slotWithBlock
                        )
                    )
                }
                lastSlot = slotWithBlock
            }
            "Expand" -> {
                startSlot = mc.thePlayer.inventory.currentItem
                if (slotWithBlock > -1) {
                    mc.thePlayer.inventory.currentItem = slotWithBlock
                }
                lastSlot = slotWithBlock
            }
        }
    }

    override fun onDisable() {
        when (modeValue.valueAsString) {
            "Verus" -> {}
            "NEW", "Dev", "NCP" -> {
                mc.thePlayer.sendQueue.addToSendQueue(
                    C09PacketHeldItemChange(
                        startSlot
                    )
                )
                lastBlockEntry = null
            }
            "Extend" -> {
                if (mc.thePlayer.inventory.currentItem != startSlot) {
                    mc.thePlayer.inventory.currentItem = startSlot
                }
            }
        }
    }

    @Subscribe
    fun onRender(event: RenderOverlayEvent?) {
        if (info.value && Sleek.getInstance().moduleManager.getModuleByName("HUD").isToggled) {
            val hud = Sleek.getInstance().moduleManager.getModuleByName("HUD") as HUD
            val scaledResolution = RenderUtils.getResolution()
            if (hud.font.value) {
                Fonts.Verdana.drawString(
                    blockCount.toString() + "",
                    (scaledResolution.scaledWidth / 2 - 5).toFloat(),
                    (scaledResolution.scaledHeight / 2 + 61 + animation).toFloat(),
                    -1
                )
            } else {
                mc.fontRendererObj.drawString(
                    blockCount.toString() + "",
                    scaledResolution.scaledWidth / 2 - 5,
                    scaledResolution.scaledHeight / 2 + 61 + animation,
                    -1
                )
            }
        }
    }

    @Subscribe
    fun onUpdate(event: UpdateEvent) {
        when (modeValue.valueAsString) {
            "Vulcan", "Verus", "New" -> {
                var vec2f: Vector2f? = null
                if (mc.thePlayer.isMoving) {
                    mc.thePlayer.forceSprinting(sprint.value)
                }
                if (blockEntry != null) {
                    vec2f = RotationUtil.getRotations(getPositionByFace(blockEntry!!.position, blockEntry!!.facing))
                    vec2f.setY(90f)
                }
                val blockEntry = find(Vec3(0.0, 0.0, 0.0)) ?: return
                this.blockEntry = blockEntry
                if (vec2f != null) {
                    event.rotationYaw = vec2f.x
                    event.rotationPitch = vec2f.y
                }
                val slot = slotWithBlock
                if (blockCount < 1 && didPlaceBlock) {
                    mc.thePlayer.motionY -= 20.0
                    didPlaceBlock = false
                    return
                }
                if (modeValue.valueAsString.equals("vulcan", ignoreCase = true)) {
                    if (sneakTimer.timeElapsed(1000)) {
                        mc.gameSettings.keyBindSneak.pressed = true
                        if (sneakTimer.timeElapsed(1100)) {
                            mc.gameSettings.keyBindSneak.pressed = false
                            sneakTimer.resetTime()
                        }
                    }
                }

                if (modeValue.value.equals("new", ignoreCase = true)) {
                    if (event.isPre && blockEntry != null) {
                        val slot = slotWithBlock
                        if (slot > -1) {
                            if (lastSlot != slot) {
                                mc.thePlayer.sendQueue.addToSendQueue(C09PacketHeldItemChange(slot))
                                lastSlot = slot
                            }
                            if (placeBlock(blockEntry.position.add(0, 0, 0), blockEntry.facing, slot, swing.value)) {
                                if (tower.value && !mc.thePlayer.isPotionActive(Potion.jump) && !mc.thePlayer.isMoving && mc.gameSettings.keyBindJump.pressed) {
                                    mc.thePlayer.motionY = .42
                                    mc.thePlayer.motionX = 0.0
                                    mc.thePlayer.motionZ = mc.thePlayer.motionX
                                    if (towerTimer.timeElapsed(1500L)) {
                                        towerTimer.resetTime()
                                        mc.thePlayer.motionY = -.28
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (this.blockEntry != null && vec2f != null && slot > -1 && event.isPre) {
                        if (lastSlot != slot) {
                            mc.thePlayer.inventory.currentItem = slot
                            lastSlot = slot
                        }
                        placeBlockVerus(
                            this.blockEntry!!.position.add(0, 0, 0),
                            this.blockEntry!!.facing,
                            slot,
                            swing.value
                        )
                        if (tower.value && !mc.thePlayer.isPotionActive(Potion.jump) && !mc.thePlayer.isMoving && mc.gameSettings.keyBindJump.pressed) {
                            mc.thePlayer.motionY = .52
                            mc.thePlayer.motionX = 0.0
                            mc.thePlayer.motionZ = mc.thePlayer.motionX
                            if (towerTimer.timeElapsed(1500L)) {
                                towerTimer.resetTime()
                                mc.thePlayer.motionY = -1.28
                            }
                        }
                    }
                }
            }
            "Extend" -> {
                val vec2f: Vector2f? = null
                if (mc.thePlayer.isMoving) {
                    mc.thePlayer.forceSprinting(sprint.value)
                }
                val slot = slotWithBlock
                if (slot > -1 && event.isPre) {
                    if (lastSlot != slot) {
                        mc.thePlayer.inventory.currentItem = slot
                        lastSlot = slot
                    }
                    val expand = expansion.value.toInt() * 5
                    var i = 0
                    while (i < expand) {
                        val blockEntry = findExpand(Vec3(mc.thePlayer.motionX * i, 0.0, mc.thePlayer.motionZ * i), i)
                        if (blockEntry != null) {
                        }
                        i++
                    }
                }
            } "NCP" -> {
                if (lastBlockEntry != null && blockEntry != null) {
                    val rotation = RotationUtil.getRotations(getPositionByFace(lastBlockEntry!!.position, lastBlockEntry!!.facing))

                    val blockEntry = find(Vec3(0.0, 0.0, 0.0)) ?: return
                    this.blockEntry = blockEntry
                }

                if (event.isPre && autoJump.value && mc.thePlayer.onGround) {
                    mc.thePlayer.jump()
                }

                var blockEntry: BlockEntry?

                if (mc.thePlayer.onGround || mc.thePlayer.fallDistance > 0.35) {
                    blockEntry = find(Vec3(0.0, (if (mc.thePlayer.onGround) 0.0 else 0.0), 0.0))
                } else {
                    return
                }
                lastBlockEntry = blockEntry

                if (mc.thePlayer.isMoving) {
                    mc.thePlayer.forceSprinting(sprint.value)
                }

                val rotation = RotationUtil.getRotations(getPositionByFace(lastBlockEntry!!.position, lastBlockEntry!!.facing))

                event.rotationYaw = rotation.x
                event.rotationPitch = rotation.y

                if (event.isPre && blockEntry != null) {
                    val slot = slotWithBlock
                    if (slot > -1) {
                        if (lastSlot != slot) {
                            mc.thePlayer.sendQueue.addToSendQueue(C09PacketHeldItemChange(slot))
                            lastSlot = slot
                        }
                        if (placeBlock(blockEntry.position.add(0, 0, 0), blockEntry.facing, slot, swing.value)) {
                            if (tower.value && !mc.thePlayer.isPotionActive(Potion.jump) && !mc.thePlayer.isMoving && mc.gameSettings.keyBindJump.pressed) {
                                mc.thePlayer.motionY = .42
                                mc.thePlayer.motionX = 0.0
                                mc.thePlayer.motionZ = mc.thePlayer.motionX
                                if (towerTimer.timeElapsed(1500L)) {
                                    towerTimer.resetTime()
                                    mc.thePlayer.motionY = -.28
                                }
                            }
                        }
                    }
                }
            }
            "Dev" -> {
                var vec2f: Vector2f? = null
                mc.thePlayer.forceSprinting(false)
                PlayerUtil.setMotion(0.005)
                if (blockEntry != null) {
                    vec2f = RotationUtil.getRotations(getPositionByFace(blockEntry!!.position, blockEntry!!.facing))
                    vec2f.setY(90f)
                }
                val blockEntry = find(Vec3(0.0, 0.0, 0.0)) ?: return
                this.blockEntry = blockEntry
                if (vec2f != null) {
                    event.rotationYaw = vec2f.x
                    event.rotationPitch = vec2f.y
                }
                val slot = slotWithBlock
                if (blockCount < 1 && didPlaceBlock) {
                    mc.thePlayer.motionY -= 20.0
                    didPlaceBlock = false
                    return
                }
                if (this.blockEntry != null && vec2f != null && slot > -1 && event.isPre) {
                    if (lastSlot != slot) {
                        mc.thePlayer.inventory.currentItem = slot
                        lastSlot = slot
                    }
                    placeBlockVerus(
                        this.blockEntry!!.position.add(0, 0, 0),
                        this.blockEntry!!.facing,
                        slot,
                        swing.value
                    )
                }
            }
        }
    }

    @Subscribe
    fun onKeyboard(event: KeyboardEvent) {
        if (!sprint.value && event.keyCode == 29) {
            event.isCancelled = true
        }
    }

    fun findExpand(offset3: Vec3?, expand: Int): BlockEntry? {
        val invert = arrayOf(
            EnumFacing.UP,
            EnumFacing.DOWN,
            EnumFacing.SOUTH,
            EnumFacing.NORTH,
            EnumFacing.EAST,
            EnumFacing.WEST
        )
        val position = BlockPos(mc.thePlayer.positionVector.add(offset3)).offset(EnumFacing.DOWN)
        if (mc.theWorld.getBlockState(position).block !is BlockAir) return null
        for (facing in EnumFacing.values()) {
            val offset = position.offset(facing)
            if (mc.theWorld.getBlockState(offset).block is BlockAir || !rayTrace(
                    mc.thePlayer.getLook(0.0f),
                    getPositionByFace(offset, invert[facing.ordinal])
                )
            ) continue
            return BlockEntry(offset, invert[facing.ordinal])
        }
        for (i in 0 until expand) {
            val offsets = arrayOf(BlockPos(-i, 0, 0), BlockPos(i, 0, 0), BlockPos(0, 0, -i), BlockPos(0, 0, i))
            for (offset in offsets) {
                val offsetPos = position.add(offset.x, 0, offset.z)
                if (mc.theWorld.getBlockState(offsetPos).block !is BlockAir) continue
                for (facing in EnumFacing.values()) {
                    val offset2 = offsetPos.offset(facing)
                    if (mc.theWorld.getBlockState(offset2).block is BlockAir || !rayTrace(
                            mc.thePlayer.getLook(0.0f),
                            getPositionByFace(offset, invert[facing.ordinal])
                        )
                    ) continue
                    return BlockEntry(offset2, invert[facing.ordinal])
                }
            }
        }
        return null
    }

    private fun placeBlock(blockPos: BlockPos, facing: EnumFacing, slot: Int, swing: Boolean): Boolean {
        if (mc.theWorld.getBlock(blockPos.x, blockPos.y + 1, blockPos.z).blockState.block !is BlockAir) {
            return false
        }

        if (delayTimer.timeElapsed(delay.value.toInt().toLong())) {
            delayTimer.resetTime()
            val offset = blockPos.offset(facing)
            val invert = arrayOf(
                EnumFacing.UP,
                EnumFacing.DOWN,
                EnumFacing.SOUTH,
                EnumFacing.NORTH,
                EnumFacing.EAST,
                EnumFacing.WEST
            )
            if (rayTrace(mc.thePlayer.getLook(0.0f), getPositionByFace(offset, invert[facing.ordinal]))) {
                val stack = mc.thePlayer.inventory.getStackInSlot(slot)
                val hitVec = Vec3(blockPos)
                    .add(Vec3(0.5, 0.5, 0.5)).add(Vec3(facing.directionVec).scale(0.5f))
                if (mc.playerController.onPlayerRightClick(
                        mc.thePlayer,
                        mc.theWorld,
                        stack,
                        blockPos,
                        facing,
                        Vec3(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord)
                    )
                ) {
                    if (swing) {
                        mc.thePlayer.swingItem()
                    } else {
                        mc.thePlayer.sendQueue.addToSendQueue(C0APacketAnimation())
                    }
                    return true
                }
            }
        }
        return false
    }

    private fun isPlaceable(itemStack: ItemStack?): Boolean {
        if (itemStack != null && itemStack.item is ItemBlock) {
            val block = (itemStack.item as ItemBlock).block
            return block !is BlockNote && block !is BlockFurnace && !block.localizedName.equals(
                "Crafting Table",
                ignoreCase = true
            ) && block !is BlockWeb && block !is BlockFence && block !is BlockFenceGate && block !is BlockSlab && block !is BlockStairs
        }
        return true
    }

    val slotWithBlock: Int
        get() {
            for (i in 0..8) {
                val itemStack = mc.thePlayer.inventory.mainInventory[i]
                if (!isPlaceable(itemStack)) continue
                if (itemStack != null && (itemStack.item is ItemAnvilBlock || itemStack.item is ItemBlock && (itemStack.item as ItemBlock).block is BlockSand)) {
                    continue
                }
                if (itemStack == null || itemStack.item !is ItemBlock || (itemStack.item as ItemBlock).block.maxY - (itemStack.item as ItemBlock).block.minY != 1.0 && itemStack.item !is ItemAnvilBlock) {
                    continue
                }
                return i
            }
            return -1
        }

    val blockCount: Int
        get() {
            var blockCount = 0
            for (i in 0..8) {
                if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i) == null) {
                    continue
                }
                if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i) != null) {
                    val `is` = Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i)
                    if (`is`.item is ItemBlock) {
                        blockCount += `is`.stackSize
                    }
                }
            }
            return blockCount
        }

    fun placeBlockVerus(blockPos: BlockPos, facing: EnumFacing, slot: Int, swing: Boolean): Boolean {
        if (delayTimer.timeElapsed(delay.value.toInt().toLong())) {
            delayTimer.resetTime()
            val offset = blockPos.offset(facing)
            val invert = arrayOf(
                EnumFacing.UP,
                EnumFacing.DOWN,
                EnumFacing.SOUTH,
                EnumFacing.NORTH,
                EnumFacing.EAST,
                EnumFacing.WEST
            )
            if (rayTrace(mc.thePlayer.getLook(0.0f), getPositionByFace(offset, invert[facing.ordinal]))) {
                val stack = mc.thePlayer.inventory.getStackInSlot(slot)
                val f = MathUtil.getRandomInRange(.3f, .5f)
                val hitVec = Vec3(blockPos)
                    .add(f, f, f).add(Vec3(facing.directionVec).scale(f))
                if (mc.playerController.onPlayerRightClick(
                        mc.thePlayer,
                        mc.theWorld,
                        stack,
                        blockPos,
                        facing,
                        Vec3(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord)
                    )
                ) {
                    if (swing) {
                        mc.thePlayer.swingItem()
                    } else {
                        mc.thePlayer.sendQueue.addToSendQueue(C0APacketAnimation())
                    }
                    return true
                }
            }
        }
        return false
    }

    override fun getSuffix(): String {
        return " " + modeValue.valueAsString
    }

    fun getPositionByFace(position: BlockPos, facing: EnumFacing): Vec3 {
        val offset = Vec3(
            facing.directionVec.x.toDouble() / 2.0,
            facing.directionVec.y.toDouble() / 2.0,
            facing.directionVec.z.toDouble() / 2.0
        )
        val point = Vec3(position.x.toDouble() + 0.5, position.y.toDouble() + 0.75, position.z.toDouble() + 0.5)
        return point.add(offset)
    }

    fun find(offset3: Vec3?): BlockEntry? {
        val invert = arrayOf(
            EnumFacing.UP,
            EnumFacing.DOWN,
            EnumFacing.SOUTH,
            EnumFacing.NORTH,
            EnumFacing.EAST,
            EnumFacing.WEST
        )
        val position = BlockPos(mc.thePlayer.positionVector.add(offset3)).offset(EnumFacing.DOWN)
        for (facing in EnumFacing.values()) {
            val offset = position.offset(facing)
            if (mc.theWorld.getBlockState(offset).block is BlockAir || !rayTrace(

                    mc.thePlayer.getLook(0.0f),
                    getPositionByFace(offset, invert[facing.ordinal])
                )
            ) continue
            return BlockEntry(offset, invert[facing.ordinal])
        }
        val offsets = arrayOf(BlockPos(-1, 0, 0), BlockPos(1, 0, 0), BlockPos(0, 0, -1), BlockPos(0, 0, 1))
        for (offset in offsets) {
            val offsetPos = position.add(offset.x, 0, offset.z)
            if (mc.theWorld.getBlockState(offsetPos).block !is BlockAir) continue
            for (facing in EnumFacing.values()) {
                val offset2 = offsetPos.offset(facing)
                if (mc.theWorld.getBlockState(offset2).block is BlockAir || !rayTrace(
                        mc.thePlayer.getLook(0.0f),
                        getPositionByFace(offset, invert[facing.ordinal])
                    )
                ) continue
                return BlockEntry(offset2, invert[facing.ordinal])
            }
        }
        return null
    }

    private fun rayTrace(origin: Vec3, position: Vec3): Boolean {
        val difference = position.subtract(origin)
        val steps = 10
        val x = difference.xCoord / steps.toDouble()
        val y = difference.yCoord / steps.toDouble()
        val z = difference.zCoord / steps.toDouble()
        var point = origin
        for (i in 0 until steps) {
            val blockPosition = BlockPos(point.addVector(x, y, z).also { point = it })
            val blockState = mc.theWorld.getBlockState(blockPosition)
            if (blockState.block is BlockLiquid || blockState.block is BlockAir) continue
            var boundingBox = blockState.block.getCollisionBoundingBox(mc.theWorld, blockPosition, blockState)
            if (boundingBox == null) {
                boundingBox = AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
            }
            if (!boundingBox.offset(blockPosition).isVecInside(point)) continue
            return false
        }
        return true
    }

    inner class BlockEntry internal constructor(val position: BlockPos, val facing: EnumFacing)
}