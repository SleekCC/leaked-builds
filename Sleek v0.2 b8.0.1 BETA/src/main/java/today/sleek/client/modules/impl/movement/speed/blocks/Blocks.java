package today.sleek.client.modules.impl.movement.speed.blocks;

import net.minecraft.potion.Potion;
import today.sleek.base.event.impl.MoveEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;
import today.sleek.client.utils.player.PlayerUtil;
import today.sleek.client.utils.player.TimerUtil;

/**
 * @author Kansio
 */
public class Blocks extends SpeedMode {

    public Blocks() {
        super("BlocksMC");
    }

    @Override
    public void onEnable() {
        TimerUtil.setTimer(getSpeed().getTimer().getValue());
    }

    @Override
    public void onMove(MoveEvent event) {
        if (mc.thePlayer.isMoving()) {
            float sped2 = (float) (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.365 : 0.355);

            if (mc.thePlayer.onGround) {
                event.setMotionY(mc.thePlayer.motionY = PlayerUtil.getMotion(0.42f));
            }

            if (mc.thePlayer.hurtTime >= 1) {
                sped2 = getSpeed().getSpeed().getValue().floatValue();
            }

            PlayerUtil.setMotion(event, sped2);
        }
    }
}
