package today.sleek.client.modules.impl.movement.speed.watchdog;

import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import today.sleek.base.event.impl.MoveEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;
import today.sleek.client.utils.player.PlayerUtil;

import static today.sleek.client.utils.player.PlayerUtil.getDirection;


public class Bhop extends SpeedMode {
    int ticks = 0;
    public Bhop() {
        super("Watchdog");
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
            float f = (float) (getDirection() / 180 * Math.PI);
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
                ticks = 0;
                if (!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    if (mc.thePlayer.hurtTime > 1) {
                        mc.thePlayer.motionX -= MathHelper.sin(f) * 0.365F;
                        mc.thePlayer.motionZ += MathHelper.cos(f) * 0.365F;
                    } else {
                        mc.thePlayer.motionX -= MathHelper.sin(f) * 0.015F;
                        mc.thePlayer.motionZ += MathHelper.cos(f) * 0.015F;
                    }
                } else {
                    if (mc.thePlayer.hurtTime > 1) {
                        mc.thePlayer.motionX -= MathHelper.sin(f) * 0.225F;
                        mc.thePlayer.motionZ += MathHelper.cos(f) * 0.225F;
                    }
                }
                PlayerUtil.strafe();
            } else {
                ticks++;
                final double yaw = getDirection() / 180 * Math.PI;
                if(ticks == 3 || ticks == 4){
                    mc.thePlayer.motionY -= 0.03;
                }
                if(mc.thePlayer.hurtTime != 0 && PlayerUtil.getSpeed1() < 0.425) {
                    mc.thePlayer.motionX -= (mc.thePlayer.motionX - (-Math.sin(yaw) * PlayerUtil.getSpeed1()*1.015))/2;
                    mc.thePlayer.motionZ -= (mc.thePlayer.motionZ - (Math.cos(yaw) * PlayerUtil.getSpeed1()*1.015))/2;
                }
                event.setRotationYaw((float) getDirection());
                mc.thePlayer.motionX -= (mc.thePlayer.motionX - (-Math.sin(yaw) * PlayerUtil.getSpeed1()))/3.34;
                mc.thePlayer.motionZ -= (mc.thePlayer.motionZ - (Math.cos(yaw) * PlayerUtil.getSpeed1()))/3.34;
                if(mc.thePlayer.isPotionActive(Potion.moveSpeed)){
                    mc.thePlayer.motionX -= (mc.thePlayer.motionX - (-Math.sin(yaw) * PlayerUtil.getSpeed1()))/4;
                    mc.thePlayer.motionZ -= (mc.thePlayer.motionZ - (Math.cos(yaw) * PlayerUtil.getSpeed1()))/4;
                }
            }
            mc.thePlayer.setSprinting(true);
        }
    }

    @Override
    public void onMove(MoveEvent event) {
    }
}