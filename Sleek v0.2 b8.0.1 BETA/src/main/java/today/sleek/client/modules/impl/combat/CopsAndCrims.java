package today.sleek.client.modules.impl.combat;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import today.sleek.Sleek;
import today.sleek.base.event.impl.Render3DEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.math.Stopwatch;
import today.sleek.client.utils.network.PacketUtil;
import today.sleek.client.utils.render.GLUtil;
import today.sleek.client.utils.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ModuleData(
        name = "Chicago",
        category = ModuleCategory.COMBAT,
        description = "pew pew shoot the opps"
)
public class CopsAndCrims extends Module {

    public static EntityLivingBase target;
    private Stopwatch watch = new Stopwatch();
    private NumberValue<Double> range = new NumberValue<>("Reach", this, 25.0, 0.1, 100.0, 0.1);
    private NumberValue<Integer> cps = new NumberValue<>("CPS", this, 8, 0, 30, 1);


    @Override
    public void onEnable() {
        target = null;
    }

    @Subscribe
    public void doRecoil(UpdateEvent event) {
        if (target != null && event.isPre()) {
            event.setRotationPitch(event.getRotationPitch());
        }
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        List<EntityLivingBase> targets = new ArrayList<>();
        for (Entity entity : mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList())) {
            targets.add((EntityLivingBase) entity);
        }

        targets = targets.stream().filter(e -> isValid(e)).collect(Collectors.toList());

        targets.sort(Comparator.comparingDouble(e -> e.getDistanceToEntity(mc.thePlayer)));

//        targets.sort((o1, o2) -> Boolean.compare(o1.canEntityBeSeen(mc.thePlayer), o2.canEntityBeSeen(mc.thePlayer)));

        target = null;
        if (!targets.isEmpty()) {
            target = targets.get(0);
            if (target != null && target.canEntityBeSeen(mc.thePlayer)) {
                float rotations[] = getRotations(target);
                event.setRotationYaw(rotations[0]);
                event.setRotationPitch(rotations[1]);
                if (watch.timeElapsed(1000 / cps.getValue())) {
                    watch.resetTime();
                    PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                    PacketUtil.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                    PacketUtil.sendPacketNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));

                }

            }
        }
    }


    @Subscribe
    public void onRender3D(Render3DEvent event) {
        if (target != null) {
            final double x = RenderUtil.interpolate(target.posX, target.lastTickPosX, event.getPartialTicks());
            final double y = RenderUtil.interpolate(target.posY, target.lastTickPosY, event.getPartialTicks());
            final double z = RenderUtil.interpolate(target.posZ, target.lastTickPosZ, event.getPartialTicks());
            drawEntityESP(x - RenderManager.renderPosX,
                    y + target.height + 0.1 - target.height - RenderManager.renderPosY,
                    z - RenderManager.renderPosZ,
                    target.height,
                    0.65,
                    target.canEntityBeSeen(mc.thePlayer) ? new Color(0xE32626) : new Color(0x49E326));
        }
    }

    private boolean isValid(EntityLivingBase entity) {
        return !(entity.getDistanceToEntity(mc.thePlayer) >= range.getValue() ||
                isOnSameTeam(entity) ||
                entity == mc.thePlayer ||
                entity.isInvisible() ||
                (entity.canEntityBeSeen(mc.thePlayer) && entity.getDistanceToEntity(mc.thePlayer) >= range.getValue() / 2) ||
                entity.isDead ||
                Sleek.getInstance().getFriendManager().isFriend(entity.getName()));
    }

    public float[] getRotations(EntityLivingBase e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) ((-Math.toDegrees(Math.atan(deltaY / distance)))) + (mc.thePlayer.getDistanceToEntity(e) / 4);

        double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + v);
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + v);
        }

        return new float[]{yaw, pitch};
    }

    /**
     * @param x
     * @param y
     * @param z
     * @param height
     * @param width
     * @param color
     * @author oHare or whoever the fuck is at moonx (visuals are hard)
     */
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

    private boolean isOnSameTeam(EntityLivingBase entity) {
        if (entity.getTeam() != null && mc.thePlayer.getTeam() != null) {
            Team team1 = entity.getTeam();
            return team1.getNameTagVisibility().equals(Team.EnumVisible.ALWAYS);
        }
        return false;
    }

}
