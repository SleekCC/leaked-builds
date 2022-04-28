package today.sleek.client.modules.impl.combat;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import today.sleek.Sleek;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.modules.impl.world.Scaffold;
import today.sleek.client.utils.combat.FightUtil;
import today.sleek.client.utils.math.Stopwatch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ModuleData(
        name = "Aura",
        category = ModuleCategory.COMBAT,
        description = "Its killaura but recoded"
)
public class Aura extends Module {

    public static EntityLivingBase target;
    private Stopwatch watch = new Stopwatch();
    private NumberValue<Double> range = new NumberValue<>("Reach", this, 3.0, 0.1, 6.0, 0.1);
    private NumberValue<Integer> cps = new NumberValue<>("CPS", this, 8, 0, 30, 1);
    private BooleanValue infiniteCps = new BooleanValue("Always Click", this, false);


    @Override
    public void onEnable() {
        target = null;
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        Scaffold scaffold = Sleek.getInstance().getModuleManager().getModuleByClass(Scaffold.class);

        if (scaffold.getAuraToggle().getValue() && scaffold.isToggled())
            return;

        List<EntityLivingBase> targets = new ArrayList<>();
        for (Entity entity : mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList())) {
            targets.add((EntityLivingBase) entity);
        }

        targets = targets.stream().filter(e -> e.getDistanceToEntity(mc.thePlayer) < range.getValue() && e != mc.thePlayer && !e.isDead).collect(Collectors.toList());

        targets.sort(Comparator.comparingDouble(e -> e.getDistanceToEntity(mc.thePlayer)));

        if (targets.isEmpty()) {
            return;
        }
        target = targets.get(0);
        if (target != null) {
            float rotations[] = getRotations(target);
            event.setRotationYaw(rotations[0]);
            event.setRotationPitch(rotations[1]);

            if (watch.timeElapsed(1000 / cps.getValue()) || infiniteCps.getValue()) {
                watch.resetTime();
                mc.thePlayer.swingItem();
                mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            }
        }
    }

    public float[] getRotations(EntityLivingBase e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 +  v);
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + v);
        }

        return new float[] {yaw, pitch};
    }

}
