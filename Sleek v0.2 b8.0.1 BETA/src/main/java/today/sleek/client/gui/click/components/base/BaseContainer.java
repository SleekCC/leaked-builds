package today.sleek.client.gui.click.components.base;

import java.util.ArrayList;
import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.components.Component;

/**
 * @author antja03
 */
public abstract class BaseContainer extends Component {

    protected final ArrayList<Component> components = new ArrayList<>();

    public BaseContainer(Interface theInterface, double x, double y, double width, double height) { super(theInterface, x, y, width, height); }

    public abstract void drawComponent(double x, double y);
    public boolean mouseButtonClicked(int button) {
        return false;
    }
    public void mouseReleased() { }
    public void mouseScrolled(final int scrollDirection) { }
    public boolean keyTyped(final int keyCode) {
        return false;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void onGuiClose() {
        for (Component component : this.components) {
            component.onGuiClose();
        }
    }

}
