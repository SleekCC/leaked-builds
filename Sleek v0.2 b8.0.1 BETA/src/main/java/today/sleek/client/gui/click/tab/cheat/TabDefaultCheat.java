package today.sleek.client.gui.click.tab.cheat;

import today.sleek.Sleek;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.client.gui.click.Interface;
import today.sleek.client.gui.click.components.tab.cheat.ContainerCheats;
import today.sleek.client.gui.click.components.tab.cheat.ContainerProperties;
import today.sleek.client.gui.click.tab.Tab;
import today.sleek.client.modules.impl.Module;

/**
 * @author antja03
 */
public class TabDefaultCheat extends Tab {

    private ModuleCategory cheatCategory;

    private Module selectedCheat;

    public TabDefaultCheat(Interface theInterface, ModuleCategory cheatCategory) {
        super(theInterface);
        this.cheatCategory = cheatCategory;
        components.add(new ContainerCheats(theInterface, this, cheatCategory, 25, 2, 150, theInterface.getHeight()));
        for (Module module : Sleek.getInstance().getModuleManager().getModules()) {
            if (module.getCategory() == cheatCategory) {
                this.components.add(new ContainerProperties(theInterface, this, module, 175, 0, 150, theInterface.getHeight()));
            }
        }
    }

    @Override
    public void onTick() {
        getInterface().setWidth(300);
        super.onTick();
    }

    public Module getSelectedCheat() {
        return selectedCheat;
    }

    public void setSelectedCheat(Module selectedCheat) {
        this.selectedCheat = selectedCheat;
    }
}
