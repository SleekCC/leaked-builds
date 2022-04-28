package today.sleek.client.gui.legacy.clickgui.frame;

import today.sleek.client.utils.render.ColorPalette;

import java.awt.*;

public interface Values {
    int stringColor = -1;

    int defaultWidth = 125;
    int defaultHeight = 300;


    int enabledColor = ColorPalette.LIGHT_BLUE.getColor().getRGB();

    int mainColor = new Color(0,0,0, 130).getRGB();
    int darkerMainColor = new Color(32, 32, 32).getRGB();
    int darkerMainColor2 = new Color(64, 64, 64).getRGB();
    int headerColor = new Color(64, 64, 64, 100).getRGB();

    int outlineWidth = 0;
    int categoryNameHeight = 20;

    int moduleHeight = 15;

    boolean hoveredColor = false;
}
