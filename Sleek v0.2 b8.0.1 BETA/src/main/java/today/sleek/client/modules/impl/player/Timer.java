package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.player.TimerUtil;


@ModuleData(
        name = "Timer",
        category = ModuleCategory.PLAYER,
        description = "Changes your game speed"
)
public class Timer extends Module {

    private final BooleanValue tick = new BooleanValue("Tick Timer", this, false);
    private NumberValue<Float> speed = new NumberValue<>("Speed", this, 1.0F, 0.1F, 10.0F, 0.1F);
    private NumberValue<Integer> tickspeed = new NumberValue("Ticks", this,1.0, 1.0, 20.0, 1.0, tick);

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (tick.getValue()) {
            TimerUtil.setTimer(speed.getValue(), tickspeed.getValue());
        } else {
            TimerUtil.setTimer(speed.getValue());
        }


    }
    @Override
    public void onDisable() {
        TimerUtil.Reset();
    }
}
