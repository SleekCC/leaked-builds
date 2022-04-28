package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.network.PacketUtil;

@ModuleData(
        name = "No Rotate",
        category = ModuleCategory.PLAYER,
        description = "Prevents the server from setting your head pos"
)
public class NoRotate extends Module {

    private ModeValue mode = new ModeValue("Mode", this, "Cancel", "Packet", "Spoof");

    @Subscribe
    public void onSendPacket(PacketEvent event) {
        switch (mode.getValue()) {
            case "Cancel":
                if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                    S08PacketPlayerPosLook packet = event.getPacket();
                    if (mc.thePlayer != null && mc.theWorld != null && mc.thePlayer.rotationYaw != -180 && mc.thePlayer.rotationPitch != 0) {
                        event.setCancelled(true);
                    }
                }
                break;
            case "Packet":
                if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                    S08PacketPlayerPosLook packet = event.getPacket();
                    if (mc.thePlayer != null && mc.theWorld != null && mc.thePlayer.rotationYaw != -180 && mc.thePlayer.rotationPitch != 0) {
                        packet.yaw = mc.thePlayer.rotationYaw;
                        packet.pitch = mc.thePlayer.rotationPitch;
                    }
                }
                break;
            case "Spoof":
                if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                    S08PacketPlayerPosLook packet = event.getPacket();
                    if (mc.thePlayer != null && mc.theWorld != null && mc.thePlayer.rotationYaw != -180 && mc.thePlayer.rotationPitch != 0) {
                        packet.yaw = mc.thePlayer.rotationYaw;
                        packet.pitch = mc.thePlayer.rotationPitch;

                        // send a c03 with the values of the packet,
                        // since the server is expecting you to send one,
                        // and some anticheats can use this to detect if you're using a norotate.
                        // :troll:
                        PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, packet.getYaw(), packet.getPitch(), false));
                    }
                }
                break;
        }
    }
}
