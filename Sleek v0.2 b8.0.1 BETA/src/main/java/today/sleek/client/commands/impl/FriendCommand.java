package today.sleek.client.commands.impl;

import today.sleek.Sleek;
import today.sleek.client.commands.Command;
import today.sleek.client.commands.CommandData;
import today.sleek.client.friend.Friend;
import today.sleek.client.utils.chat.ChatUtil;

import java.text.MessageFormat;
@CommandData(
        name = "friend",
        description = "Handles friends"
)
public class FriendCommand extends Command {

    public void run(String[] args) {
        if (args.length > 1) {
            switch (args[0].toLowerCase()) {
                case "add": {
                    if (args.length == 3) {
                        Sleek.getInstance().getFriendManager().addFriend(new Friend(args[1], args[2]));
                        ChatUtil.log(MessageFormat.format("Added friend {0} as {1}", args[1], args[2]));
                    } else {
                        Sleek.getInstance().getFriendManager().addFriend(new Friend(args[1], args[1]));
                        ChatUtil.log(MessageFormat.format("Added friend {0}", args[1]));
                    }
                    break;
                }

                case "del":
                case "delete":
                case "remove": {
                    Sleek.getInstance().getFriendManager().removeFriend(args[1]);
                    ChatUtil.log(MessageFormat.format("Removed friend {0}", args[1]));
                    break;
                }
            }
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "clear": {
                    Sleek.getInstance().getFriendManager().clearFriends();
                    ChatUtil.log(MessageFormat.format("You now have {0} friends", Sleek.getInstance().getFriendManager().getFriends().size()));
                    break;
                }
                case "list": {
                    Sleek.getInstance().getFriendManager().getFriends().forEach(friend -> ChatUtil.log(MessageFormat.format(" - {0} \2477({1})", friend.getName(), friend.getDisplayName())));
                    break;
                }
            }
        }
    }
}
