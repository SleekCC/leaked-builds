package today.sleek.client.modules.impl.combat;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import today.sleek.Sleek;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.modules.impl.movement.Flight;
import today.sleek.client.utils.math.MathUtil;
import today.sleek.client.utils.network.PacketUtil;

@ModuleData(
        name = "Criticals",
        category = ModuleCategory.COMBAT,
        description = "Automatically deals critical hits"
)
public class Criticals extends Module {

    private ModeValue mode = new ModeValue("Mode", this, "Packet", "Verus", "MiniJump", "Jump", "Test");
    private final BooleanValue c06 = new BooleanValue("C06", this, true);

    public final double[] packetValues = new double[]{0.0625D, 0.0D, 0.05D, 0.0D};

    @Subscribe
    public void onPacket(PacketEvent event) {
        Flight flight = (Flight) Sleek.getInstance().getModuleManager().getModuleByName("Flight");

        if (flight.isToggled())
            return;

        if (event.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity) event.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK) {
            final C02PacketUseEntity packetUseEntity = event.getPacket();
            final Entity entity = packetUseEntity.getEntityFromWorld(mc.theWorld);
            if (mc.thePlayer.onGround && entity.hurtResistantTime != -1 && !Sleek.getInstance().getModuleManager().getModuleByName("Speed").isToggled()) {
                doCritical();
                entity.hurtResistantTime = -1;
            }
        }
    }

    public void doCritical() {
        switch (mode.getValueAsString()) {
            case "Packet": {
                for (double d : packetValues) {
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + d, mc.thePlayer.posZ, false));
                }
                break;
            }
            case "Test": {
                C03PacketPlayer.C04PacketPlayerPosition c04 = new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false);
                c04.y += 1.0E-9;
                PacketUtil.sendPacketNoEvent(c04);
                break;
            }
            case "Verus": {
                if (!mc.thePlayer.onGround) {
                    return;
                }

                sendPacket(0, 0.11, 0, false);
                sendPacket(0, 0.1100013579, 0, false);
                sendPacket(0, 0.0000013579, 0, false);
                break;
            }
            case "MiniJump": {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = MathUtil.getRandomInRange(0.075f,0.1875f);
                }
                break;
            }
            case "Jump": {
                if (!mc.thePlayer.onGround) {
                    return;
                }

                mc.thePlayer.jump();
                break;
            }
        }
    }

    void sendPacket(double xOffset, double yOffset, double zOffset, boolean ground) {
        double x = mc.thePlayer.posX + xOffset;
        double y = mc.thePlayer.posY + yOffset;
        double z = mc.thePlayer.posZ + zOffset;
        if (c06.getValue()) {
            mc.getNetHandler().addToSendQueue(new
                    C03PacketPlayer.C06PacketPlayerPosLook(
                            x,
                            y,
                            z,
                            mc.thePlayer.rotationYaw,
                            mc.thePlayer.rotationPitch,
                            ground
                    )
            );
        } else {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, ground));
        }
    }

    @Override
    public String getSuffix() {
        return " " + mode.getValueAsString();
    }
}
