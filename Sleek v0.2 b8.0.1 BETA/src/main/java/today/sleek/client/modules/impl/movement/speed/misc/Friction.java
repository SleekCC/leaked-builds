package today.sleek.client.modules.impl.movement.speed.misc;

import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;
import today.sleek.client.utils.player.PlayerUtil;

public class Friction extends SpeedMode {

    public Friction() {
        super("Friction");
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.isMovingOnGround()) {
            mc.thePlayer.motionY = PlayerUtil.getMotion(0.42f);
        }
        PlayerUtil.setMotion(getSpeed().getSpeed().getValue());
        if (!mc.thePlayer.onGround) {
            mc.thePlayer.motionX *= 0.9;
            mc.thePlayer.motionZ *= 0.9;
        }
    }
}
