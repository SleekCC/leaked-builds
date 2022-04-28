package today.sleek.client.gui.click.components.tab.cheat;

import java.awt.*;
import java.util.ArrayList;
import today.sleek.base.value.Value;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.utils.Draw;
import today.sleek.client.utils.font.Fonts;

/**
 * @author antja03
 */
public class PropertyComboBox extends PropertyComponent {

    private ModeValue stringsProperty;
    private boolean extended;

    public PropertyComboBox(Interface theInterface, Value property, double x, double y, double width, double height) {
        super(theInterface, property, x, y, width, height);
        if (property instanceof ModeValue) {
            stringsProperty = (ModeValue) property;
        }
    }

    @Override public void drawComponent(double x, double y) {
        this.positionX = x - theInterface.getPositionX();
        this.positionY = y - theInterface.getPositionY();

        int keyCount = stringsProperty.getChoices().size();
        double boxPosX = x + 80;
        double boxPosY = y + 2;
        double boxWidth = 60;
        double boxHeight = 10;

        Fonts.Arial15.drawString(getProperty().getName(), x + 6, y + maxHeight / 2 - 5.5, theInterface.getColor(255, 255, 255));

        if (extended)
            boxHeight += 10 * (keyCount - 1);

        Draw.drawBorderedRectangle(boxPosX, boxPosY, boxPosX + boxWidth, boxPosY + boxHeight, 0.5, new Color(32, 31, 32).getRGB(), new Color(54, 56, 56, 175).getRGB(), true);

        if (extended) {
            ArrayList<String> options = getUnorderedList();
            for (String option : options) {
                double optionPosX = boxPosX;
                double optionPosY = boxPosY + options.indexOf(option) * 10;
                double optionWidth = boxWidth;
                double optionHeight = 10;

                if (stringsProperty.getValue().equalsIgnoreCase(option))
                    Draw.drawRectangle(optionPosX, optionPosY, optionPosX + optionWidth, optionPosY + optionHeight, new Color(147, 2, 0, 175).getRGB());

                Fonts.Arial12.drawCenteredString(option, optionPosX + optionWidth / 2, optionPosY + optionHeight / 2 - 1, new Color(230, 230, 230, 230).getRGB());
            }
        } else {
            Fonts.Arial12.drawCenteredString(getBoxLabel(), boxPosX + boxWidth / 2, boxPosY + boxHeight / 2 - 1, theInterface.getColor(255, 255, 255));
        }
    }

    @Override public boolean mouseButtonClicked(int button) {
        ModeValue stringsProperty = (ModeValue) getProperty();
        int keyCount = stringsProperty.getChoices().size();
        double boxPosX = positionX + 80;
        double boxPosY = positionY + 2;
        double boxWidth = 60;
        double boxHeight = 10;
        if (!extended) {
            if (theInterface.isMouseInBounds(
                    theInterface.getPositionX() + boxPosX,
                    theInterface.getPositionX() + boxPosX + boxWidth,
                    theInterface.getPositionY() + boxPosY,
                    theInterface.getPositionY() + boxPosY + boxHeight)) {
                extended = true;
                return true;
            }
        } else {
            ArrayList<String> options = getUnorderedList();
            for (String string : options) {
                double optionPosY = boxPosY + options.indexOf(string) * 10;
                if (theInterface.isMouseInBounds(
                        theInterface.getPositionX() + boxPosX,
                        theInterface.getPositionX() + boxPosX + boxWidth,
                        theInterface.getPositionY() + optionPosY,
                        theInterface.getPositionY() + optionPosY + 10)) {
                    stringsProperty.setValue(string);
                    return true;
                }
            }
        }
        extended = false;
        return false;
    }

    public void onGuiClose() {
        extended = false;
    }

    public String getBoxLabel() {
        String finalString = stringsProperty.getValueAsString();


        return finalString;
    }

    public ArrayList<String> getUnorderedList() {
        return new ArrayList<String>(stringsProperty.getChoices());
    }

    public ArrayList<String> getOrderedList() {
        return new ArrayList<String>(stringsProperty.getChoices());
    }

}
