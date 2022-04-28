package today.sleek.client.modules.impl.movement.speed.misc;

import today.sleek.base.event.impl.MoveEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;
import today.sleek.client.utils.player.PlayerUtil;

public class Retard extends SpeedMode {

    public Retard() {
        super("Retard");
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.onGround) {
            getSpeed().getHDist().set(getSpeed().getHDist().get() + getSpeed().getSpeed().getValue());
        }

        if (mc.thePlayer.isCollidedHorizontally) {
            getSpeed().getHDist().set(0);
        }
    }

    @Override
    public void onMove(MoveEvent event) {
        if (mc.thePlayer.isMovingOnGround()) {
            event.setMotionY(mc.thePlayer.motionY = PlayerUtil.getMotion(0.42f));
        }

        PlayerUtil.setMotion(getSpeed().handleFriction(getSpeed().getHDist()));
    }
}
