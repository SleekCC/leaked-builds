package today.sleek.client.modules.impl.visuals;

import com.google.common.eventbus.Subscribe;
import net.minecraft.potion.PotionEffect;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;

@ModuleData(
        name = "Brightness",
        category = ModuleCategory.VISUALS,
        description = "Changes the game brightness"
)
public class Brightness extends Module {

    private float oldGamma;

    private ModeValue mode = new ModeValue("Mode", this, "Gamma", "Potion");

    public void onEnable() {
        this.oldGamma = mc.gameSettings.gammaSetting;
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        switch (mode.getValueAsString()) {
            case "Gamma":
                mc.gameSettings.gammaSetting = 2000f;
                break;
            case "Potion":
                mc.thePlayer.addPotionEffect(new PotionEffect(16, (817 * 20), 30));
                break;
        }
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = this.oldGamma;
        mc.thePlayer.removePotionEffect(16);

    }

}
