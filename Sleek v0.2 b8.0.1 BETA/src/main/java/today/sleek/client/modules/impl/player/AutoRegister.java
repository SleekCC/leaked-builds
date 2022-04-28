package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.network.PacketUtil;

@ModuleData(
        name = "Auto Register",
        description = "Automatically registers you",
        category = ModuleCategory.PLAYER
)
public class AutoRegister extends Module {

    @Subscribe
    public void onChat(PacketEvent event) {
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = event.getPacket();

            if (packet.getChatComponent().getUnformattedText().contains("/register <password> <repeat password>")) {
                PacketUtil.sendPacketNoEvent(new C01PacketChatMessage("/register SleekCheat SleekCheat"));
                ChatUtil.log("Automatically registered with password 'SleekCheat'");
            }
        }
    }

}
