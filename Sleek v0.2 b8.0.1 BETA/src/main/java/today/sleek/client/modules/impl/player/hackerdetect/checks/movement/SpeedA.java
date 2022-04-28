package today.sleek.client.modules.impl.player.hackerdetect.checks.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import today.sleek.Sleek;
import today.sleek.client.modules.impl.player.HackerDetect;
import today.sleek.client.modules.impl.player.hackerdetect.checks.Check;
import today.sleek.client.utils.math.BPSUtil;

public class SpeedA extends Check {

    @Override
    public String name() {
        return "Speed (Check A)";
    }

    @Override
    public void onUpdate() {
        if (Minecraft.getMinecraft().thePlayer.ticksExisted > 20) {
           for (EntityPlayer ent : Minecraft.getMinecraft().theWorld.playerEntities) {

                if (ent.ticksExisted < 20) continue;
                if (ent.fallDistance > 20) continue;

                if (ent.hurtTime != 0) continue;

                if (BPSUtil.getBPS(ent) > 20 && BPSUtil.getBPS(ent) < 30) {
                    HackerDetect.getInstance().getViolations().put(ent, HackerDetect.getInstance().getViolations().getOrDefault(ent, 1));

                    if (HackerDetect.getInstance().getViolations().get(ent) > 60) {
                        if (!Sleek.getInstance().getTargetManager().isTarget(ent)) {
                            flag(ent);
                            Sleek.getInstance().getTargetManager().getTarget().add(ent.getName());
                        }
                    }
                } else {
                    HackerDetect.getInstance().getViolations().put(ent, HackerDetect.getInstance().getViolations().getOrDefault(ent, 1));
                }
            }
        }
    }
}
