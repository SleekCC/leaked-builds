package today.sleek.client.commands.impl;

import today.sleek.Sleek;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.utils.chat.ChatUtil;

@CommandData(
        name = "focus",
        description = "Focuses a mentioned player"
)
public class FocusCommand extends Command {

    @Override
    public void run(String[] args) {
        if (args.length > 0) {
            Sleek.getInstance().getTargetManager().getTarget().add(args[0]);
            ChatUtil.log("Added them as a target.");
        }
    }
}
