package today.sleek.client.modules.impl.movement.speed.viper;

import today.sleek.base.event.impl.MoveEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;
import today.sleek.client.utils.player.PlayerUtil;
import today.sleek.client.utils.player.TimerUtil;


public class ViperGround extends SpeedMode {
    public ViperGround() {
        super("Viper Ground");
    }

    @Override
    public void onMove(MoveEvent event) {
        if (!mc.thePlayer.isMovingOnGround()) {
            TimerUtil.setTimer(1f);
            mc.thePlayer.motionY = - 5f;
            return;
        }


        if (mc.thePlayer.isMoving()) {
            TimerUtil.setTimer(0.3f);
            for (int i = 0; i < 17; ++i) {
                PlayerUtil.TP(event, 0.22, 0);
            }
        }
    }
}
