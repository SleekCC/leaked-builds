package today.sleek.client.utils.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import today.sleek.client.utils.chat.ChatUtil;

public class PacketSleepThread extends Thread {

    public PacketSleepThread(Packet packet, long delay) {
        super(() -> {
            sleep_ms(delay);
            if (Minecraft.getMinecraft().thePlayer != null) {
                PacketUtil.sendPacketNoEvent(packet);
                ChatUtil.log("sent");
            }
        });
    }

    static void sleep_ms(long delay) {
        try {
            sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void delayPacket(Packet packet, long delay) {
        new PacketSleepThread(packet, delay).start();
    }
}