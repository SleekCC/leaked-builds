package today.sleek.client.gui.click.components.tab.cheat;

import today.sleek.Sleek;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.components.Component;
import today.sleek.client.gui.click.components.base.BaseContainer;
import today.sleek.client.gui.click.tab.cheat.TabDefaultCheat;
import today.sleek.client.gui.click.utils.Draw;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.font.Fonts;

import java.awt.*;

/**
 * @author antja03
 */
public class ContainerCheats extends BaseContainer {

    private TabDefaultCheat parentTab;
    public ModuleCategory cheatCategory;
    private int scrollIndex;

    public ContainerCheats(Interface theInterface, TabDefaultCheat parentTab, ModuleCategory cheatCategory, double x, double y, double width, double height) {
        super(theInterface, x, y, width, height);
        this.parentTab = parentTab;
        this.cheatCategory = cheatCategory;
        this.scrollIndex = 0;
        double modY = y;
        for (Module cheat : Sleek.getInstance().getModuleManager().getModules()) {
            if (parentTab.getSelectedCheat() == null) {
                parentTab.setSelectedCheat(cheat);
            }
            if (cheat.getCategory().equals(cheatCategory)) {
                components.add(new ButtonCheat(theInterface, parentTab, this, cheat, x, modY, 148, 25, null));
                modY += 20;
            }
        }
    }

    public void drawComponent(double x, double y) {

        if (components.size() > 8) {
            double barHeight = maxHeight;
            double div = barHeight / components.size();
            if (components.size() > 8) {
                barHeight -= (components.size() - 8) * div;
            }
            double barPosition = div * scrollIndex;

            Draw.drawRectangle(x + maxWidth - 1.5, theInterface.getPositionY() + barPosition + 1, x + maxWidth, theInterface.getPositionY() + barPosition + barHeight - 1, theInterface.getColor(110, 110, 110, 255));
        }
        String cat = cheatCategory.name().replaceAll("MOVEMENT", "Movement").replaceAll("VISUALS", "Render").replaceAll("COMBAT", "Combat").replaceAll("PLAYER", "Player").replaceAll("MISC", "Misc");
        Draw.drawRectangle(theInterface.getPositionX() + 25, theInterface.getPositionY() - 1, theInterface.getPositionX() + 173, theInterface.getPositionY() + 251, new Color(32, 31, 32).getRGB());
        int index = 0;
        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 10) {
                component.drawComponent(theInterface.getPositionX() + component.positionX, theInterface.getPositionY() + (25 * index));
                index += 1;
            }
        }
    }

    public boolean mouseButtonClicked(int button) {
        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex  + 8) {
                if (component.mouseButtonClicked(button)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void mouseReleased() {
        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 8) {
                component.mouseReleased();
            }
        }
    }

    public void mouseScrolled(final int scrollDirection) {
        if (theInterface.getCurrentFrameMouseX() < theInterface.getPositionX() + positionX || theInterface.getCurrentFrameMouseX() > theInterface.getPositionX() + maxWidth)
            return;

        if (scrollDirection == 1) {
            if (scrollIndex < components.size() - 8) {
                scrollIndex += 1;
            }
        } else {
            if (scrollIndex > 0) {
                scrollIndex -= 1;
            }
        }

        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 8) {
                component.mouseScrolled(scrollDirection);
            }
        }
    }

    public boolean keyTyped(char typedChar, int keyCode) {
        for (Component component : this.components) {
            if (components.indexOf(component) >= scrollIndex && components.indexOf(component) < scrollIndex + 8) {
                if (component.keyTyped(typedChar, keyCode)) {
                    return true;
                }
            }
        }
        return false;
    }
}
