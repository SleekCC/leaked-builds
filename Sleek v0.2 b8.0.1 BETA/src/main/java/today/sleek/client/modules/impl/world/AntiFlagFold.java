package today.sleek.client.modules.impl.world;

import com.google.common.eventbus.Subscribe;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.math.MathUtil;
import today.sleek.client.utils.player.PlayerUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@ModuleData(
        name = "New Scaffold",
        description = "Credits to Vince im not making this shit by hand its too hard",
        category = ModuleCategory.PLAYER // TODO: change to world when finished
)
public class AntiFlagFold extends Module {

    public ModeValue mode = new ModeValue("Rotations", this, "Smooth", "Watchdog", "Legit", "Snap", "None");
    public BooleanValue safeWalk = new BooleanValue("Safe Walk", this, true);
    public NumberValue timer = new NumberValue("Timer", this, 1, 1, 5, 0.05);
    public BooleanValue sprint = new BooleanValue("Sprint", this, false);
    public NumberValue delayMin = new NumberValue("Delay Min", this, 1, 0, 15, 1);
    public NumberValue delayMax = new NumberValue("Delay Max", this, 1, 0, 15, 1);
    public NumberValue minRot = new NumberValue("Minimum Rotation", this, 5, 0, 15, 0.2);
    public NumberValue maxRot = new NumberValue("Maximum Rotation", this, 5, 0, 15, 0.2);
    public BooleanValue noAura = new BooleanValue("No Aura", this, false);
    public static BlockDataOld data = null;
    private final List<Block> validBlocks;
    private final List<Block> invalidBlocks;
    private final BlockPos[] blockPositions;
    private final EnumFacing[] facings;
    private int slot;
    public float yaw, pitch;

