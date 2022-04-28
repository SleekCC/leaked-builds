package today.sleek.client.utils.network;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import today.sleek.client.utils.Util;

/**
 * @author Moshi
 */
public class TimedPacket extends Util {

    private Packet<?> packet;
    private long time;

    public TimedPacket(Packet<?> packet, long time) {
        this.time = time;
        this.packet = packet;
    }

    public long postAddTime() {
        return System.currentTimeMillis() - time;
    }

    public void send() {
        PacketUtil.sendPacket(this.getPacket());
    }

    public void sendSilent() {
        if (this.getPacket() instanceof S08PacketPlayerPosLook) {
            mc.thePlayer.sendQueue.handlePlayerPosLook((S08PacketPlayerPosLook) this.getPacket());
            return;
        }
        PacketUtil.sendPacketNoEvent(this.getPacket());
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }
}