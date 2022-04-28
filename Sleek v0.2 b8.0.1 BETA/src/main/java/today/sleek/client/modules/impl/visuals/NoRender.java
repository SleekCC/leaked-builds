package today.sleek.client.modules.impl.visuals;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.client.modules.impl.Module;

@ModuleData(
        name = "NoRender",
        category = ModuleCategory.VISUALS,
        description = "Items Dropped Have Physics"
)
public class NoRender extends Module {
    private final BooleanValue noWeather  = new BooleanValue("No Weather", this, false);
    private final BooleanValue items  = new BooleanValue("Items", this, false);


    @Subscribe
    public void onUpdate() {
        if (this.items.getValue()) {
            NoRender.mc.theWorld.loadedEntityList.stream().filter(EntityItem.class::isInstance).map(EntityItem.class::cast).forEach(Entity::setDead);
        }
        if (this.noWeather.getValue() && NoRender.mc.theWorld.isRaining()) {
            NoRender.mc.theWorld.setRainStrength(0.0f);
        }
    }


}
