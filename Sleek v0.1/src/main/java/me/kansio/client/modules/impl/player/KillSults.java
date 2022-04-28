package me.kansio.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import me.kansio.client.Client;
import me.kansio.client.event.impl.PacketEvent;
import me.kansio.client.modules.api.ModuleCategory;
import me.kansio.client.modules.api.ModuleData;
import me.kansio.client.modules.impl.Module;
import me.kansio.client.utils.chat.ChatUtil;
import me.kansio.client.value.value.ModeValue;
import net.minecraft.network.play.server.S02PacketChat;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        ArrayList<String> msgs = Client.getInstance().getKillsultManager().getKillSults();

        if (msgs.isEmpty()) {
            ChatUtil.log("§c§lError! §fFailed to send killsult due to you having none set up...");
            return;
        }

        String toSend = msgs.get(RandomUtils.nextInt(0, msgs.size() - 1))
                        .replaceAll("%killed%", name)
                        .replaceAll("%discord%", Client.getInstance().getDiscordTag())
                        .replaceAll("%username%", Client.getInstance().getUsername())
                        .replaceAll("%uid%", Client.getInstance().getUid());
        mc.thePlayer.sendChatMessage(toSend);
    }
}
