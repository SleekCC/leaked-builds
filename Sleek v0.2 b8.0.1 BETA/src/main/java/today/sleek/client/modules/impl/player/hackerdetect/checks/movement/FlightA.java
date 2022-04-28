package today.sleek.client.modules.impl.player.hackerdetect.checks.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import today.sleek.Sleek;
import today.sleek.client.modules.impl.player.hackerdetect.checks.Check;

import java.util.HashMap;

public class FlightA extends Check {

    private Minecraft mc = Minecraft.getMinecraft();

    private HashMap<EntityPlayer, Integer> airTicks = new HashMap<>();

    @Override
    public String name() {
        return "Flight (Check A)";
    }

    @Override
    public void onUpdate() {
        if (mc.thePlayer.ticksExisted < 5) {
            airTicks.clear();
        }

        for (EntityPlayer ent : mc.theWorld.playerEntities) {
            if (ent == mc.thePlayer) {
                return;
            }

            double yDiff = ent.posY - ent.prevPosY;

            if (ent.onGround) {
                airTicks.put(ent, 0);
                return;
            }

            if (yDiff < -0.45) {
                airTicks.put(ent, 0);
                return;
            }

            int ticks = airTicks.getOrDefault(ent, 0);
            airTicks.put(ent, ticks + 1);

            if (ticks > 35) {
                if (!Sleek.getInstance().getTargetManager().isTarget(ent)) {
                    flag(ent);
                    Sleek.getInstance().getTargetManager().getTarget().add(ent.getName());
                }
            }
        }
    }
}
