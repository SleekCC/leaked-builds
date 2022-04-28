package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import today.sleek.Sleek;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.modules.impl.world.Scaffold;
import today.sleek.client.utils.network.PacketUtil;

/**
 * @author Kansio
 */
@ModuleData(
        name = "Anti Desync",
        description = "Prevents your inventory from desyncronizing with the server",
        category = ModuleCategory.PLAYER
)
public class AntiDesync extends Module {

    private int currSlot = -1;

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C09PacketHeldItemChange) {
            currSlot = ((C09PacketHeldItemChange) event.getPacket()).getSlotId();
        }
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (Sleek.getInstance().getModuleManager().getModuleByName("Scaffold").isToggled())
            return;

        if (mc.thePlayer.inventory.currentItem != currSlot && currSlot != -1)
            PacketUtil.sendPacketNoEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
    }
}
