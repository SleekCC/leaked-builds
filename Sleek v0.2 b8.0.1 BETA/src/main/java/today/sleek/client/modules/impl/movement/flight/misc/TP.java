package today.sleek.client.modules.impl.movement.flight.misc;

import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.flight.FlightMode;
import today.sleek.client.utils.player.PlayerUtil;

public class TP extends FlightMode {
    public TP() {
        super("Teleport");
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        mc.thePlayer.motionY = 0;

        if (mc.thePlayer.ticksExisted % 5 == 0) {
            double[] negroids = PlayerUtil.teleportForward(getFlight().getSpeed().getValue());
            if (!mc.thePlayer.isMoving()) {
                return;
            }
            mc.thePlayer.setPosition(mc.thePlayer.posX + negroids[0], mc.thePlayer.posY, mc.thePlayer.posZ + negroids[1]);
        }
    }
}
