package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import today.sleek.base.event.impl.ChatEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.client.irc.IRCClient;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.math.Stopwatch;

import java.net.URISyntaxException;

@ModuleData(
        name = "IRC",
        category = ModuleCategory.PLAYER,
        description = "Let's you chat with other client users"
)
public class IRC extends Module {

    public IRC() {
        super("IRC", ModuleCategory.PLAYER);
    }

    private IRCClient client;


    public void onEnable() {

        try {
            client = new IRCClient();
            client.connectBlocking();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {
        try {
            client.closeBlocking();
            client = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onChat(ChatEvent event) {
        String message = event.getMessage();

        if (message.startsWith("-") || message.startsWith("- ")) {
            event.setCancelled(true);
            client.sendMessage(event.getMessage().replace("-", ""));
        }
    }
}
