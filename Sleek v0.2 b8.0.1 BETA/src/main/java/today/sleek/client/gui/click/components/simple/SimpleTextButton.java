package today.sleek.client.gui.click.components.simple;

import net.minecraft.util.ResourceLocation;
import today.sleek.Sleek;
import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.components.base.BaseButton;
import today.sleek.client.gui.click.utils.Draw;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.font.MCFontRenderer;

import java.awt.*;

/**
 * @author antja03
 */
public class SimpleTextButton extends BaseButton {

    private Color buttonColor;
    private Color buttonColorHovered;
    private Color textColor;
    private String text;
    private MCFontRenderer fontRenderer;

    public SimpleTextButton(Interface theInterface, Color buttonColor, Color buttonColorHovered, String text, int fontSize, Color textColor, double positionX, double positionY, double maxWidth, double maxHeight, Action action) {
        super(theInterface, positionX, positionY, maxWidth, maxHeight, action);
        this.buttonColor = buttonColor;
        this.buttonColorHovered = buttonColorHovered;
        this.text = text;
        this.textColor = textColor;
        this.fontRenderer = Fonts.clickGuiVerdana;
    }

    public void drawComponent(double x, double y) {
        this.positionX = x - theInterface.getPositionX();
        this.positionY = y - theInterface.getPositionY();

        int color = isMouseOver() ?
                Sleek.getInstance().userInterface.theInterface.getColor(buttonColorHovered.getRed(), buttonColorHovered.getGreen(), buttonColorHovered.getBlue(), buttonColorHovered.getAlpha())
                : Sleek.getInstance().userInterface.theInterface.getColor(buttonColor.getRed(), buttonColor.getGreen(), buttonColor.getBlue(), buttonColor.getAlpha());

        Draw.drawBorderedRectangle(x, y, x + maxWidth, y + maxHeight, 0.5,  new Color(32, 31, 32).getRGB(), new Color(54, 56, 56, 175).getRGB(), true);
        fontRenderer.drawCenteredString(text, x + maxWidth / 2, y + (maxHeight / 2) - (fontRenderer.getStringHeight(text) / 2),
                Sleek.getInstance().userInterface.theInterface.getColor(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), textColor.getAlpha()));
    }

}
