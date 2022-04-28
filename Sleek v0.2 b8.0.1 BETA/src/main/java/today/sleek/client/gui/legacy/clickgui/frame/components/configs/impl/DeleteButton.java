package today.sleek.client.gui.legacy.clickgui.frame.components.configs.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import today.sleek.Sleek;
import today.sleek.base.config.Config;
import today.sleek.client.gui.legacy.clickgui.frame.Values;
import today.sleek.client.gui.legacy.clickgui.frame.components.configs.ConfigComponent;
import today.sleek.client.gui.legacy.clickgui.frame.components.configs.FrameConfig;
import today.sleek.client.gui.legacy.clickgui.utils.render.animation.easings.Animate;
import today.sleek.client.gui.legacy.clickgui.utils.render.animation.easings.Easing;
import today.sleek.client.gui.notification.Notification;
import today.sleek.client.gui.notification.NotificationManager;
import today.sleek.client.modules.impl.visuals.ClickGUI;
import today.sleek.client.utils.render.RenderUtils;

import java.awt.*;

public class DeleteButton extends ConfigComponent implements Values {
    private final Animate animation;
    private Config config;

    public DeleteButton(int x, int y, FrameConfig owner)
    {
        super(x, y, owner);
        this.config = owner.config;

        this.animation = new Animate().setMin(0).setMax(5).setSpeed(15).setEase(Easing.LINEAR).setReversed(true);
    }

    @Override
    public void initGui()
    {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        animation.update();
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        fontRenderer.drawString("Delete Config", x + 5, y + (getOffset() / 2F - (fontRenderer.FONT_HEIGHT / 2F)), -1, true);
        //Gui.drawRect(x + defaultWidth - 15, y, x + defaultWidth - 5, y + 10, darkerMainColor);
        RenderUtils.drawFilledCircle(x + defaultWidth - 10, (int) (y + (getOffset() / 2F - (fontRenderer.FONT_HEIGHT / 2F)) + 6.75f), 5, new Color(darkerMainColor));

        if (animation.getValue() != 0) {
            RenderUtils.drawFilledCircle(x + defaultWidth - 10, (int) (y + (getOffset() / 2F - (fontRenderer.FONT_HEIGHT / 2F)) + 6.75f), animation.getValue(), new Color(enabledColor));
            GlStateManager.resetColor();
            GL11.glColor4f(1, 1, 1, 1);
        }
    }


    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if(RenderUtils.hover(x, y, mouseX, mouseY, defaultWidth, getOffset())) {
            //remove the config
            Sleek.getInstance().getConfigManager().removeConfig(config.getName());
            NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "Deleted config", "Successfully deleted", 1));

            //close the click gui
            Minecraft.getMinecraft().thePlayer.closeScreen();

            //retoggle it
            ClickGUI cgui = (ClickGUI) Sleek.getInstance().getModuleManager().getModuleByName("Click GUI");
            cgui.toggle();
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
