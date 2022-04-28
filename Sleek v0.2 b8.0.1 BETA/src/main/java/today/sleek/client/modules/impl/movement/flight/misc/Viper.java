package today.sleek.client.modules.impl.movement.flight.misc;

import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;
import today.sleek.base.event.impl.BlockCollisionEvent;
import today.sleek.base.event.impl.MoveEvent;
import today.sleek.client.modules.impl.movement.flight.FlightMode;
import today.sleek.client.utils.player.PlayerUtil;
import today.sleek.client.utils.player.TimerUtil;

public class Viper extends FlightMode {

    public Viper() {
        super("Viper");
    }

    @Override
    public void onMove(MoveEvent event) {
        if (!mc.thePlayer.isMovingOnGround()) {
            TimerUtil.Reset();
            return;
        }

        if (mc.thePlayer.isMoving()) {
            TimerUtil.setTimer(0.3f);
            for (int i = 0; i < 17; ++i) {
                PlayerUtil.TPGROUND(event, 0.28, 0);
            }
        }
    }

    @Override
    public void onCollide(BlockCollisionEvent event) {
        if (event.getBlock() instanceof BlockAir) {
            if (mc.thePlayer.isSneaking())
                return;
            double x = event.getX();
            double y = event.getY();
            double z = event.getZ();
            if (y < mc.thePlayer.posY) {
                event.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5, -1, -5, 5, 1.0F, 5).offset(x, y, z));
            }
        }
    }
}
