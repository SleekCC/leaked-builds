package today.sleek.client.modules.impl.movement.flight.blox;

import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;
import today.sleek.base.event.impl.BlockCollisionEvent;
import today.sleek.base.event.impl.MoveEvent;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.flight.FlightMode;
import today.sleek.client.utils.player.PlayerUtil;

public class BlocksMC extends FlightMode {


    public BlocksMC() {
        super("BlocksMC");
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        super.onUpdate(event);
    }

    @Override
    public void onMove(MoveEvent event) {
        if (mc.thePlayer.ticksExisted % 15 == 0) {
            mc.thePlayer.motionY = 0.0;
            event.setMotionY(0.41999998688698);
            PlayerUtil.setMotion(getFlight().getSpeed().getValue().floatValue());
        }
    }

    @Override
    public void onPacket(PacketEvent event) {
        super.onPacket(event);
    }

    @Override
    public void onCollide(BlockCollisionEvent event) {
        if (mc.thePlayer.ticksExisted % 15 != 0) {
            if (event.getBlock() instanceof BlockAir) {
                if (mc.thePlayer.isSneaking())
                    return;
                double x = event.getX();
                double y = event.getY();
                double z = event.getZ();
                event.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5, -1, -5, 5, 1.0F, 5).offset(x, y, z));

            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
