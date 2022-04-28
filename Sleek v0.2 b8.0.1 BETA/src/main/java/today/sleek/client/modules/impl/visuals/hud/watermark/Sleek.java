package today.sleek.client.modules.impl.visuals.hud.watermark;

import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.client.modules.impl.visuals.HUD;
import today.sleek.client.modules.impl.visuals.hud.WaterMarkMode;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.render.ColorUtils;

import java.awt.*;

public class Sleek extends WaterMarkMode {

    public Sleek() {
        super("Sleek");
    }

    @Override
    public void onRenderOverlay(RenderOverlayEvent event) {
        HUD hud = getHud();
        int y = hud.arrayListY.getValue().intValue();
        Color color = ColorUtils.getColorFromHud(y);
        if (hud.font.getValue()) {
            Fonts.SFRegular.drawStringWithShadow(ChatUtil.translateColorCodes(getHud().clientName.getValue()), 6, 6, color.getRGB());
        } else {
            mc.fontRendererObj.drawStringWithShadow(ChatUtil.translateColorCodes(getHud().clientName.getValue()), 4, 4, color.getRGB());
        }
    }
}
