package today.sleek.client.modules.impl.movement;

import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.AtomicDouble;
import today.sleek.base.event.impl.MoveEvent;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.gui.notification.Notification;
import today.sleek.client.gui.notification.NotificationManager;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.java.ReflectUtils;
import today.sleek.client.utils.player.PlayerUtil;
import today.sleek.client.utils.player.TimerUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ModuleData(name = "Speed", category = ModuleCategory.MOVEMENT, description = "Move faster than normal.")
public class Speed extends Module {
    private final List<? extends SpeedMode> modes = ReflectUtils.getReflects(this.getClass().getPackage().getName() + ".speed", SpeedMode.class).stream().map(aClass -> {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }).sorted(Comparator.comparing(speedMode -> speedMode != null ? speedMode.getName() : null)).collect(Collectors.toList());
    private final ModeValue mode = new ModeValue("Mode", this, modes.stream().map(SpeedMode::getName).collect(Collectors.toList()).toArray(new String[] {}));
    private SpeedMode currentMode = modes.stream().anyMatch(speedMode -> speedMode.getName().equalsIgnoreCase(mode.getValue())) ? modes.stream().filter(speedMode -> speedMode.getName().equalsIgnoreCase(mode.getValue())).findAny().get() : null;
    private final NumberValue<Double> speed = new NumberValue<>("Speed", this, 0.5, 0.0, 8.0, 0.1);
    private final NumberValue<Float> timer = new NumberValue<>("Timer Speed", this, 1.0F, 1.0F, 2.5F, 0.1F, mode, "Watchdog", "BlocksMC", "");
    private final BooleanValue forceFriction = new BooleanValue("Force Friction", this, true);
    private final ModeValue frictionMode = new ModeValue("Friction", this, forceFriction, "NCP", "NEW", "LEGIT", "SILENT");
    private final AtomicDouble hDist = new AtomicDouble();

    @Override
    public void onEnable() {
        currentMode = modes.stream().anyMatch(speedMode -> speedMode.getName().equalsIgnoreCase(mode.getValue())) ? modes.stream().filter(speedMode -> speedMode.getName().equalsIgnoreCase(mode.getValue())).findAny().get() : null;
        if (currentMode == null) {
            ChatUtil.log("§c§lError! §fIt looks like this mode doesn\'t exist anymore, setting it to a mode that exists.");
            currentMode = modes.get(0);
            toggle();
            return;
        }
        currentMode.onEnable();
    }

    @Override
    public void onDisable() {
        TimerUtil.Reset();
        PlayerUtil.setMotion(0);
        hDist.set(0);
        currentMode.onDisable();
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        currentMode.onUpdate(event);
        if (mc.thePlayer.ticksExisted < 5) {
            if (isToggled()) {
                NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "World Change!", "Speed disabled", 1));
                toggle();
            }
        }
    }

    @Subscribe
    public void onMove(MoveEvent event) {
        currentMode.onMove(event);
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        currentMode.onPacket(event);
    }

    public double handleFriction(AtomicDouble atomicDouble) {
        if (forceFriction.getValue()) {
            double value = atomicDouble.get();
            switch (frictionMode.getValue()) {
            case "NCP": 
                atomicDouble.set(value - value / 159);
                break;
            case "NEW": 
                atomicDouble.set(value * 0.98);
                break;
            case "LEGIT": 
                atomicDouble.set(value * 0.91);
                break;
            case "SILENT": 
                atomicDouble.set(value - 1.0E-9);
                break;
            }
            return Math.max(atomicDouble.get(), PlayerUtil.getVerusBaseSpeed());
        }
        return atomicDouble.get();
    }

    @Override
    public String getSuffix() {
        return " " + mode.getValueAsString();
    }

    @SuppressWarnings("all")
    public NumberValue<Double> getSpeed() {
        return this.speed;
    }

    @SuppressWarnings("all")
    public NumberValue<Float> getTimer() {
        return this.timer;
    }

    @SuppressWarnings("all")
    public BooleanValue getForceFriction() {
        return this.forceFriction;
    }

    @SuppressWarnings("all")
    public ModeValue getFrictionMode() {
        return this.frictionMode;
    }

    @SuppressWarnings("all")
    public AtomicDouble getHDist() {
        return this.hDist;
    }
}
