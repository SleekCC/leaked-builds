package today.sleek.client.commands.impl;

import today.sleek.Sleek;
import today.sleek.base.scripting.base.ScriptFile;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.utils.chat.ChatUtil;

import java.util.Arrays;

@CommandData(
        name = "script",
        description = "Scripts"
)
public class ScriptCommand extends Command {
    @Override
    public void run(String[] args) {
        ChatUtil.log(args[0]);
        try {
            switch (args[0]) {
                case "load": {
                    Sleek.getInstance().getScriptManager().loadScript(args[1]);
                    ChatUtil.log("Loaded script " + args[1]);
                    break;
                }
                case "reload": {
                    Sleek.getInstance().getScriptManager().unloadScripts();
                    Sleek.getInstance().getScriptManager().loadScripts();
                    ChatUtil.log("Reloaded scripts");
                    break;
                }
                case "unload": {
                    Sleek.getInstance().getScriptManager().unloadScripts();
                    ChatUtil.log("Unloaded scripts");
                    break;
                }
                case "list": {
                    ChatUtil.log("Scripts: ");
                    for (ScriptFile file : Sleek.getInstance().getScriptManager().getScripts()) {
                        ChatUtil.log("- " + file.getName());
                    }
                    ChatUtil.log("" + Sleek.getInstance().getScriptManager().getScripts().size());
                }
                break;
            }
        } catch (Exception e) {
            ChatUtil.log(".script load <script>");
            ChatUtil.log(".script unload");
            ChatUtil.log(".script reload");
            ChatUtil.log(".script list");
            e.printStackTrace();
        }
    }
}
