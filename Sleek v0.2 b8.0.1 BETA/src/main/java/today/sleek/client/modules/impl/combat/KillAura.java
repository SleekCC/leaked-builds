package today.sleek.client.modules.impl.combat;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.GL11;
import today.sleek.Sleek;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.Render3DEvent;
import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.gui.notification.Notification;
import today.sleek.client.gui.notification.NotificationManager;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.modules.impl.visuals.TargetHUD;
import today.sleek.client.utils.combat.FightUtil;
import today.sleek.client.utils.math.Stopwatch;
import today.sleek.client.utils.network.PacketUtil;
import today.sleek.client.utils.pathfinding.DortPathFinder;
import today.sleek.client.utils.pathfinding.Vec3;
import today.sleek.client.utils.render.GLUtil;
import today.sleek.client.utils.render.RenderUtil;
import today.sleek.client.utils.rotations.AimUtil;
import today.sleek.client.utils.rotations.Rotation;
import today.sleek.client.utils.rotations.RotationUtil;

import javax.vecmath.Vector2f;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@ModuleData(
        name = "Killaura",
        category = ModuleCategory.COMBAT,
        description = "Automatically attacks nearby entities"
)
public class KillAura extends Module {

    public static EntityLivingBase target;
    public static boolean isBlocking, swinging;
    public final Stopwatch attackTimer = new Stopwatch();
    public ModeValue mode = new ModeValue("Mode", this, /*"Switch",*/ "Smart");
    public ModeValue targetPriority = new ModeValue("Target Priority", this, "None", "Distance", "Armor", "HurtTime", "Health");
    public ModeValue rotatemode = new ModeValue("Rotation Mode", this, "None", "Default", "Down", "NCP", "AAC", "GWEN");
    public NumberValue<Double> swingrage = new NumberValue<>("Swing Range", this, 3.0, 1.0, 9.0, 0.1);
    public NumberValue<Double> autoblockRange = new NumberValue<>("Block Range", this, 3.0, 1.0, 12.0, 0.1);
    public NumberValue<Double> cps = new NumberValue<>("CPS", this, 12.0, 1.0, 20.0, 1.0);
    public NumberValue<Double> cprandom = new NumberValue<>("Randomize CPS", this, 3.0, 0.0, 10.0, 1.0);
    public NumberValue<Double> fov = new NumberValue<>("FOV", this, 360.0, 1.0, 360.0, 1.0);
    public BooleanValue drawFOV = new BooleanValue("Draw FOV", this, false);
    public BooleanValue teleportAura = new BooleanValue("TP Hit", this, false);
    public BooleanValue tpHitRender = new BooleanValue("Render Path", this, false, teleportAura);
    public NumberValue<Double> tprange = new NumberValue<>("Teleport Range", this, 25.0, 0.0, 120.0, 1.0, teleportAura);
    public NumberValue chance = new NumberValue<>("Hit Chance", this, 100, 0, 100, 1);
    public ModeValue swingmode = new ModeValue("Swing Mode", this, "Client", "Server");
    public ModeValue attackMethod = new ModeValue("Attack Method", this, "Packet", "Legit");
    public ModeValue autoblockmode = new ModeValue("Autoblock Mode", this, "None", "Real", "Verus", "Hold", "Fake");
    public BooleanValue gcd = new BooleanValue("GCD", this, false);
    public BooleanValue hold = new BooleanValue("Hold", this, false);
    public BooleanValue players = new BooleanValue("Players", this, true);
    public BooleanValue friends = new BooleanValue("Friends", this, true);
    public BooleanValue animals = new BooleanValue("Animals", this, true);
    public BooleanValue monsters = new BooleanValue("Monsters", this, true);
    public BooleanValue invisible = new BooleanValue("Invisibles", this, true);
    public BooleanValue walls = new BooleanValue("Walls", this, true);
    public BooleanValue targetTracer = new BooleanValue("Draw Target Tracer", this, true);
    public Vector2f currentRotation = null;
    private int index;
    private boolean canBlock;
    private Rotation lastRotation;

    private ArrayList<Vec3> path;

    private int switchState;
    private Stopwatch switchTimer = new Stopwatch();
    private NumberValue switchDelay = new NumberValue("Switch Delay", this, 250, 1, 1000, 1);

    public static boolean isSwinging() {
        return swinging;
    }

