package today.sleek.client.modules.impl.visuals;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;

@ModuleData(
        name = "Time Changer",
        category = ModuleCategory.VISUALS,
        description = "Changes the world time"
)
public class TimeChanger extends Module {

    private ModeValue mode = new ModeValue("Mode", this, "Day", "Noon", "Night", "Mid Night");

    @Override
    public void onEnable() {
        switch (mode.getValue()) {
            case "Day":
                mc.theWorld.setWorldTime(1000);
                break;
            case "Noon":
                mc.theWorld.setWorldTime(13200);
                break;
            case "Night":
                mc.theWorld.setWorldTime(13000);
                break;
            case "Mid Night":
                mc.theWorld.setWorldTime(18000);
                break;
        }
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
    }
}
