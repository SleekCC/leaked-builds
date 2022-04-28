package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.network.PacketUtil;

/**
 * @author Kansio
 */
@ModuleData(
        name = "Test",
        description = "test mod",
        category = ModuleCategory.PLAYER
)
public class Test extends Module {

    private static Entity riding = null;

    @Override
    public void onEnable() {

        if (mc.thePlayer == null) {
            toggle();
            return;
        }

        if (!mc.thePlayer.isRiding()) {
            toggle();
            return;
        }

        riding = mc.thePlayer.ridingEntity;

        mc.theWorld.removeEntity(riding);
        mc.thePlayer.dismountEntity(riding);
        mc.thePlayer.setPosition(riding.posX, riding.posY, riding.posZ);
    }

    @Override
    public void onDisable() {
        if (riding != null && !riding.isDead) {
            mc.theWorld.spawnEntityInWorld(riding);
            mc.thePlayer.ridingEntity = riding;
            mc.thePlayer.updateRiderPosition();
        }

        mc.thePlayer.capabilities.isFlying = false;
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = event.getPacket();
            PacketUtil.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, ((S08PacketPlayerPosLook) event.getPacket()).getYaw(), ((S08PacketPlayerPosLook) event.getPacket()).pitch, true));
            event.setCancelled(true);
        } else if (event.getPacket() instanceof C03PacketPlayer) {
            mc.getNetHandler().getNetworkManager().sendPacket(new C0CPacketInput(mc.thePlayer.moveStrafing, mc.thePlayer.moveForward, false, false));
        }
        else if (event.getPacket() instanceof S00PacketDisconnect) {
            toggle();
        }
    }
    
    
    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {

            if (riding == null)
                return;

            if (mc.thePlayer.isRiding())
                return;

            mc.thePlayer.onGround = true;

            riding.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            riding.worldObj.spawnEntityInWorld(riding);

            //PacketUtil.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
            //mc.getNetHandler().getNetworkManager().sendPacket(new C0CPacketInput(mc.thePlayer.moveStrafing, mc.thePlayer.moveForward, false, false));
        }
    }
}
