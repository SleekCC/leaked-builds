package today.sleek.client.modules.impl.combat;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.player.EntityPlayer;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.client.modules.impl.Module;

@ModuleData(
        name = "Anti Bot",
        category = ModuleCategory.COMBAT,
        description = "Hides bots"
)
public class AntiBot extends Module {

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        for (EntityPlayer p : mc.theWorld.playerEntities) {
            if (p != null) {
                if (p.isInvisible() && p != mc.thePlayer) {
                    mc.theWorld.removeEntity(p);
                }
            }
        }
    }

}
