package today.sleek.client.commands.impl;

import today.sleek.Sleek;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.utils.chat.ChatUtil;

@CommandData(
        name = "reload",
        description = "Used for reloading some of the client. used for debugging"
)
public class ReloadCommand extends Command {

    @Override
    public void run(String[] args) {
        Sleek.getInstance().getModuleManager().reloadModules();
        Sleek.getInstance().getKeybindManager().load();
        ChatUtil.log("Reloaded.");
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("all")) {
                Sleek.getInstance().getCommandManager().clearCommands();
                Sleek.getInstance().getCommandManager().registerCommands();
            }
        }
    }
}
