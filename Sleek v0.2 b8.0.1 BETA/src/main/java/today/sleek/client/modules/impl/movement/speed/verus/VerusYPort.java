package today.sleek.client.modules.impl.movement.speed.verus;

import today.sleek.base.event.impl.MoveEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;
import today.sleek.client.utils.player.PlayerUtil;

public class VerusYPort extends SpeedMode {

    public VerusYPort() {
        super("Verus (Port)");
    }

    @Override
    public void onMove(MoveEvent event) {
        if (!mc.thePlayer.isInLava() && !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder() && mc.thePlayer.ridingEntity == null) {
            if (mc.thePlayer.isMoving()) {
                mc.gameSettings.keyBindJump.pressed = false;
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    mc.thePlayer.motionY = 0.0;
                    PlayerUtil.strafe(0.61F);
                    event.setMotionY(0.41999998688698);
                }
                PlayerUtil.strafe();
            }
        }
    }
}
