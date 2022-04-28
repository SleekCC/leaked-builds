package today.sleek.client.modules.impl.world

import com.google.common.eventbus.Subscribe
import net.minecraft.block.Block
import net.minecraft.block.BlockAir
import net.minecraft.client.settings.KeyBinding
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.modules.ModuleData
import today.sleek.client.modules.impl.Module
import net.minecraft.util.BlockPos

import net.minecraft.entity.player.EntityPlayer
import today.sleek.base.event.impl.UpdateEvent


/**
 * @author Kansio
 */

@ModuleData(
    name = "Eagle",
    description = "Automatically sneaks when you're on the edge",
    category = ModuleCategory.WORLD
)
class Eagle : Module() {

    override fun onEnable() {
        mc.thePlayer.isSneaking = false
    }

    override fun onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.keyCode, false)
    }

    @Subscribe
    fun onPlayerUpdate(event: UpdateEvent) {
        if (!event.isPre) {
            return
        }

        if (!mc.thePlayer.onGround) {
            return
        }

        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.keyCode, getUnderPlayer(mc.thePlayer) is BlockAir);
    }

    fun getBlock(pos: BlockPos?): Block? {
        return mc.theWorld.getBlockState(pos).block
    }


    fun getUnderPlayer(player: EntityPlayer): Block? {
        return getBlock(BlockPos(player.posX, player.posY - 1.0, player.posZ))
    }
}