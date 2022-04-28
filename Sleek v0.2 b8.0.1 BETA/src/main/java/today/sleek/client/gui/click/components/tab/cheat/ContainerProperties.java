package today.sleek.client.gui.click.components.tab.cheat;

import org.lwjgl.input.Keyboard;
import today.sleek.base.value.Value;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.components.Component;
import today.sleek.client.gui.click.components.base.BaseContainer;
import today.sleek.client.gui.click.tab.cheat.TabDefaultCheat;
import today.sleek.client.gui.click.utils.Draw;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.font.Fonts;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author antja03
 */
public class ContainerProperties extends BaseContainer {

    private TabDefaultCheat parentTab;
    ArrayList<Component> activeComponents;
    public Module module;
    public int scrollIndex;

    public ContainerProperties(Interface theInterface, TabDefaultCheat parentTab, Module module, double x, double y, double width, double height) {
        super(theInterface, x, y, width, height);
        this.parentTab = parentTab;
        this.module = module;
        this.scrollIndex = 0;
        double valueY = y + 2;
        for (Value value : module.getValues()) {
            if (value instanceof BooleanValue) {
                components.add(new PropertyCheckbox(theInterface, value, x, valueY, width, 20));
                valueY += 20;
            } else if (value instanceof NumberValue) {
                components.add(new PropertySlider(theInterface, value, x, valueY, width, 20));
                valueY += 20;
            } else if (value instanceof ModeValue) {
                components.add(new PropertyComboBox(theInterface, value, x, valueY, width, 20));
                valueY += 20;
            }
        }
    }

    public void drawComponent(double x, double y) {
        if (parentTab.getSelectedCheat() != module)
            return;

        activeComponents = getActiveComponents();

        //Quick workaround for a scrolling bug
        if (scrollIndex + 10 > getActiveComponents().size()) {
            if (getActiveComponents().size() - 10 < 0) {
                scrollIndex = 0;
            } else {
                scrollIndex = getActiveComponents().size() - 10;
            }
        }

        //Scroll bar
        Draw.drawRectangle(x + maxWidth - 1.5, y, x + maxWidth, theInterface.getPositionY() + maxHeight, new Color(29, 29, 29, 255).getRGB());
        if (module.getValues().size() > 10) {
            double barHeight = this.maxHeight;
            double div = barHeight / components.size();
            if (activeComponents.size() > 10) {
                barHeight -= (activeComponents.size() - 10) * div;
            }
            double barPosition = div * scrollIndex;

            Draw.drawRectangle(x + this.maxWidth - 2, theInterface.getPositionY() + barPosition - 1, x + this.maxWidth, theInterface.getPositionY() + barPosition + barHeight + 0.5, new Color(70, 70, 70, 255).getRGB());
        }


        String cat = module.getCategory().name().replaceAll("MOVEMENT", "Movement").replaceAll("VISUAL", "Render").replaceAll("COMBAT", "Combat").replaceAll("PLAYER", "Player").replaceAll("MISC", "Misc");

        Fonts.Arial12.drawString(cat + " / " + module.getName(), theInterface.getPositionX() + 180, theInterface.getPositionY() + 7, new Color(118, 118, 118).getRGB());
        if (!(module.getKeyBind() == 0)) {
            Fonts.Arial12.drawString("Keybind: " + Keyboard.getKeyName(module.getKeyBind()), theInterface.getPositionX() + 180, theInterface.getPositionY() + 14, new Color(118, 118, 118).getRGB());
        }

        //Drawing components
        double height = 20 * (activeComponents.size() - 1);
        for (int i = activeComponents.size() - 1; i > -1; i--) {
            Component component = activeComponents.get(i);
            if (component instanceof PropertyComponent) {
                PropertyComponent vComponent = (PropertyComponent) component;
                if (activeComponents.indexOf(component) >= scrollIndex && activeComponents.indexOf(component) < scrollIndex + 10) {
                    component.drawComponent(theInterface.getPositionX() + component.positionX, 50 + theInterface.getPositionY() + height - scrollIndex * 20);
                }
                height -= 20;

            }
        }
    }

    public boolean mouseButtonClicked(int button) {
        if (parentTab.getSelectedCheat() != module)
            return false;

        ArrayList<Component> activeComponents = getActiveComponents();
        for (Component component : activeComponents) {
            if (activeComponents.indexOf(component) >= scrollIndex && activeComponents.indexOf(component) < scrollIndex + 10) {
                if (component.mouseButtonClicked(button)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void mouseReleased() {
        ArrayList<Component> activeComponents = getActiveComponents();
        for (Component component : activeComponents) {
            component.mouseReleased();
        }
    }

    public void mouseScrolled(final int scrollDirection) {
        if (parentTab.getSelectedCheat() != module)
            return;

        ArrayList<Component> activeComponents = getActiveComponents();

        for (Component component : activeComponents) {
            if (activeComponents.indexOf(component) >= scrollIndex && activeComponents.indexOf(component) < scrollIndex + 10) {
                component.mouseScrolled(scrollDirection);
            }
        }

        if (theInterface.getCurrentFrameMouseX() < theInterface.getPositionX() + theInterface.getWidth() / 2)
            return;

        if (scrollDirection == 1) {
            if (scrollIndex < activeComponents.size() - 10) {
                scrollIndex += 1;
            }
        } else {
            if (scrollIndex > 0) {
                scrollIndex -= 1;
            }
        }
    }

    public boolean keyTyped(char typedChar, int keyCode) {
        if (parentTab.getSelectedCheat() != module)
            return false;

        ArrayList<Component> activeComponents = getActiveComponents();
        for (Component component : activeComponents) {
            if (activeComponents.indexOf(component) >= scrollIndex && activeComponents.indexOf(component) < scrollIndex + 10) {
                if (component.keyTyped(typedChar, keyCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Component> getActiveComponents() {
        ArrayList<Component> activeComponents = new ArrayList<>();
        for (int i = this.components.size() - 1; i > -1; i--) {
            Component component = components.get(i);
            if (component instanceof PropertyComponent) {
                PropertyComponent vComponent = (PropertyComponent) component;
                activeComponents.add(component);
            } else {
                activeComponents.add(component);
            }
        }
        Collections.reverse(activeComponents);
        return activeComponents;
    }
}
