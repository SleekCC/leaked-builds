package today.sleek.client.modules.impl.movement.speed.watchdog;

import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import today.sleek.base.event.impl.MoveEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;

public class Test extends SpeedMode {

    private double moveSpeed;
    private double lastDist;
    private int stage;

    public Test() {
        super("Watchdog (Test)");
    }

    @Override
    public void onEnable() {
        mc.timer.timerSpeed = 1.4f;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onMove(MoveEvent event) {
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
            if (mc.thePlayer.onGround) {
                float f = mc.thePlayer.rotationYaw * 0.017453292F;
                if (!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    mc.thePlayer.jump();
                } else {
                    mc.thePlayer.motionX -= MathHelper.sin(f) * 0.25F;
                    mc.thePlayer.motionZ += MathHelper.cos(f) * 0.25F;
                }
                mc.timer.timerSpeed = 2.5f;
                mc.thePlayer.motionY = 0.411;
            } else {
                if (mc.thePlayer.ticksExisted % 12 == 0) {
                    mc.timer.timerSpeed = 1.6f;
                } else
                mc.timer.timerSpeed = 1.4f;
            }
        }
    }
}
