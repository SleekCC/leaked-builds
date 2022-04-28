package today.sleek.client.commands.impl;

import today.sleek.Sleek;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.utils.chat.ChatUtil;

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
        for (String ks : Sleek.getInstance().getKillsultManager().getKillSults()) {
            ChatUtil.log(ks);
        }
    }
}
