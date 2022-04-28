package today.sleek.client.utils.network;

import net.minecraft.network.Packet;
import today.sleek.client.utils.Util;
import today.sleek.client.utils.chat.ChatUtil;

public class PacketUtil extends Util {

    public static void sendPacket(Packet packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void sendPacket(int sendTimes, Packet packet) {
        for (int i = 0; i < sendTimes; i++) {
            mc.getNetHandler().addToSendQueue(packet);
        }
    }

    public static void sendPacketNoEvent(Packet packet) {
        mc.getNetHandler().addToSendQueueNoEvent(packet);
        ChatUtil.log("sent s packet " + packet.toString());
    }

    public static void sendPacketNoEvent(int sendTimes, Packet packet) {
        for (int i = 0; i < sendTimes; i++) {
            mc.getNetHandler().addToSendQueueNoEvent(packet);
        }
    }
}
