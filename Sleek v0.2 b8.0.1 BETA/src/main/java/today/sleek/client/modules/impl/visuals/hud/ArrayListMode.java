package today.sleek.client.modules.impl.visuals.hud;

import today.sleek.Sleek;
import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.client.modules.impl.visuals.HUD;
import today.sleek.client.utils.Util;

public abstract class ArrayListMode extends Util {

    private final String name;

    public ArrayListMode(String name) {
        this.name = name;
    }

    public void onRenderOverlay(RenderOverlayEvent event) {}
    public void onEnable() {}
    public void onDisable() {}

    public String getName() {
        return name;
    }

    public HUD getHud() {
        return (HUD) Sleek.getInstance().getModuleManager().getModuleByName("Hud");
    }


}
