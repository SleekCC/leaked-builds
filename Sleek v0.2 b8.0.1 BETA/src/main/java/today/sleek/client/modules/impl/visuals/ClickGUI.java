package today.sleek.client.modules.impl.visuals;

import org.lwjgl.input.Keyboard;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.gui.click.Screen;
import today.sleek.client.gui.nl.gui.NeverLoseGui;
import today.sleek.client.modules.impl.Module;

@ModuleData(
        name = "Click GUI",
        category = ModuleCategory.VISUALS,
        description = "The click gui... nothing special (Credit: Wykt)",
        bind = Keyboard.KEY_RSHIFT
)
public class ClickGUI extends Module {

    public ModeValue mode = new ModeValue("Mode", this, "Legacy", "New"/*, "Neverlose"*/);
    public BooleanValue fonttoggle = new BooleanValue("Font", this, true);
    public ModeValue fontmode = new ModeValue("Font Mode", this, fonttoggle, "SF Regular", "Lucida Sans", "Verdana", "Roobert");
    public NumberValue<Integer> animspeed = new NumberValue("Animation Speed", this, 50, 1, 250, 1);
    public BooleanValue rainbow = new BooleanValue("RGB OMG", this, false);


    @Override
    public void onEnable() {
        switch (mode.getValue()) {
            case "New": {
                try {
                    mc.displayGuiScreen(new Screen());
                    toggle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "Legacy": {
                try {
                    mc.displayGuiScreen(new today.sleek.client.gui.legacy.clickgui.frame.ClickGUI());
                    toggle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "Neverlose": {
                try {
                    mc.displayGuiScreen(new NeverLoseGui());
                    toggle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }


    }

}
