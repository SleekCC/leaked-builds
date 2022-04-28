package today.sleek.client.gui.click.components.tab.configuration;

import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.components.base.BaseTextEntry;
import today.sleek.client.gui.click.utils.Draw;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.font.MCFontRenderer;

/**
 * @author antja03
 */
public class TextEntryConfigurations extends BaseTextEntry {

    public TextEntryConfigurations(Interface theInterface, MCFontRenderer fontRenderer, double x, double y, double width, double height) {
        super(theInterface, fontRenderer, x, y, width, height);
    }

    @Override
    public void drawComponent(double x, double y) {
        this.positionX = x - theInterface.getPositionX();
        this.positionY = y - theInterface.getPositionY();

        Draw.drawRectangle(x, y, x + maxWidth, y + maxHeight, theInterface.getColor(35, 35, 35));
        Fonts.Arial12.drawString(getContent(), x + 2, y + 3, theInterface.getColor(200, 200, 200));
        if (focused)
            Draw.drawRectangle(x + 2 + Fonts.Arial12.getStringWidth(getContent()), y + 7, x + 2 + Fonts.Arial12.getStringWidth(getContent() + 2), y + 7.5, theInterface.getColor(230, 230, 230));
    }

    @Override
    public void execute() {
        this.focused = false;
    }

}
