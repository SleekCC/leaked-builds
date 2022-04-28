package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.player.EntityPlayer;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.math.MathUtil;
import today.sleek.client.utils.math.Stopwatch;

@ModuleData(
        name = "Auto Report",
        description = "Creates a lot of false reports on hypixel :troll:",
        category = ModuleCategory.PLAYER
)
public class AutoReport extends Module {

    private NumberValue delay = new NumberValue("Delay (ms)", this, 100, 0, 100000, 1);

    private Stopwatch timer = new Stopwatch();

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (timer.timeElapsed(delay.getValue().longValue())) {
            reportPlayer();
            timer.resetTime();
        }
    }

    private void reportPlayer() {
        EntityPlayer toReport = mc.theWorld.playerEntities.get(MathUtil.getRandomInRange(0, mc.theWorld.playerEntities.size() - 1));

        if (toReport.getName().contains("§"))
            return;

        mc.thePlayer.sendChatMessage("/report " + toReport.getName() + " bhop ka");
    }

}
