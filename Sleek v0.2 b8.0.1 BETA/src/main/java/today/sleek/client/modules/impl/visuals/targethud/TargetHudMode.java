package today.sleek.client.modules.impl.visuals.targethud;

import net.minecraft.entity.EntityLivingBase;
import today.sleek.Sleek;
import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.client.modules.impl.visuals.HUD;
import today.sleek.client.modules.impl.visuals.TargetHUD;
import today.sleek.client.utils.Util;

/**
 * @author Kansio
 */

public abstract class TargetHudMode extends Util {

    private final String name;

    public TargetHudMode(String name) {
        this.name = name;
    }

    public void onRender(RenderOverlayEvent event, EntityLivingBase target, float x, float y) {}

    public void onEnable() {}
    public void onDisable() {}

    public String getName() {
        return name;
    }

    public TargetHUD getHud() {
        return Sleek.getInstance().getModuleManager().getModuleByClass(TargetHUD.class);
    }
}