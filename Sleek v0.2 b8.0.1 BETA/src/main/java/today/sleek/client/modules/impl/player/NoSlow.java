package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import today.sleek.base.event.impl.NoSlowEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;

@ModuleData(
        name = "No Slow",
        category = ModuleCategory.PLAYER,
        description = "Stops you from getting slowed down"
)
public class NoSlow extends Module {

    public ModeValue mode = new ModeValue("Mode", this, "Vanilla");
    public BooleanValue item = new BooleanValue("Item", this, true);
    public BooleanValue water = new BooleanValue("Water", this, false);
    public BooleanValue soulsand = new BooleanValue("SoulSand", this, false);

    @Subscribe
    public void onNoSlow(NoSlowEvent event) {
        switch (event.getType()) {
            case ITEM:
                event.setCancelled(item.getValue());
                break;
            case WATER:
                event.setCancelled(water.getValue());
                break;
            case SOULSAND:
                event.setCancelled(soulsand.getValue());
                break;
        }
    }

}
