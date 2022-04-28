package today.sleek.client.modules.impl.visuals.targethud.impl;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import optifine.MathUtils;
import org.lwjgl.opengl.GL11;
import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.client.modules.impl.visuals.targethud.TargetHudMode;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.render.AnimationUtils;
import today.sleek.client.utils.render.ColorUtils;
import today.sleek.client.utils.render.RenderUtil;

import java.awt.*;

/**
 * @author Kansio
 */
public class TestMode extends TargetHudMode {

    public float animation = 0;

    public TestMode() {
        super("Test");
    }

    @Override
    public void onRender(RenderOverlayEvent event, EntityLivingBase target, float x, float y) {
        String clientTag = "";
        String playerName = target.getName();
        Color color;

        float width2 = Math.max(75, Fonts.Tahoma13.getStringWidth(clientTag + playerName) + 25);
        String healthStr2 = Math.round(target.getHealth() * 10) / 10d + "";
        GL11.glTranslatef(x, y, 0);

        RenderUtil.drawRect(0, 0, 45 + width2, 42, new Color(0, 0, 0, 90).getRGB());
        RenderUtil.drawRect(4, 4, 37 + width2, 34, new Color(0xff1E1E1C).getRGB());

        Fonts.Tahoma13.drawOutlinedString(clientTag + playerName, 8, 9, Color.WHITE.getRGB(), Color.BLACK.getRGB());

        boolean isNaN = Float.isNaN(target.getHealth());
        float health = isNaN ? 20 : target.getHealth();
        float maxHealth = isNaN ? 20 : target.getMaxHealth();
        float healthPercent = MathUtils.clampValue(health / maxHealth, 0, 1);

        int hue = (int) (healthPercent * 120);
        color = Color.getHSBColor(hue / 360f, 0.7f, 1f);

        //RenderUtil.drawRect(7, 14, 27.5f + width2, 21, RenderUtil.reAlpha(0xff1D1D1D, 1f));

        float barWidth = (34.5f + width2 - 2) - 37;
        float drawPercent = 34.5f + (barWidth / 100) * (healthPercent * 100);

        if (this.animation <= 0) {
            this.animation = drawPercent;
        }

        if (target.hurtTime <= 6) {
            this.animation = AnimationUtils.getAnimationState(this.animation, drawPercent, (float) Math.max(10, (Math.abs(this.animation - drawPercent) * 30) * 0.4));
        }

        RenderUtil.drawRect(7, 16, this.animation, 7, color.getRGB());
        RenderUtil.drawRect(7, 16, drawPercent, 7, color.getRGB());

        Fonts.Tahoma13.drawOutlinedString(healthStr2, 55.5f, 18, Color.WHITE.getRGB(), Color.BLACK.getRGB());

        Fonts.Tahoma13.drawOutlinedString("Distance: " + Math.round(target.getDistanceToEntity(mc.thePlayer) * 10) / 10d + " - Hurt Time: " + Math.round(target.hurtTime), 9, 30, Color.WHITE.getRGB(), Color.BLACK.getRGB());

        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();

        GL11.glPopMatrix();
    }
}
