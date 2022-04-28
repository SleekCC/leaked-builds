package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang3.RandomStringUtils;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.NumberValue;
import today.sleek.base.value.value.StringValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.math.MathUtil;
import today.sleek.client.utils.math.Stopwatch;

@ModuleData(
        name = "Spammer",
        description = "Spams a message with a delay",
        category = ModuleCategory.PLAYER
)
public class Spammer extends Module {

    private final NumberValue delay = new NumberValue<>("Delay", this, 3000, 0, 600000, 0.1);
// parse for %r and insert random char
    private final StringValue message = new StringValue("Text", this, "sex");

    private Stopwatch stopwatch = new Stopwatch();

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (stopwatch.timeElapsed(delay.getValue().longValue() + MathUtil.getRandomInRange(1000, 3000))) {
            mc.thePlayer.sendChatMessage(message.getValue().replace("%r", RandomStringUtils.random(1, true, true)));
            stopwatch.resetTime();
        }
    }
}
