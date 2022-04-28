package today.sleek.client.utils.math;

import net.minecraft.entity.player.EntityPlayer;
import today.sleek.client.utils.Util;

public class BPSUtil extends Util {

    public static double getBPS() {
        return mc.thePlayer.getDistance(mc.thePlayer.lastTickPosX, mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ) * (mc.timer.ticksPerSecond * mc.timer.timerSpeed);
    }

    public static double getBPS(EntityPlayer player) {
        return player.getDistance(player.lastTickPosX, player.posY, player.lastTickPosZ) * (mc.timer.ticksPerSecond * mc.timer.timerSpeed);
    }

}
