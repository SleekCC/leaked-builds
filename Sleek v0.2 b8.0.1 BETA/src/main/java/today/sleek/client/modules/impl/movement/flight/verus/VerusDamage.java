package today.sleek.client.modules.impl.movement.flight.verus;

import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;
import today.sleek.base.event.impl.BlockCollisionEvent;
import today.sleek.base.event.impl.MoveEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.flight.FlightMode;
import today.sleek.client.utils.player.PlayerUtil;
import today.sleek.client.utils.player.TimerUtil;

public class VerusDamage extends FlightMode {

    private double veroos = 2.5;

    public VerusDamage() {
        super("Verus Damage");
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        


        if (mc.thePlayer.hurtTime > 8) {
            veroos = getFlight().getSpeed().getValue();
        }

        if (mc.thePlayer.hurtResistantTime < 2) {
            veroos = 0.22f;
        }

        /* else if (boosted && mc.thePlayer.hurtResistantTime < 2) {
            veroos = 0.22f;
        } else if (boosted) {
            veroos -= 0.01;
        }*/


    }

    @Override
    public void onMove(MoveEvent event) {
       // ChatUtil.log(veroos + " ");
        PlayerUtil.setMotion(event, veroos);
    }

    @Override
    public void onCollide(BlockCollisionEvent event) {
        if (event.getBlock() instanceof BlockAir) {
            if (mc.thePlayer.isSneaking())
                return;
            double x = event.getX();
            double y = event.getY();
            double z = event.getZ();
            if (y < mc.thePlayer.posY) {
                event.setAxisAlignedBB(AxisAlignedBB.fromBounds(-5, -1, -5, 5, 1.0F, 5).offset(x, y, z));
            }
        }
    }

    @Override
    public void onEnable() {
        if (!mc.thePlayer.onGround) {
            getFlight().toggle();
            return;
        }
        veroos = 0.22;
        TimerUtil.setTimer(0.8f);
        PlayerUtil.damageVerus();
    }
}
