package today.sleek.base.event.impl;

import net.minecraft.network.Packet;
import today.sleek.base.event.Event;
import today.sleek.base.event.PacketDirection;

public class PacketEvent extends Event {

    private final PacketDirection packetDirection;
    private Packet packet;

    public PacketEvent(PacketDirection packetDirection, Packet packet) {
        this.packetDirection = packetDirection;
        this.packet = packet;
    }

    public PacketDirection getPacketDirection() {
        return packetDirection;
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet> T getPacket() {
        return (T) packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public Class getPacketClass() {
        return packet.getClass();
    }

    public boolean isSending() {
        return this.packetDirection == PacketDirection.INBOUND;
    }
}