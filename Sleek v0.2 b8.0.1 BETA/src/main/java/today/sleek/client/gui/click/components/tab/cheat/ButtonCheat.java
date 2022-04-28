package today.sleek.client.gui.click.components.tab.cheat;

import java.awt.Color;
import org.lwjgl.input.*;
import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.components.base.BaseButton;
import today.sleek.client.gui.click.tab.cheat.TabDefaultCheat;
import today.sleek.client.gui.click.utils.Draw;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.math.Stopwatch;

public class ButtonCheat extends BaseButton
{
    private TabDefaultCheat parentTab;
    private ContainerCheats parentContainer;
    private Module cheat;
    private Stopwatch stopwatch;
    private int opacityOffset;
    private boolean listeningForKey;
    
    public ButtonCheat(final Interface theInterface, final TabDefaultCheat parentTab, final ContainerCheats parentContainer, final Module cheat, final double x, final double y, final double width, final double height, final Action action) {
        super(theInterface, x, y, width, height, action);
        this.stopwatch = new Stopwatch();
        this.opacityOffset = 0;
        this.listeningForKey = false;
        this.parentTab = parentTab;
        this.parentContainer = parentContainer;
        this.cheat = cheat;
    }

    int animation;
    boolean s;
    int prev_anim;
    
    @Override
    public void drawComponent(final double x, final double y) {
        this.positionX = x - this.theInterface.getPositionX();
        this.positionY = y - this.theInterface.getPositionY();
        if (this.stopwatch.timeElapsed(50)) {
            if (this.isMouseOver()) {
                if (this.opacityOffset < 80) {
                    this.opacityOffset += 16;
                }
            }
            else if (this.opacityOffset > 0) {
                this.opacityOffset -= 16;
            }
            this.stopwatch.resetTime();
        }
        Color color;
        if (!(parentTab.getSelectedCheat() == this.cheat)) {
            color = new Color(18, 17, 18);
        } else {
            color = new Color(20, 19, 20);
        }

        Draw.drawRectangle(x + 3, y + 1, x + this.maxWidth - 3, y - 2 + this.maxHeight + 1, new Color(40, 40, 40).getRGB());


        //State selector
        {
            Draw.drawRectangle(x + maxWidth - 25, y + 4, x + maxWidth - 5, y + 12, theInterface.getColor(60, 60, 60));
            if (cheat.isToggled())
                Draw.drawRectangle(x + maxWidth - 14, y + 5, x + maxWidth - 6, y + 11, theInterface.getColor(80, 150, 80));
            else
                Draw.drawRectangle(x + maxWidth - 24, y + 5, x + maxWidth - 16, y + 11, theInterface.getColor(200, 80, 80));
        }

        if (cheat.isToggled()) {
            if (!this.listeningForKey) {
                Fonts.Arial18.drawString("  " + this.cheat.getName(), x + 3, y + 9, Color.WHITE.getRGB());
            } else {
                Fonts.Arial18.drawString("  " + "...", x + 8, y + 9, Color.WHITE.getRGB());
            }
            //Fonts.Arial30.drawString(" �a�", x + 2.0, y + 6.5, Color.WHITE.getRGB());
        } else {
            if (!this.listeningForKey) {
                Fonts.Arial18.drawString("  " + this.cheat.getName(), x + 3, y + 9, Color.WHITE.getRGB());
            } else {
                Fonts.Arial18.drawString("  " + "...", x + 8, y + 9, Color.WHITE.getRGB());
            }
        }

        //Fonts.Arial12.drawString("  " + this.cheat.getDescription(), x + 8, y + 18, Color.GRAY.getRGB());
    }
    
    @Override
    public boolean mouseButtonClicked(final int button) {
        String bindText = (this.cheat.getKeyBind() != 0) ? Keyboard.getKeyName(this.cheat.getKeyBind()) : "  ";
        if (this.listeningForKey) {
            bindText = "...";
        }

        if (theInterface.isMouseInBounds(
                theInterface.getPositionX() + positionX + maxWidth - 25,
                theInterface.getPositionX() + positionX + maxWidth - 5,
                theInterface.getPositionY() + positionY + 4,
                theInterface.getPositionY() + positionY + 12)) {
            cheat.setToggled(!cheat.isToggled());
            return true;
        }

        if (this.theInterface.isMouseInBounds(this.theInterface.getPositionX() + this.positionX, this.theInterface.getPositionX() + this.positionX + this.maxWidth, this.theInterface.getPositionY() + this.positionY, this.theInterface.getPositionY() + this.positionY + this.maxHeight)) {
            switch (button) {
                case 0: {
                    this.parentTab.setSelectedCheat(this.cheat);
                    break;
                }

                case 1: {
                    this.cheat.setToggled(!this.cheat.isToggled());
                    break;
                }

                case 2: {
                    if (!listeningForKey) {
                        this.listeningForKey = true;
                    } else {
                        this.listeningForKey = false;
                    }
                    break;
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean keyTyped(final char typedChar, final int keyCode) {
        if (this.listeningForKey) {
            if (keyCode == 1) {
                this.cheat.setKeyBind(0);
                this.listeningForKey = false;
            }
            else {
                this.cheat.setKeyBind(keyCode);
                this.listeningForKey = false;
            }
            return true;
        }
        return false;
    }
}
