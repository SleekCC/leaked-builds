package today.sleek.client.gui.legacy.clickgui.frame.components.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import today.sleek.Sleek;
import today.sleek.base.value.Value;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.gui.legacy.clickgui.frame.Values;
import today.sleek.client.gui.legacy.clickgui.frame.components.Component;
import today.sleek.client.gui.legacy.clickgui.frame.components.FrameModule;
import today.sleek.client.modules.impl.visuals.ClickGUI;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.font.MCFontRenderer;
import today.sleek.client.utils.render.RenderUtils;

public class EnumSetting extends Component implements Values {
    public EnumSetting(int x, int y, FrameModule owner, Value setting) {
        super(x, y, owner, setting);
    }

    @Override
    public void initGui() {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        MCFontRenderer font = Fonts.clickGuiFont();
        if (((ClickGUI) Sleek.getInstance().getModuleManager().getModuleByName("Click GUI")).fonttoggle.getValue()) {
            font.drawStringWithShadow("§7" + getSetting().getName(), x + 5, y + (getOffset() / 2F - (9 / 2F)), -1);
            font.drawStringWithShadow(((ModeValue) getSetting()).getValue().toUpperCase(), x + defaultWidth - Fonts.Verdana.getStringWidth(((ModeValue) getSetting()).getValue().toUpperCase()) - 5, (y + (getOffset() / 2F - (12 / 2F))) + 1.2, -1);
        } else {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
            fontRenderer.drawString("§7" + getSetting().getName(), x + 5, y + (getOffset() / 2F - (fontRenderer.FONT_HEIGHT / 2F)), -1, true);
            fontRenderer.drawString(((ModeValue) getSetting()).getValue().toUpperCase(), x + defaultWidth - fontRenderer.getStringWidth(((ModeValue) getSetting()).getValue().toUpperCase()) - 5, y + (getOffset() / 2F - (fontRenderer.FONT_HEIGHT / 2F)), -1, true);
        }

    }
    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtils.hover(x, y, mouseX, mouseY, defaultWidth, getOffset())) {
            ModeValue enumValue = (ModeValue) getSetting();

            int enumIndex = 0;
            for (String _enum : enumValue.getChoices()) {
                if (_enum.equals(enumValue.getValue())) break;
                ++enumIndex;
            }

            if (mouseButton == 1) {
                if (enumIndex - 1 >= 0) {
                    enumValue.setValue(enumValue.getChoices().get(enumIndex - 1));
                } else {
                    enumValue.setValue(enumValue.getChoices().get(enumValue.getChoices().size() - 1));
                }

                if (enumValue.getOwner().isToggled()) {
                    enumValue.getOwner().toggle();
                    enumValue.getOwner().toggle();
                }
            }

            if (mouseButton == 0) {
                if (enumIndex + 1 < enumValue.getChoices().size()) {
                    enumValue.setValue(enumValue.getChoices().get(enumIndex + 1));
                } else {
                    enumValue.setValue(enumValue.getChoices().get(0));
                }

                if (enumValue.getOwner().isToggled()) {
                    enumValue.getOwner().toggle();
                    enumValue.getOwner().toggle();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onGuiClosed(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public int getOffset() {
        return 15;
    }
}
