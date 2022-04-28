package today.sleek.client.modules.impl.movement.speed.verus;

import org.lwjgl.input.Keyboard;
import today.sleek.base.event.impl.MoveEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.player.PlayerUtil;

/**
 * @author Kansio
 */
public class VerusLowHop extends SpeedMode {

    public VerusLowHop() {
        super("Verus (Low)");
    }

    @Override
    public void onMove(MoveEvent event) {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {

            if (!mc.thePlayer.isInLava() && !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder() && mc.thePlayer.ridingEntity == null) {
                if (mc.thePlayer.isMoving()) {
                    mc.gameSettings.keyBindJump.pressed = false;
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        mc.thePlayer.motionY = 0.0;
                        PlayerUtil.setMotion(event, 0.61F + (mc.thePlayer.hurtTime / 22.423));
                        event.setMotionY(0.41999998688698);
                    }
                    PlayerUtil.setMotion(event, PlayerUtil.getVerusBaseSpeed() + (mc.thePlayer.hurtTime / 22.423));
                }
            }

            mc.thePlayer.posY = 0;
        } else {
            if (!mc.thePlayer.isInLava() && !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder() && mc.thePlayer.ridingEntity == null) {
                if (mc.thePlayer.isMoving()) {
                    mc.gameSettings.keyBindJump.pressed = false;
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        PlayerUtil.setMotion(event, 0.61F + (mc.thePlayer.hurtTime / 22.423));
                        event.setMotionY(0.41999998688698);
                    }
                    PlayerUtil.setMotion(event, PlayerUtil.getVerusBaseSpeed() + (mc.thePlayer.hurtTime / 22.423));

                }
            }
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (!Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()))
        mc.thePlayer.posY = Math.round(mc.thePlayer.posY);
    }
}
