package today.sleek.client.modules.impl.movement.flight.misc;

import today.sleek.base.event.impl.MoveEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.flight.FlightMode;
import today.sleek.client.utils.player.PlayerUtil;

public class Matrix extends FlightMode {

    public Matrix() {
        super("Matrix");
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.ticksExisted % 4 == 1) {
            mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? 0.98 : mc.thePlayer.movementInput.sneak ? -0.98 : .0784;
            PlayerUtil.setMotion(PlayerUtil.isBlockUnder() ? getFlight().getSpeed().getValue() : 0.98);
        }
        if (mc.thePlayer.ticksExisted % 4 == 0) {
            PlayerUtil.setMotion(0);
            mc.thePlayer.motionY = 0;
        }
    }

    @Override
    public void onMove(MoveEvent event) {
        super.onMove(event);
    }
}
