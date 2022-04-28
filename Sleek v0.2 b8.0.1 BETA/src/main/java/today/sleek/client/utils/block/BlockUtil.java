package today.sleek.client.utils.block;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import today.sleek.client.utils.Util;

public class BlockUtil extends Util {

    public static Block getBlockAt(BlockPos bpos) {
        return mc.theWorld.getBlockState(bpos).getBlock();
    }

}
