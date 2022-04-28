package today.sleek.client.modules.impl.combat;

import com.google.common.eventbus.Subscribe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.block.BlockUtil;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.math.MathUtil;
import today.sleek.client.utils.moshi.IPacketUtils;
import today.sleek.client.utils.network.PacketUtil;
import today.sleek.client.utils.network.TimedPacket;
import today.sleek.client.utils.rotations.AimUtil;
import today.sleek.client.utils.rotations.Rotation;

import java.util.LinkedList;

@ModuleData(
        name = "Velocity",
        category = ModuleCategory.COMBAT,
        description = "Allows you to modify your knockback"
)
public class Velocity extends Module implements IPacketUtils {

    private NumberValue<Double> v = new NumberValue<>("Vertical", this, 100.0, 0.0, 100.0, 1.0);
    private NumberValue<Double> h = new NumberValue<>("Horizontal", this, 100.0, 0.0, 100.0, 1.0);
    private ModeValue modeValue = new ModeValue("Mode", this, "Packet", "Teleport", "Push", "Delayed");
    private NumberValue<Integer> delay = new NumberValue<Integer>("Delay (MS)", this, 500, 0, 3000, 50,
            modeValue, "Delayed");
    public BooleanValue explotion = new BooleanValue("Explosion", this, true);
    private BooleanValue moveOnly = new BooleanValue("Only While Moving", this, false);

    private NumberValue chance = new NumberValue("Chance", this, 100, 0, 100, 1);

    // Handles delayed velocities
    LinkedList<TimedPacket> velocities = new LinkedList<>();
    LinkedList<TimedPacket> removeVelocities = new LinkedList<>();

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        try {
            if (modeValue.getValue().equalsIgnoreCase("Delayed")) {
                velocities.forEach(vel -> {
                    if (vel.postAddTime() > delay.getValue()) {
                        mc.thePlayer.sendQueue.handleEntityVelocity((S12PacketEntityVelocity) vel.getPacket());
                        removeVelocities.add(vel);
                    }
                });
            }
            velocities.removeIf(timedPacket -> removeVelocities.contains(timedPacket));
            removeVelocities.clear();
        } catch (Exception ev) {
            ev.printStackTrace();
        }
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null)
            return;

        if (moveOnly.getValue() && !mc.thePlayer.isMoving())
            return;


        if (chance.getValue().floatValue() != 100f && MathUtil.getRandomInRange(0, 100) > chance.getValue().floatValue())
            return;

        switch (modeValue.getValueAsString()) {
            case "Packet": {
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity packet = event.getPacket();
                    event.setCancelled(mc.theWorld != null && mc.thePlayer != null && mc.theWorld.getEntityByID(packet.getEntityID()) == mc.thePlayer);
                } else if (event.getPacket() instanceof S27PacketExplosion) {
                    if (explotion.getValue()) {
                        event.setCancelled(true);
                    }
                }
                break;
            }
            case "Teleport": {
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity packet = event.getPacket();
                    if (mc.theWorld != null && mc.thePlayer != null && mc.theWorld.getEntityByID(packet.getEntityID()) == mc.thePlayer) {
                        event.setCancelled(true);
                        PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, (mc.thePlayer.posY - mc.thePlayer.fallDistance - 0.21), mc.thePlayer.posZ, true));

                    }
                } else if (event.getPacket() instanceof S27PacketExplosion) {
                    if (explotion.getValue()) {
                        event.setCancelled(true);
                        PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.25232, mc.thePlayer.posZ, true));
                    }
                }
                break;
            }
            case "Push": {
                onSVelocity(event, p -> {
                    double pushH = h.getValue() / 100;
                    double pushV = v.getValue() / 100;

                    p.motionX *= pushH;
                    p.motionZ *= pushH;
                    p.motionY *= pushV;
                });

                break;
            }
            case "Delayed": {
                onSVelocity(event, p -> {
                    double pushH = h.getValue() / 100;
                    double pushV = v.getValue() / 100;

                    p.motionX *= pushH;
                    p.motionZ *= pushH;
                    p.motionY *= pushV;

                    velocities.add(create(p));
                    event.setCancelled(true);
                });
                break;
            }
        }

    }

    public boolean isVoidUnder(double x, double y, double z) {
        for (double i = mc.thePlayer.posY + 1; i > 0; i--) {
            Block below = BlockUtil.getBlockAt(new BlockPos(x, y - i, z));

            //if it's air this returns true.
            if (!(below instanceof BlockAir))
                return false;
        }

        return true;
    }

    @Override
    public String getSuffix() {
        return " " + modeValue.getValueAsString();
    }
}
