package me.kansio.client.modules.impl.visuals;

import org.lwjgl.input.Keyboard;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.gui.click.Screen;
import today.sleek.client.modules.impl.Module;

@ModuleData(
        name = "Click GUI",
        category = ModuleCategory.VISUALS,
        description = "The click gui... nothing special (Credit: Wykt)",
        bind = Keyboard.KEY_RSHIFT
)
public class ClickGUI extends Module {

    public BooleanValue fonttoggle = new BooleanValue("Font", this, true);
    public ModeValue fontmode = new ModeValue("Mode", this, fonttoggle, "SF Regular", "Lucida Sans", "Verdana");
    public NumberValue<Integer> animspeed = new NumberValue("Animation Speed", this, 50, 1, 100, 1);
    public BooleanValue rainbow = new BooleanValue("RGB OMG", this, false);


    @Override
    public void onEnable() {
        try {
            mc.displayGuiScreen(new Screen());
            toggle();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
