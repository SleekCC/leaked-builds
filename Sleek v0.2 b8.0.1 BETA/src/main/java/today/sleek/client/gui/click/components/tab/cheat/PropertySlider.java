package today.sleek.client.gui.click.components.tab.cheat;

import today.sleek.base.value.Value;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.utils.Draw;
import today.sleek.client.gui.click.utils.Mafs;
import today.sleek.client.utils.font.Fonts;

import java.awt.*;
import java.util.*;

public class PropertySlider extends PropertyComponent
{
    private boolean mouseDragging;
    private boolean typing;
    private String typedChars;
    private double widthOfSlider;
    private NumberValue property;
    private double currentPosition;
    private ArrayList<Double> possibleValues;
    
    public PropertySlider(final Interface theInterface, final Value value, final double x, final double y, final double width, final double height) {
        super(theInterface, value, x, y, width, height);
        this.possibleValues = new ArrayList<Double>();
        this.mouseDragging = false;
        this.typing = false;
        this.typedChars = "";
        this.widthOfSlider = x + this.maxWidth - 10.0 - (x + 80.0);
        this.property = (NumberValue)value;
        this.currentPosition = this.widthOfSlider / (this.property.getMax().floatValue() - this.property.getMin().floatValue()) * (this.property.getValue().floatValue() - this.property.getMin().floatValue());
    }
    
    @Override
    public void drawComponent(final double x, final double y) {
        this.positionX = x - this.theInterface.getPositionX();
        this.positionY = y - this.theInterface.getPositionY();
        //Draw.drawCircle(x + 80, y + this.maxHeight / 2.0 - 6.0, 4, new Color(255, 255, 255).getRGB());
        Fonts.Arial15.drawString(this.getProperty().getName(), x + 6.0, y + this.maxHeight / 2.0 - 5.5, this.theInterface.getColor(255, 255, 255));
        Draw.drawRectangle(x + 80.0, y + this.maxHeight / 2.0 - 6.0, x + this.maxWidth - 10.0, y + this.maxHeight / 2.0 - 4.0, new Color(54, 56, 56).getRGB());
        Draw.drawRectangle(x + 80.0, y + this.maxHeight / 2.0 - 4.5, x + 80.0 + this.currentPosition + 1.5, y + this.maxHeight / 2.0 - 4.5,
                new Color(147, 2, 0).getRGB());
        Draw.drawRectangle(x + 80.0, y + this.maxHeight / 2.0 - 6.0, x + 80.0 + this.currentPosition, y + this.maxHeight / 2.0 - 4.0,
                new Color(147, 2, 0).getRGB());
        if (this.typing) {
            Fonts.Arial12.drawCenteredString(String.valueOf(this.typedChars) + "_", (float)(x + 80.0 + this.widthOfSlider / 2.0), (float)(y + this.maxHeight / 2.0 - 2.0), this.typing ? this.theInterface.getColor(255, 255, 255) : this.theInterface.getColor(255, 255, 255));
        }
        else {
            Fonts.Arial12.drawCenteredString(String.valueOf(Mafs.round(this.property.getValue().floatValue(), 2)), (float)(x + 80.0 + this.currentPosition), (float)(y + this.maxHeight / 2.0), this.typing ? this.theInterface.getColor(255, 255, 255) : this.theInterface.getColor(255, 255, 255));
        }
        if (this.theInterface.isClosing()) {
            this.mouseDragging = false;
            this.typing = false;
            return;
        }
        if (this.typing) {
            this.mouseDragging = false;
        }
        if (this.mouseDragging) {
            final double cursorPosOnBar = this.theInterface.getCurrentFrameMouseX() - x - 80.0;
            this.currentPosition = Mafs.clamp(cursorPosOnBar, 0.0, this.widthOfSlider);
            final double exactValue = Mafs.clamp(this.property.getMin().floatValue() + (this.property.getMax().floatValue() - this.property.getMin().floatValue()) * (cursorPosOnBar / this.widthOfSlider), this.property.getMin().floatValue(), this.property.getMax().floatValue());
            if (this.possibleValues.isEmpty()) {
                double current = this.property.getMin().doubleValue();
                this.possibleValues.add(current);
                while (current < this.property.getMax().floatValue()) {
                    current += this.property.getIncrement().doubleValue();
                    this.possibleValues.add(current);
                }
            }
            double bestValue = -1.0;
            for (final Double value : this.possibleValues) {
                if (bestValue == -1.0) {
                    bestValue = value;
                }
                else {
                    if (Mafs.getDifference(exactValue, value) >= Mafs.getDifference(exactValue, bestValue)) {
                        continue;
                    }
                    bestValue = value;
                }
            }
            this.property.setValue(bestValue);
        }
        else {
            this.setPositionBasedOnValue();
            this.possibleValues.clear();
        }
    }
    
    @Override
    public boolean mouseButtonClicked(final int button) {
        if (button == 0 && this.theInterface.isMouseInBounds(this.theInterface.getPositionX() + this.positionX + 80.0, this.theInterface.getPositionX() + this.positionX + this.maxWidth - 10.0, this.theInterface.getPositionY() + this.positionY + this.maxHeight / 2.0 - 12.0, this.theInterface.getPositionY() + this.positionY + this.maxHeight / 2.0 + 2.0)) {
            return this.mouseDragging = true;
        }
        if (this.theInterface.isMouseInBounds(this.theInterface.getPositionX() + this.positionX + 80.0, this.theInterface.getPositionX() + this.positionX + this.maxWidth - 10.0, this.theInterface.getPositionY() + this.positionY, this.theInterface.getPositionY() + this.positionY + this.maxHeight)) {
            this.typing = (button == 1);
            return true;
        }
        this.mouseDragging = false;
        this.typing = false;
        this.typedChars = "";
        return false;
    }
    
    @Override
    public void mouseReleased() {
        this.mouseDragging = false;
    }
    
    @Override
    public boolean keyTyped(final char typedChar, final int keyCode) {
        if (!this.typing) {
            return false;
        }
        if (keyCode == 1) {
            this.typing = false;
            return true;
        }
        final String allowedChars = "0123456789.";
        if (keyCode == 14) {
            if (this.typedChars.length() > 1) {
                this.typedChars = this.typedChars.substring(0, this.typedChars.length() - 1);
            }
            else if (this.typedChars.length() == 1) {
                this.typedChars = "";
            }
        }
        else if (keyCode == 28) {
            this.typedChars = "";
            this.typing = false;
        }
        else if (allowedChars.contains(Character.toString(typedChar)) && Fonts.Arial15.getStringWidth(this.typedChars) < this.maxWidth - 1.0) {
            this.typedChars = String.valueOf(this.typedChars) + Character.toString(typedChar);
        }
        this.property.setValue(this.typedChars);
        return true;
    }
    
    @Override
    public void onGuiClose() {
        this.mouseDragging = false;
        this.typing = false;
    }
    
    public void setPositionBasedOnValue() {
        this.currentPosition = this.widthOfSlider / (this.property.getMax().doubleValue() - this.property.getMin().doubleValue()) * (this.property.getValue().doubleValue() - this.property.getMin().doubleValue());
    }
}