    @Override
    public void onEnable() {
        index = 0;
        lastRotation = null;
        target = null;
        switchState = 0;
        this.attackTimer.resetTime();
    }

    @Override
    public void onDisable() {
        if (isBlocking) unblock();
        isBlocking = false;
        mc.gameSettings.keyBindUseItem.pressed = false;
        swinging = false;
        currentRotation = null;
        target = null;

        if (!mc.thePlayer.isBlocking()) {
            isBlocking = false;
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }

    @Subscribe
    public void doHoldBlock(UpdateEvent event) {
        if (autoblockmode.getValue().equalsIgnoreCase("Hold")) {
            mc.gameSettings.keyBindUseItem.pressed = KillAura.target != null;
        }
    }

    @Subscribe
    public void onMotion(UpdateEvent event) {
        List<EntityLivingBase> entities = FightUtil.getMultipleTargetsSafe(teleportAura.getValue() ? tprange.getValue() : swingrage.getValue(), 5, players.getValue(), friends.getValue(), animals.getValue(), false, monsters.getValue(), invisible.getValue());

        if (mc.currentScreen != null) return;

        if (isBlocking && target == null) {
            unblock();
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }


        if (mc.thePlayer.ticksExisted < 5) {
            if (isToggled()) {
                NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "World Change!", "Killaura disabled", 1));
                toggle();
            }
        }

        //Return if the player isn't holding attack & hold is on
        if (hold.getValue() && !mc.gameSettings.keyBindAttack.isKeyDown()) {
            return;
        }

        List<EntityLivingBase> blockRangeEntites = FightUtil.getMultipleTargets(autoblockRange.getValue(), players.getValue(), friends.getValue(), animals.getValue(), walls.getValue(), monsters.getValue(), invisible.getValue());

        entities.removeIf(e -> e.getName().contains("[NPC]"));

        entities.removeIf(e -> e.getHealth() < 0);

        if (fov.getValue() != 360f) {
            entities.removeIf(e -> !RotationUtil.isVisibleFOV(e, fov.getValue().floatValue() / 2));
        }

        //target switching (switch aura)
        if (switchTimer.timeElapsed(switchDelay.getValue().longValue())) {
            if (switchState < (entities.size() - 1)) {
                switchState++;
            } else {
                switchState = 0;
            }
            switchTimer.resetTime();
        }

        ItemStack heldItem = mc.thePlayer.getHeldItem();

        canBlock = !blockRangeEntites.isEmpty()
                && heldItem != null
                && heldItem.getItem() instanceof ItemSword;

        if (event.isPre()) {
            target = null;
        }

