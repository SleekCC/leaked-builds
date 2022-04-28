package today.sleek.client.commands.impl;

import today.sleek.Sleek;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.chat.ChatUtil;


@CommandData(
        name = "panic",
        description = "Disables all modules"
)
public class PanicCommand extends Command {

    @Override
    public void run(String[] args) {
        for (Module mod : Sleek.getInstance().getModuleManager().getModules()) {
            if (mod.isToggled()) mod.toggle();
        }

        ChatUtil.log("Disabled all modules.");
    }
}
