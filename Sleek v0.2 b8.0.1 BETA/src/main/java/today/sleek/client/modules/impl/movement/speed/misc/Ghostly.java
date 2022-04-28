package today.sleek.client.modules.impl.movement.speed.misc;

import today.sleek.base.event.impl.MoveEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;

public class Ghostly extends SpeedMode {
    
    public Ghostly() {
        super("Ghostly");
    }

    @Override
    public void onMove(MoveEvent event) {


        double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
        double x = -Math.sin(yaw) * getSpeed().getSpeed().getValue();
        double z = Math.cos(yaw) * getSpeed().getSpeed().getValue();

        if (!mc.thePlayer.isMoving()) return;

        if (mc.thePlayer.ticksExisted % 5 == 0) {
            mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
        }


    }
}
