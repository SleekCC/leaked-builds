package today.sleek.client.commands.impl;

import org.lwjgl.input.Keyboard;
import today.sleek.Sleek;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.chat.ChatUtil;
@CommandData(
        name = "binds",
        description = "Lists binds"
)
public class BindsCommand extends Command {

    @Override
    public void run(String[] args) {
        ChatUtil.log("The Current Binds Are:");
        for (Module module : Sleek.getInstance().getModuleManager().getModules()) {
            if (module.getKeyBind() != 0) {
                ChatUtil.log(module.getName() + " - " + Keyboard.getKeyName(module.getKeyBind()));
            }
        }
    }
}
