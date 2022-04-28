package today.sleek.client.gui.nl.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import today.sleek.client.utils.render.ColorUtils;
import today.sleek.client.utils.render.RenderUtil;
import today.sleek.client.utils.render.RenderUtils;

import java.awt.*;

public class NeverLoseGui extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        mc.fontRendererObj.drawString(sr.getScaledWidth() + "", sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, -1);
        int x = sr.getScaledWidth() / 2 - 150, y = sr.getScaledHeight() / 2 - 150, width = 450, height = 500;
        RenderUtils.drawRoundedRect((double) x, y, width, height, 2, new Color(0xFF101010).getRGB());
        if (RenderUtils.hover(x, y, mouseX, mouseY, width, height)) {
            RenderUtil.drawOutlinedRoundedRect(x, y, width, height, 2, 2, ColorUtils.getIntGradientOffset(new Color(255, 60, 234), new Color(27, 179, 255), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + 1 * 9.95));
        }
        mc.fontRendererObj.drawString(sr.getScaledWidth() + "", sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
