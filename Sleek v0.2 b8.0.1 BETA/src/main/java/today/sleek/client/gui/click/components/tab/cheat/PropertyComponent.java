package today.sleek.client.gui.click.components.tab.cheat;

import today.sleek.base.value.Value;
import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.components.Component;

/**
 * @author antja03
 */
public abstract class PropertyComponent extends Component {

    private Value property;

    public PropertyComponent(Interface theInterface, Value value, double x, double y, double width, double height) {
        super(theInterface, x, y, width, height);
        this.property = value;
    }

    public abstract void drawComponent(double x, double y);

    public boolean mouseButtonClicked(int button) {
        return false;
    }

    public boolean keyTyped(char typedChar, int keyCode) {
        return false;
    }

    public Value getProperty() {
        return property;
    }

}