    public AntiFlagFold() {
        this.invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.furnace, Blocks.carpet, Blocks.crafting_table, Blocks.trapped_chest, (Block) Blocks.chest, Blocks.dispenser, Blocks.air, (Block) Blocks.water, (Block) Blocks.lava, (Block) Blocks.flowing_water, (Block) Blocks.flowing_lava, (Block) Blocks.sand, Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.wooden_pressure_plate, Blocks.heavy_weighted_pressure_plate, (Block) Blocks.stone_slab, (Block) Blocks.wooden_slab, (Block) Blocks.stone_slab2, (Block) Blocks.red_mushroom, (Block) Blocks.brown_mushroom, (Block) Blocks.yellow_flower, (Block) Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, (Block) Blocks.stained_glass_pane, Blocks.iron_bars, (Block) Blocks.cactus, Blocks.ladder, Blocks.web, Blocks.gravel, Blocks.tnt);
        this.validBlocks = Arrays.asList(Blocks.air, (Block) Blocks.water, (Block) Blocks.flowing_water, (Block) Blocks.lava, (Block) Blocks.flowing_lava);
        this.blockPositions = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1)};
        this.facings = new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH};
        this.slot = -1;
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        mc.timer.timerSpeed = timer.getValue().floatValue();
        mc.thePlayer.setSprinting(sprint.getValue());
        mc.gameSettings.keyBindSprint.pressed = false;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            PlayerUtil.setMotion(0.075f);
        }
        BlockPos pos = null;

        if (mc.thePlayer.ticksExisted % 35 == 0) {
            try {
                new Thread(() -> {
                    try {
                        mc.gameSettings.keyBindSneak.pressed = true;
                        Thread.sleep(75);
                        mc.gameSettings.keyBindSneak.pressed = false;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }).start();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        final EntityPlayerSP player = this.mc.thePlayer;
        final WorldClient world = this.mc.theWorld;
        double yDif = 1.0;

        for (double posY = player.posY - yDif; posY > 0.0; --posY) {
            final BlockDataOld newData = this.getBlockDataOld2(new BlockPos(player.posX, mc.thePlayer.posY - 1, player.posZ));
            if (newData != null) {
                yDif = player.posY - posY;
                if (yDif <= 20) {
                    data = newData;
                    break;
                }
            }
        }

        pos = data.pos;
        final Vec3 hitVec = this.getVec3(data);

        int slot = -1;
        int blockCount = 0;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = player.inventory.getStackInSlot(i);
            if (itemStack != null) {
                final int stackSize = itemStack.stackSize;
                if (this.isValidItem(itemStack.getItem()) && stackSize > blockCount) {
                    blockCount = stackSize;
                    slot = i;
                }
            }
        }

        int randomInt = MathUtil.getRandomInRange((int) delayMin.getValue(), (int) delayMax.getValue());
        if (!e.isPre()) {
            if (data != null && slot != -1) {
                if (mc.thePlayer.ticksExisted % randomInt == 0) {
                    randomInt = MathUtil.getRandomInRange((int) delayMin.getValue(), (int) delayMax.getValue());
                    player.inventory.currentItem = slot;
                    final BlockDataOld newData = this.getBlockDataOld2(new BlockPos(player.posX, mc.thePlayer.posY - 1, player.posZ));
                    if (this.mc.playerController.onPlayerRightClick(player, world, player.getCurrentEquippedItem(), newData.pos, newData.face, hitVec)) {
                        float[] rotations = getBlockRotations(data.pos, data.face);
                        yaw = rotations[0];
                        pitch = rotations[1] - 0.7f - MathUtil.getRandomInRange((int) minRot.getValue(), (int) maxRot.getValue());
                        player.swingItem();
                    }
                }
            }
        }
        if (e.isPre()) {
            float[] rotations = getBlockRotations(data.pos, data.face);
            switch ("Watchdog") {
                case "Watchdog":
                    setRotations(e, (float) MathUtil.round((double) rotations[0], 44), rotations[1] - 0.7f - MathUtil.getRandomInRange((int) minRot.getValue(), (int) maxRot.getValue()));
                    break;
                case "Smooth":
                    setRotations(e, rotations[0], rotations[1]);
                    break;
                case "Legit":
                    setRotations(e, rotations[0], rotations[1] - 0.7f - MathUtil.getRandomInRange((int) minRot.getValue(), (int) maxRot.getValue()));
                    break;
                case "Snap":
                    setRotations(e, yaw, pitch);
                case "None":
                    break;
            }
        }
    }

    /**
     * Added this to avoid compatibility issues
     * @param event
     * @param yaw
     * @param pitch
     */
    public void setRotations(UpdateEvent event, float yaw, float pitch) {
        event.setRotationYaw(yaw);
        event.setRotationPitch(pitch);
    }
    
    public static double getDirection() {
        float rotationYaw = mc.thePlayer.rotationYaw;

        if(mc.thePlayer.moveForward < 0F)
            rotationYaw += 180F;

        float forward = 1F;
        if(mc.thePlayer.moveForward < 0F)
            forward = -0.5F;
        else if(mc.thePlayer.moveForward > 0F)
            forward = 0.5F;

        if(mc.thePlayer.moveStrafing > 0F)
            rotationYaw -= 90F * forward;

        if(mc.thePlayer.moveStrafing < 0F)
            rotationYaw += 90F * forward;

        return rotationYaw;
    }

    private float[] getBlockRotations(BlockPos blockPos, EnumFacing enumFacing) {
        if (blockPos == null && enumFacing == null) {
            return null;
        } else {
            Vec3 positionEyes = this.mc.thePlayer.getPositionEyes(2.0F);
            Vec3 add = (new Vec3((double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D));
            double n = add.xCoord - positionEyes.xCoord;
            double n2 = add.yCoord - positionEyes.yCoord;
            double n3 = add.zCoord - positionEyes.zCoord;
            return new float[]{(float)(Math.atan2(n3, n) * 180.0D / 3.141592653589793D - 90.0D), -((float)(Math.atan2(n2, (double)((float)Math.hypot(n, n3))) * 180.0D / 3.141592653589793D))};
        }
    }

    private Vec3 getVec3(final BlockDataOld data) {
        final BlockPos pos = data.pos;
        final EnumFacing face = data.face;
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        x += face.getFrontOffsetX() / 2.0;
        z += face.getFrontOffsetZ() / 2.0;
        y += face.getFrontOffsetY() / 2.0;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += this.randomNumber(0.3, -0.3);
            z += this.randomNumber(0.3, -0.3);
        }
        else {
            y += this.randomNumber(0.49, 0.5);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += this.randomNumber(0.3, -0.3);
        }

        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += this.randomNumber(0.3, -0.3);
        }
        return new Vec3(x, y, z);
    }

    private double randomNumber(final double max, final double min) {
        return Math.random() * (max - min) + min;
    }

    private BlockDataOld getBlockDataOld2(final BlockPos pos) {
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos.add(-1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos.add(1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos.add(0, 0, 1))).getBlock())) {
            return new BlockDataOld(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos.add(0, 0, -1))).getBlock())) {
            return new BlockDataOld(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos1 = pos.add(-1, 0, 0);
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos1.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos1.add(-1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos1.add(1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos1.add(0, 0, 1))).getBlock())) {
            return new BlockDataOld(pos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos1.add(0, 0, -1))).getBlock())) {
            return new BlockDataOld(pos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos2 = pos.add(1, 0, 0);
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos2.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos2.add(-1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos2.add(1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos2.add(0, 0, 1))).getBlock())) {
            return new BlockDataOld(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos2.add(0, 0, -1))).getBlock())) {
            return new BlockDataOld(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos3 = pos.add(0, 0, 1);
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos3.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos3.add(-1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos3.add(1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos3.add(0, 0, 1))).getBlock())) {
            return new BlockDataOld(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos3.add(0, 0, -1))).getBlock())) {
            return new BlockDataOld(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos4 = pos.add(0, 0, -1);
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos4.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos4.add(-1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos4.add(1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos4.add(0, 0, 1))).getBlock())) {
            return new BlockDataOld(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos4.add(0, 0, -1))).getBlock())) {
            return new BlockDataOld(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos19 = pos.add(-2, 0, 0);
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos1.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos1.add(-1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos1.add(1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos1.add(0, 0, 1))).getBlock())) {
            return new BlockDataOld(pos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos1.add(0, 0, -1))).getBlock())) {
            return new BlockDataOld(pos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos2.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos2.add(-1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos2.add(1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos2.add(0, 0, 1))).getBlock())) {
            return new BlockDataOld(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos2.add(0, 0, -1))).getBlock())) {
            return new BlockDataOld(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos3.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos3.add(-1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos3.add(1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos3.add(0, 0, 1))).getBlock())) {
            return new BlockDataOld(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos3.add(0, 0, -1))).getBlock())) {
            return new BlockDataOld(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos4.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos4.add(-1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos4.add(1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos4.add(0, 0, 1))).getBlock())) {
            return new BlockDataOld(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos4.add(0, 0, -1))).getBlock())) {
            return new BlockDataOld(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos5 = pos.add(0, -1, 0);
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos5.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos5.add(-1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos5.add(1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos5.add(0, 0, 1))).getBlock())) {
            return new BlockDataOld(pos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos5.add(0, 0, -1))).getBlock())) {
            return new BlockDataOld(pos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos6 = pos5.add(1, 0, 0);
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos6.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos6.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos6.add(-1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos6.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos6.add(1, 0, 0))).getBlock())) {
            return new BlockDataOld(pos6.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos6.add(0, 0, 1))).getBlock())) {
            return new BlockDataOld(pos6.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos6.add(0, 0, -1))).getBlock())) {
            return new BlockDataOld(pos6.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos7 = pos5.add(-1, 0, 0);
        if (!invalidBlocks.contains(mc.theWorld.getBlockState((pos7.add(0, -1, 0))).getBlock())) {
            return new BlockDataOld(pos7.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos7.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos7.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos7.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos7.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos7.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos7.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos7.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos7.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos8 = pos5.add(0, 0, 1);
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos8.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos8.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos8.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos8.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos8.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos8.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos8.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos8.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos8.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos8.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos9 = pos5.add(0, 0, -1);
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos9.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos9.add(0, -1, 0), EnumFacing.UP);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos9.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos9.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos9.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos9.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos9.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos9.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!invalidBlocks.contains(mc.theWorld.getBlockState(pos9.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos9.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }

    private static class BlockDataOld
    {
        public final BlockPos pos;
        public final EnumFacing face;

        private BlockDataOld(final BlockPos pos, final EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }
    }

    private boolean isValidItem(final Item item) {
        if (item instanceof ItemBlock) {
            final ItemBlock iBlock = (ItemBlock)item;
            final Block block = iBlock.getBlock();
            return !this.invalidBlocks.contains(block);
        }
        return false;
    }
}
