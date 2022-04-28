package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.server.S02PacketChat;
import org.apache.commons.lang3.RandomUtils;
import today.sleek.Sleek;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.chat.ChatUtil;

import java.util.ArrayList;

@ModuleData(
        name = "Kill Insults",
        category = ModuleCategory.PLAYER,
        description = "Test Module..."
)
public class KillSults extends Module {

    private final ModeValue modeValue = new ModeValue("Mode", this, "BlocksMC");

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat p = event.getPacket();
            String msg = p.getChatComponent().getFormattedText();

            switch (modeValue.getValue()) {
                case "BlocksMC": {
                    if (msg.contains("for killing")) {
                        sendKillSult(msg.split(" ")[11]);
                    }
                    break;
                }
            }
        }
    }


    public void sendKillSult(String name) {
        ArrayList<String> msgs = Sleek.getInstance().getKillsultManager().getKillSults();

        if (msgs.isEmpty()) {
            ChatUtil.log("§c§lError! §fFailed to send killsult due to you having none set up...");
            return;
        }

        String toSend = msgs.get(RandomUtils.nextInt(0, msgs.size() - 1))
                        .replaceAll("%killed%", name)
                        .replaceAll("%discord%", Sleek.getInstance().getDiscordTag())
                        .replaceAll("%username%", Sleek.getInstance().getUsername())
                        .replaceAll("%uid%", Sleek.getInstance().getUid());
        mc.thePlayer.sendChatMessage(toSend);
    }
}
