package today.sleek.client.modules.impl.visuals.hud.watermark;

import net.minecraft.client.renderer.GlStateManager;
import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.client.modules.impl.visuals.hud.WaterMarkMode;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.render.ColorUtils;

public class Intent extends WaterMarkMode {

    public Intent() {
        super("Intent");
    }

    @Override
    public void onRenderOverlay(RenderOverlayEvent event) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(4, 4, 0);
        GlStateManager.scale(2, 2, 1);
        GlStateManager.translate(-4, -4, 0);
        if (getHud().font.getValue()) {
            Fonts.SFRegular.drawString(ChatUtil.translateColorCodes(getHud().clientName.getValue()), 4, 4, ColorUtils.getColorFromHud(1).getRGB());
        } else {
            mc.fontRendererObj.drawString(ChatUtil.translateColorCodes(getHud().clientName.getValue()), 4, 4, ColorUtils.getColorFromHud(1).getRGB());
        }
        GlStateManager.popMatrix();
    }
}
