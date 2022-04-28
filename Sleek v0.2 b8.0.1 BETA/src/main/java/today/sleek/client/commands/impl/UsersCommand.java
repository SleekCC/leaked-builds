package today.sleek.client.commands.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import today.sleek.Sleek;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.network.HttpUtil;

import java.io.IOException;
import java.text.MessageFormat;

@CommandData(
        name = "users",
        description = "Lists the current online users"
)
public class UsersCommand extends Command {

    @Override
    public void run(String[] args) {

        try {
            JsonElement node = new JsonParser().parse(HttpUtil.get("https://api.sleek.today/api/getclientplayers"));


            if (node.isJsonArray()) {
                Sleek.getInstance().getUsers().clear();
                for (JsonElement ele : node.getAsJsonArray()) {
                    JsonObject obj = ele.getAsJsonObject();

                    Sleek.getInstance().getUsers().put(obj.get("ign").getAsString(), obj.get("name").getAsString());

                    ChatUtil.log(MessageFormat.format(ChatUtil.translateColorCodes("&b{0} [{1}] &7({2})&b - {3}"), obj.get("name").getAsString(), obj.get("uid").getAsString(), obj.get("ign").getAsString(), obj.get("serverIP").getAsString()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
