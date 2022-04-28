package today.sleek.client.modules.impl.player

import com.google.common.eventbus.Subscribe
import net.minecraft.network.play.client.C03PacketPlayer
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import today.sleek.base.event.impl.UpdateEvent
import today.sleek.base.modules.ModuleCategory
import today.sleek.base.modules.ModuleData
import today.sleek.client.modules.impl.Module


/**
 * @author Kansio
 */
@ModuleData(
    name = "Zoot",
    description = "Removes bad effects or burn",
    category = ModuleCategory.PLAYER
)
class Zoot : Module() {

    @Subscribe
    fun onUpdate(event: UpdateEvent) {
        for (potion in Potion.potionTypes) {
            lateinit var effect: PotionEffect
            if (event.isPre && (potion != null) && (((mc.thePlayer.getActivePotionEffect(potion).also {
                    effect = it
                } != null) && potion.isBadEffect) || (mc.thePlayer.isBurning && !mc.thePlayer.isInWater && mc.thePlayer.onGround))) {
                var i = 0
                while (if (mc.thePlayer.isBurning) i < 20 else i < effect.duration / 20) {
                    mc.thePlayer.sendQueue.addToSendQueue(C03PacketPlayer(true))
                    i++
                }
            }
        }
    }

}