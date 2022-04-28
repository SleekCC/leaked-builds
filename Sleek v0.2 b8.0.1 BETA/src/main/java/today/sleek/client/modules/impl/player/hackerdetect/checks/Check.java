package today.sleek.client.modules.impl.player.hackerdetect.checks;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import today.sleek.Sleek;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.client.modules.impl.player.HackerDetect;
import today.sleek.client.utils.chat.ChatUtil;

public abstract class Check {

    public abstract String name();

    protected HackerDetect detect = HackerDetect.getInstance();

    protected static Minecraft mc = Minecraft.getMinecraft();

    public void onUpdate() {

    }

    public void onBlocksMCGameStartTick() {


    }
    public void onPacket(PacketEvent event) {

    }

    public void flag(EntityPlayer player) {
        HackerDetect hack = (HackerDetect) Sleek.getInstance().getModuleManager().getModuleByName("HackerDetect");
        switch (hack.theme.getValue()) {
            case "Sleek":
                ChatUtil.logSleekCheater(player.getName(), name());
                break;
            case "Verus":
                ChatUtil.logVerusCheater(player.getName(), name(), "2" );
                break;
        }
    }

}
