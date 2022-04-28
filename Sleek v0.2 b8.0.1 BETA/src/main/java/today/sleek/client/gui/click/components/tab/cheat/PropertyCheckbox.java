package today.sleek.client.gui.click.components.tab.cheat;

import java.awt.*;
import java.util.*;
import net.minecraft.client.Minecraft;
import today.sleek.base.value.Value;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.utils.Draw;
import today.sleek.client.utils.font.Fonts;

public class PropertyCheckbox extends PropertyComponent
{
    public PropertyCheckbox(final Interface theInterface, final Value value, final double x, final double y, final double width, final double height) {
        super(theInterface, value, x, y, width, height);
    }
    
    @Override
    public void drawComponent(final double x, final double y) {
        this.positionX = x - this.theInterface.getPositionX();
        this.positionY = y - this.theInterface.getPositionY();
        Draw.drawBorderedRectangle(x + this.maxWidth - 18.0, y + 3.0, x + this.maxWidth - 10.0, y + 11.0, 0.5, new Color(32, 31, 32).getRGB(), new Color(255, 3, 0, 166).getRGB(), true);
        if (((BooleanValue)this.getProperty()).getValue()) {
            Draw.drawRectangle(x + this.maxWidth - 17.5, y + 3.5, x + this.maxWidth - 9.5, y + 10.5, new Color(163, 2, 0).getRGB());
            Minecraft.getMinecraft().fontRendererObj.drawString("\u2713", (int)x + 133, (int) (y + 2), new Color(249, 255, 243).getRGB());
        }
        Fonts.Arial15.drawString(this.getProperty().getName(), x + 6.0, y + this.maxHeight / 2.0 - 5.5, this.theInterface.getColor(255, 255, 255));
    }
    
    @Override
    public boolean mouseButtonClicked(final int button) {
        if (button == 0 && this.theInterface.isMouseInBounds(this.theInterface.getPositionX() + this.positionX + this.maxWidth - 18.0, this.theInterface.getPositionX() + this.positionX + this.maxWidth - 10.0, this.theInterface.getPositionY() + this.positionY + 3.0, this.theInterface.getPositionY() + this.positionY + 11.0)) {
            this.getProperty().setValue(!((BooleanValue)this.getProperty()).getValue());
            return true;
        }
        return false;
    }
}
