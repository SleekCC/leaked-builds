package today.sleek.client.commands.impl;

import today.sleek.Sleek;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.modules.ModuleManager;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.chat.ChatUtil;
@CommandData(
        name = "toggle",
        description = "Binds a module",
        aliases = {"t"}
)
public class ToggleCommand extends Command {

    @Override
    public void run(String[] args) {
        if (args.length != 1) {
            ChatUtil.log("Specify the module you'd like to toggle please.");
            return;
        }

        String moduleName = args[0];
        ModuleManager moduleManager = Sleek.getInstance().getModuleManager();

        Module mod = moduleManager.getModuleByNameIgnoreSpace(moduleName);

        if (mod == null) {
            ChatUtil.log("That module doesn't exist.");
            return;
        }

        ChatUtil.log("You've toggled " + moduleName);
        mod.toggle();
    }
}
