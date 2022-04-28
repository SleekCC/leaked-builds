package today.sleek.client.modules.impl.combat;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C0APacketAnimation;
import today.sleek.Sleek;
import today.sleek.base.event.PacketDirection;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.combat.FightUtil;
import today.sleek.client.utils.network.PacketUtil;
import today.sleek.client.utils.render.RenderUtil;
import today.sleek.client.utils.render.RenderUtils;
import today.sleek.client.utils.rotations.RotationUtil;

import java.awt.*;
import java.util.List;

/**
 * @author Kansio
 */
@ModuleData(name = "Aimbot", description = "Automatically aims at enemies", category = ModuleCategory.COMBAT)
public class Aimbot extends Module {

    private BooleanValue silent = new BooleanValue("Silent", this, false);
    private NumberValue fov = new NumberValue("FOV", this, 60, 0, 360, 1);
    private BooleanValue drawFOV = new BooleanValue("FOV Circle", this, true);
    private NumberValue range = new NumberValue("Range", this, 3.0, 2.5, 6.0, 0.05);

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C0APacketAnimation && mc.thePlayer.isSwingInProgress && silent.getValue()) {
            if (handleSilentAttack()) {
                event.setCancelled(true);
            }
        }
    }

    public boolean handleSilentAttack() {
        if (!silent.getValue()) {
            return false;
        }

        if (mc.objectMouseOver.entityHit != null) {
            return false;
        }

        List<EntityLivingBase> attackList = FightUtil.getMultipleTargets(range.getValue().floatValue(), true, false, false, false, false, true);

        //sort the people by crosshair
        attackList.sort((o1, o2) -> {
            double rot1 = FightUtil.getRotationToEntity(o1)[0];
            double rot2 = FightUtil.getRotationToEntity(o2)[0];
            double h1 = (mc.thePlayer.rotationYaw - rot1) ;
            double h2 = (mc.thePlayer.rotationYaw - rot2) ;
            return Double.compare(h1, h2);
        });

        if (fov.getValue().floatValue() != 360f) {
            attackList.removeIf(e -> !RotationUtil.isVisibleFOV(e, fov.getValue().floatValue() / 2));
        }

        if (attackList.isEmpty())
            return false;

        EntityLivingBase target = attackList.get(0);


        //send the packet silently
        PacketUtil.sendPacketNoEvent(new C0APacketAnimation());

        //attack the player
        mc.playerController.attackEntity(mc.thePlayer, target);
        return true;
    }

    @Subscribe
    public void onRender(RenderOverlayEvent event) {
        if (drawFOV.getValue()) {
            float drawingFOV = fov.getValue().floatValue() * 6;
            RenderUtil.drawUnfilledCircle((RenderUtil.getResolution().getScaledWidth() - drawingFOV) / 2, (RenderUtil.getResolution().getScaledHeight() - drawingFOV) / 2, drawingFOV, Color.WHITE.getRGB());
        }
    }

}
