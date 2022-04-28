package today.sleek.client.gui.legacy.clickgui.frame.components.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import today.sleek.Sleek;
import today.sleek.base.value.Value;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.client.gui.legacy.clickgui.frame.Values;
import today.sleek.client.gui.legacy.clickgui.frame.components.Component;
import today.sleek.client.gui.legacy.clickgui.frame.components.FrameModule;
import today.sleek.client.gui.legacy.clickgui.utils.render.animation.easings.Animate;
import today.sleek.client.gui.legacy.clickgui.utils.render.animation.easings.Easing;
import today.sleek.client.modules.impl.visuals.ClickGUI;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.font.MCFontRenderer;
import today.sleek.client.utils.render.RenderUtils;

import java.awt.*;

public class BoolSetting extends Component implements Values {
    private final Animate animation;

    public BoolSetting(int x, int y, FrameModule owner, Value setting)
    {
        super(x, y, owner, setting);
        this.animation = new Animate().setMin(0).setMax(5).setSpeed(100).setEase(Easing.LINEAR).setReversed(!((BooleanValue) setting).getValue());
    }

    @Override
    public void initGui()
    {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        animation.update();
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        MCFontRenderer font = Fonts.clickGuiFont();
        if (((ClickGUI) Sleek.getInstance().getModuleManager().getModuleByName("Click GUI")).fonttoggle.getValue()) {
            font.drawStringWithShadow("§7" + getSetting().getName(), x + 5 - 0.3, (y + (getOffset() / 2F - (12 / 2F))) + 1.2, -1);
        } else {
            fontRenderer.drawString("§7" + getSetting().getName(), x + 5, y + (getOffset() / 2F - (fontRenderer.FONT_HEIGHT / 2F)), -1, true);

        }//Gui.drawRect(x + defaultWidth - 15, y, x + defaultWidth - 5, y + 10, darkerMainColor);
        RenderUtils.drawFilledCircle(x + defaultWidth - 10, (int) (y + (getOffset() / 2F - (fontRenderer.FONT_HEIGHT / 2F)) + 6.75f), 5, new Color(darkerMainColor));

        if (((BooleanValue) getSetting()).getValue() || animation.getValue() != 0) {
            RenderUtils.drawFilledCircle(x + defaultWidth - 10, (int) (y + (getOffset() / 2F - (fontRenderer.FONT_HEIGHT / 2F)) + 6.75f), animation.getValue(), new Color(enabledColor));
            GlStateManager.resetColor();
            GL11.glColor4f(1, 1, 1, 1);
        }
    }


    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if(RenderUtils.hover(x, y, mouseX, mouseY, defaultWidth, getOffset())) {
            BooleanValue set = (BooleanValue) getSetting();
            set.setValue(!set.getValue());
            animation.setReversed(!set.getValue());
            return true;
        }
        return false;
    }

    @Override
    public void onGuiClosed(int mouseX, int mouseY, int mouseButton)
    {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {

    }

    @Override
    public int getOffset()
    {
        return 15;
    }
}
