package me.kansio.client.commands.impl;

import me.kansio.client.Client;
import me.kansio.client.commands.Command;
import me.kansio.client.commands.CommandData;
import me.kansio.client.utils.chat.ChatUtil;

/**
 * @author Kansio
 */
@CommandData(
        name = "killsults",
        description = "Displays all the killsults"
)
public class KillsultsCommand extends Command {

    @Override
    public void run(String[] args) {
        for (String ks : Client.getInstance().getKillsultManager().getKillSults()) {
            ChatUtil.log(ks);
        }
    }
}
