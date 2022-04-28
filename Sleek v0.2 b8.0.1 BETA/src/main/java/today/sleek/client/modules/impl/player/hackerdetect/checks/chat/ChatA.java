package today.sleek.client.modules.impl.player.hackerdetect.checks.chat;

import net.minecraft.network.play.server.S02PacketChat;
import today.sleek.Sleek;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.client.modules.impl.player.hackerdetect.checks.Check;

import java.util.Arrays;
import java.util.List;

public class ChatA extends Check {

    private List<String> flagged = Arrays.asList("[FDPClient]");

    @Override
    public String name() {
        return "Chat (Type A)";
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S02PacketChat) {
            String msg = ((S02PacketChat) event.getPacket()).getChatComponent().getFormattedText();
            for (String flagged : flagged) {
                if (msg.contains(flagged)) {
                    String sender = msg.split(" ")[0].replace("§r§9", "").replace("§r§8", "");

                    if (!Sleek.getInstance().getTargetManager().isTarget(sender)) {
                        Sleek.getInstance().getTargetManager().getTarget().add(sender);
                    }
                    return;
                }
            }
        }
    }
}
