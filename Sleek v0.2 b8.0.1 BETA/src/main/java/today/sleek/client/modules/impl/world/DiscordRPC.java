package today.sleek.client.modules.impl.world;

import com.google.common.eventbus.Subscribe;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import lombok.SneakyThrows;
import today.sleek.Sleek;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.server.ServerUtil;

import java.time.OffsetDateTime;
import java.util.Timer;
import java.util.TimerTask;

@ModuleData(
        name = "Discord RPC",
        category = ModuleCategory.WORLD,
        description = "Starts the Discord RPC"
)


public class DiscordRPC extends Module {

    private IPCClient client = new IPCClient(921136646253056012L);
    private boolean active = false;

    public void onEnable() {
        active = true;
        try {
            final OffsetDateTime[] time = {OffsetDateTime.now()};
            final String[] lastServer = {ServerUtil.getServer()};
            client.setListener(new IPCListener() {
                @Override
                public void onReady(IPCClient client) {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @SneakyThrows
                        @Override
                        public void run() {
                            if (!active) {
                                timer.cancel();
                                return;
                            }

                            //update the time whenever they switch servers
                            if (!lastServer[0].equalsIgnoreCase(ServerUtil.getServer())) {
                                time[0] = OffsetDateTime.now();
                                lastServer[0] = ServerUtil.getServer();
                            }

                            //create rpc
                            RichPresence.Builder builder = new RichPresence.Builder();

                            builder.setState("UID: " + Sleek.getInstance().getUid())
                                    .setDetails(ServerUtil.getServer())
                                    .setStartTimestamp(time[0])
                                    .setLargeImage("icon", "sleek.today");
//                                    .setSmallImage("ptb-small", "Discord PTB");
                            //update it
                            client.sendRichPresence(builder.build());
                        }
                    }, 0, 5000);
                }
            });

            client.connect();
        } catch (Exception e) {
            System.out.println("Error: Discord RPC!");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        client.close();
        active = false;
    }
}
