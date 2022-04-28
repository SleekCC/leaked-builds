package today.sleek.client.commands.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import today.sleek.Sleek;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.network.HttpUtil;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@CommandData(
        name = "config",
        description = "Handles configs"
)
public class ConfigCommand extends Command {

    @Override
    public void run(String[] args) {

        try {

            switch (args[0].toLowerCase()) {
                case "save": {
                    Sleek.getInstance().getConfigManager().saveConfig(args[1]);
                    ChatUtil.log("Saved Config " + args[1]);
                    break;
                }
                case "load": {
                    if (args.length == 3) {
                        Sleek.getInstance().getConfigManager().loadConfig(args[1], args[2].equals("keys"));
                    } else {
                        Sleek.getInstance().getConfigManager().loadConfig(args[1], false);
                    }
                    // .CONFIG BLOCKS ADESGOJHAOG
                    break;
                }
                case "reload": {
                    Sleek.getInstance().getConfigManager().loadConfigs();
                    ChatUtil.log("Reloaded Configs");
                    break;
                }
                case "delete":
                case "remove": {
                    Sleek.getInstance().getConfigManager().removeConfig(args[1]);
                    ChatUtil.log("Removed Config " + args[1]);
                    break;
                }
                case "list": {
                    for (File file : Sleek.getInstance().getConfigManager().getDir().listFiles()) {
                        ChatUtil.log("- " + file.getName().replaceAll(".sleek", ""));
                    }
                    break;
                }
                case "teest": {
                    String[] data = Sleek.getInstance().getConfigManager().getConfigData(args[1]);
                    for (String dat : data) {
                        ChatUtil.log(dat);
                    }
                    break;
                }
                case "upload": {
                    String[] data = Sleek.getInstance().getConfigManager().getConfigData(args[1]);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("lastUpdate", data[2]);
                    params.put("name", data[0]);
                    params.put("author", data[1]);
                    Map<String, String> bParams = new HashMap<>();
                    bParams.put("data", data[3]);
                    try {

                        System.out.println(HttpUtil.postForm(HttpUtil.appendQueryParams("https://api.sleek.today/api/verifiedconfig", params), bParams, null));
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "online": {
                    switch (args[1].toLowerCase()) {
                        case "list": {
                            ChatUtil.log("nig");
                            String serv = HttpUtil.get("https://api.sleek.today/api/verifiedconfigs");
                            System.out.println(serv);
                            JsonObject element = new JsonParser().parse(serv).getAsJsonObject().get("configs").getAsJsonArray().get(0).getAsJsonObject();
                            if (element.isJsonArray()) {
                                JsonArray rr = element.getAsJsonArray();
                                rr.forEach(ele -> {
                                    JsonObject obj = ele.getAsJsonObject();
                                    ChatUtil.log(MessageFormat.format("Config \"{0}\" made by {1} was last updated on {2}", obj.get("name").getAsString(), obj.get("author").getAsString(), obj.get("lastUpdate").getAsString().split(" ")[1]));
                                });
                            }
                            break;
                        }
                        case "load": {
                            String serv = HttpUtil.get("https://api.sleek.today/api/verifiedconfigs");
                            JsonObject ar2 = new JsonParser().parse(serv).getAsJsonArray().get(0).getAsJsonObject();
                            if (!ar2.isJsonArray()) {
                                return;
                            }

                            ar2.getAsJsonArray().forEach(fig -> {
                                if (fig.getAsJsonObject().get("name").getAsString().equalsIgnoreCase(args[2])) {
                                    JsonArray arr = new JsonParser().parse(fig.getAsJsonObject().get("data").getAsString()).getAsJsonArray();
                                    arr.forEach(element -> {
                                        JsonObject obj = element.getAsJsonObject();
                                        String modName = obj.get("name").getAsString();
                                        Module m = Sleek.getInstance().getModuleManager().getModuleByName(modName);
                                        if (m != null) {
                                            m.load(obj, false);
                                        }
                                    });
                                }
                            });


                            break;
                        }
                    }
                    break;
                }
            }
        } catch (Throwable gnored) {
            gnored.printStackTrace();
            ChatUtil.log(".config save <configName>");
            ChatUtil.log(".config load <configName>");
            ChatUtil.log(".config remove <configName>");
            ChatUtil.log(".config online <list | load> [name]");
            ChatUtil.log(".config reload");
            ChatUtil.log(".config list");
        }
    }
}
