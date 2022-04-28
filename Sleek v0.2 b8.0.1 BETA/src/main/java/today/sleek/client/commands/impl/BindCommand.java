package today.sleek.client.commands.impl;

import org.lwjgl.input.Keyboard;
import today.sleek.Sleek;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.chat.ChatUtil;

@CommandData(
        name = "bind",
        description = "Binds a module"
)
public class BindCommand extends Command {

    @Override
    public void run(String[] args) {
        try {
            if (args[0].equalsIgnoreCase("list")) {
                ChatUtil.log("The Current Binds Are:");
                for (Module module : Sleek.getInstance().getModuleManager().getModules()) {
                    if (module.getKeyBind() != 0) {
                        ChatUtil.log(module.getName() + " - " + Keyboard.getKeyName(module.getKeyBind()));
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("del")) {
                Module module = Sleek.getInstance().getModuleManager().getModuleByName(args[0].replace('_', ' '));
                ChatUtil.log("Deleted the bind.");
                module.setKeyBind(0, true);
            } else {
                Module module = Sleek.getInstance().getModuleManager().getModuleByName(args[0].replace('_', ' '));
                if (module != null) {
                    int key = Keyboard.getKeyIndex(args[1].toUpperCase());
                    if (key != -1) {
                        ChatUtil.log("You've set the bind to " + Keyboard.getKeyName(key) + ".");
                        module.setKeyBind(key, true);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            ChatUtil.log("Usage: bind [module] [key]");
            ChatUtil.log("Usage: bind del [module]");
            ChatUtil.log("Usage: bind list");
        } catch (Exception exception) {
            ChatUtil.log("Usage: bind [module] [key]");
            ChatUtil.log("Usage: bind del [module]");
            ChatUtil.log("Usage: bind list");
            exception.printStackTrace();
//            ChatUtil.displayChatMessage("Invalid arguments.");
        }
    }
}
