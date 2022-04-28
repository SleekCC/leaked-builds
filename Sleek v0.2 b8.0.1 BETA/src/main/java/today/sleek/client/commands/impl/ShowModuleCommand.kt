package today.sleek.client.commands.impl

import today.sleek.Sleek
import today.sleek.client.commands.Command
import today.sleek.client.commands.CommandData
import today.sleek.client.modules.impl.Module
import today.sleek.client.utils.chat.ChatUtil
import java.util.function.Consumer

/**
 * @author Kansio
 */
@CommandData(
    name = "showmodule",
    description = "Hides a module"
)
class ShowModuleCommand : Command() {

    override fun run(args: Array<String>) {
        if (args.size != 1) {
            ChatUtil.log("Please specify a module to hide. You can also hide all modules by typing 'all'")
            return
        }
        if (args[0].equals("all", ignoreCase = true)) {
            Sleek.getInstance().moduleManager.modules.forEach(Consumer { module: Module -> module.isHidden = false })
            ChatUtil.log("You've shown all the modules.")
            return
        }
        val name = args[0]
        Sleek.getInstance().moduleManager.getModuleByNameIgnoreSpace(name).isHidden = false
        ChatUtil.log("You've shown the module '$name'.")
    }
}