        if (entities.isEmpty()) {
            index = 0;

            isBlocking = false;
        } else {
            if (index >= entities.size()) index = 0;

            if (canBlock) {
                switch (autoblockmode.getValue()) {
                    case "Real":
                        if (!event.isPre()) {
                            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                            isBlocking = true;
                        }
                        break;
                    case "Fake": {
                        isBlocking = true;
                        break;
                    }
                }
            }


            if (event.isPre()) {
                switch (mode.getValue()) {
                    case "Smart": {
                        switch (targetPriority.getValue().toLowerCase()) {
                            case "distance": {
                                entities.sort(Comparator.comparingInt(e -> (int) -e.getDistanceToEntity(mc.thePlayer)));
                                break;
                            }
                            case "armor": {
                                entities.sort(Comparator.comparingInt(e -> -e.getTotalArmorValue()));
                                break;
                            }
                            case "hurttime": {
                                entities.sort(Comparator.comparingInt(e -> -e.hurtResistantTime));
                                break;
                            }
                            case "health": {
                                entities.sort(Comparator.comparingInt(e -> (int) -e.getHealth()));
                                break;
                            }
                        }
                        Collections.reverse(entities);
                        target = entities.get(switchState);

                        //set the targetted players as main targets.
                        entities.forEach(entityLivingBase -> {
                            if (entityLivingBase instanceof EntityPlayer && Sleek.getInstance().getTargetManager().isTarget((EntityPlayer) target)) {
                                target = entities.get(entities.indexOf(entityLivingBase));
                            }
                        });
                        break;
                    }
                }


                if (!teleportAura.getValue()) {
                    aimAtTarget(event, rotatemode.getValue(), target);
                }
            }

            if (event.isPre()) {

                boolean canIAttack = attackTimer.timeElapsed((long) (1000L / cps.getValue()));

                if (canIAttack) {
                    if (cps.getValue() > 9) {
                        cps.setValue(cps.getValue() - RandomUtils.nextInt(0, cprandom.getValue().intValue()));
                    } else {
                        cps.setValue(cps.getValue() + RandomUtils.nextInt(0, cprandom.getValue().intValue()));
                    }
                    switch (mode.getValue()) {
                        case "Switch": {
                            if (canIAttack && attack(target, chance.getValue().intValue())) {
                                index++;
                                attackTimer.resetTime();
                            }
                            break;
                        }
                        case "Smart": {
                            if (canIAttack && attack(target, chance.getValue().intValue())) {
                                attackTimer.resetTime();
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean attack(EntityLivingBase entity, double chance) {
        if (FightUtil.canHit(chance / 100)) {
            if (swingmode.getValue().equalsIgnoreCase("client")) {
                mc.thePlayer.swingItem();
            } else if (swingmode.getValue().equalsIgnoreCase("server")) {
                mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
            }
            path = DortPathFinder.computePath(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), new Vec3(entity.posX, entity.posY, entity.posZ));
            if (teleportAura.getValue()) {
                for (Vec3 vec : path) {
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(vec.getX(), vec.getY(), vec.getZ(), true));
                }
            }
            //sending the attack directly through a packet prevents you from getting slowed down when hitting
            if (attackMethod.getValue().equalsIgnoreCase("Packet"))
                PacketUtil.sendPacket(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            else
                mc.playerController.attackEntity(mc.thePlayer, entity);

            if (teleportAura.getValue()) {
                Collections.reverse(path);
                for (Vec3 vector : path) {
                    PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(vector.getX(), vector.getY(), vector.getZ(), true));
                }
            }

            if (!isBlocking && autoblockmode.getValue().equalsIgnoreCase("verus")) {
                PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                isBlocking = true;
            }

            return true;
        } else {
            mc.thePlayer.swingItem();
        }
        return false;
    }

    public void aimAtTarget(UpdateEvent event, String mode, Entity target) {
        Rotation rotation = AimUtil.getRotationsRandom((EntityLivingBase) target);

        if (lastRotation == null) {
            lastRotation = rotation;
            attackTimer.resetTime();
            return;
        }

        Rotation temp = rotation;

        rotation = lastRotation;

        switch (mode.toUpperCase()) {
            case "DEFAULT":
                event.setRotationYaw(rotation.getRotationYaw());
                event.setRotationPitch(rotation.getRotationPitch());
                break;
            case "DOWN":
                temp = new Rotation(mc.thePlayer.rotationYaw, 90.0f);
                event.setRotationPitch(90.0F);
                break;
            case "NCP":
                lastRotation = temp = rotation = Rotation.fromFacing((EntityLivingBase) target);
                event.setRotationYaw(rotation.getRotationYaw());
                break;
            case "AAC":
                rotation = new Rotation(mc.thePlayer.rotationYaw, temp.getRotationPitch());
                event.setRotationPitch(rotation.getRotationPitch());
                break;
            case "GWEN":
                temp = mc.thePlayer.ticksExisted % 5 == 0 ? AimUtil.getRotationsRandom((EntityLivingBase) target) : lastRotation;
                event.setRotationYaw(temp.getRotationYaw());
                event.setRotationPitch(temp.getRotationPitch());
                break;
        }
        lastRotation = temp;
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        Packet packet = event.getPacket();
        if (isBlocking && ((packet instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging) packet).getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM) || packet instanceof C08PacketPlayerBlockPlacement)) {
            event.setCancelled(true);
        }
        if (packet instanceof C09PacketHeldItemChange) {
            isBlocking = false;
        }

        //fix bad packets I on vulcan
        if (event.getPacket() instanceof C0BPacketEntityAction) {
            event.setCancelled(true);
        }

        if (gcd.getValue() && target != null && event.getPacket() instanceof C03PacketPlayer && ((C03PacketPlayer) event.getPacket()).getRotating()) {
            C03PacketPlayer p = event.getPacket();
            float m = (float) (0.005 * mc.gameSettings.mouseSensitivity / 0.005);
            double f = m * 0.6 + 0.2;
            double gcd = m * m * m * 1.2;
            p.pitch -= p.pitch % gcd;
            p.yaw -= p.yaw % gcd;
        }
    }

    @Subscribe
    public void onRender(RenderOverlayEvent event) {
        if (drawFOV.getValue()) {
            float drawingFOV = fov.getValue().floatValue() * 6;
            RenderUtil.drawUnfilledCircle((RenderUtil.getResolution().getScaledWidth() - drawingFOV) / 2, (RenderUtil.getResolution().getScaledHeight() - drawingFOV) / 2, drawingFOV, Color.WHITE.getRGB());
        }

        if (target == null) {
            return;
        }

        TargetHUD targetHUD = Sleek.getInstance().getModuleManager().getModuleByClass(TargetHUD.class);

        if (targetHUD.isToggled()) {
            targetHUD.render(event, target);
        }
    }

    @Subscribe
    public void onRender3D(Render3DEvent event) {
        if (targetTracer.getValue()) {
            if (target != null) {
                trace(target, 2, new Color(255, 0, 0), Minecraft.getMinecraft().timer.renderPartialTicks);
            }
        }

        if (tpHitRender.getValue() && teleportAura.getValue()) {
            for (Vec3 vec : path) {
                final double x = RenderUtil.interpolate(vec.getX(), vec.getX(), event.getPartialTicks());
                final double y = RenderUtil.interpolate(vec.getY(), vec.getY(), event.getPartialTicks());
                final double z = RenderUtil.interpolate(vec.getZ(), vec.getZ(), event.getPartialTicks());

                double xPos = x - mc.getRenderManager().renderPosX;
                double yPos = vec.getY();
                double zPos = z - mc.getRenderManager().renderPosZ;

                RenderUtil.drawEntityESP(xPos, yPos - 3.5, zPos, 0.5, 0.5, new Color(255, 255, 255), false);
            }
        }
    }

    private void drawEntityESP(double x, double y, double z, double height, double width, Color color) {
        GL11.glPushMatrix();
        GLUtil.setGLCap(3042, true);
        GLUtil.setGLCap(3553, false);
        GLUtil.setGLCap(2896, false);
        GLUtil.setGLCap(2929, false);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.8f);
        GL11.glBlendFunc(770, 771);
        GLUtil.setGLCap(2848, true);
        GL11.glDepthMask(true);
        RenderUtil.BB(new AxisAlignedBB(x - width + 0.25, y, z - width + 0.25, x + width - 0.25, y + height, z + width - 0.25), new Color(color.getRed(), color.getGreen(), color.getBlue(), 120).getRGB());
        RenderUtil.OutlinedBB(new AxisAlignedBB(x - width + 0.25, y, z - width + 0.25, x + width - 0.25, y + height, z + width - 0.25), 1, color.getRGB());
        GLUtil.revertAllCaps();
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
    }

    //credit: moonx (idk how to do visuals)
    private void trace(Entity entity, float width, Color color, float partialTicks) {
        /* Setup separate path rather than changing everything */
        float r = ((float) 1 / 255) * color.getRed();
        float g = ((float) 1 / 255) * color.getGreen();
        float b = ((float) 1 / 255) * color.getBlue();
        GL11.glPushMatrix();

        /* Load custom identity */
        GL11.glLoadIdentity();

        /* Set the camera towards the partialTicks */
        mc.entityRenderer.orientCamera(partialTicks);

        /* PRE */
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);

        /* Keep it AntiAliased */
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        /* Interpolate needed X, Y, Z files */
        double x = RenderUtil.interpolate(entity.posX, entity.lastTickPosX, partialTicks) - mc.getRenderManager().viewerPosX;
        double y = RenderUtil.interpolate(entity.posY, entity.lastTickPosY, partialTicks) - mc.getRenderManager().viewerPosY;
        double z = RenderUtil.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks) - mc.getRenderManager().viewerPosZ;



        /* Setup line width */
        GL11.glLineWidth(width);

        /* Drawing */
        GL11.glBegin(GL11.GL_LINE_STRIP);
        {
            GL11.glColor3d(r, g, b);
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(0.0, mc.thePlayer.getEyeHeight(), 0.0);
            GL11.glEnd();
            GL11.glDisable(GL11.GL_LINE_SMOOTH);

            /* POST */
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glEnable(2929);

            /* End the custom path */
            GL11.glPopMatrix();
        }
    }

    private void unblock() {
        isBlocking = false;
    }

    @Override
    public String getSuffix() {
        return " " + mode.getValueAsString();
    }
}
