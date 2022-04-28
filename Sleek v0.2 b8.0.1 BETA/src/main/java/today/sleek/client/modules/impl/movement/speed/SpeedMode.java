package today.sleek.client.modules.impl.movement.speed;

import today.sleek.Sleek;
import today.sleek.base.event.impl.MoveEvent;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.Speed;
import today.sleek.client.utils.Util;

public abstract class SpeedMode extends Util {

    private final String name;

    public SpeedMode(String name) {
        this.name = name;
    }

    public void onUpdate(UpdateEvent event) {}
    public void onMove(MoveEvent event) {}
    public void onPacket(PacketEvent event) {}
    public void onEnable() {}
    public void onDisable() {}

    public String getName() {
        return name;
    }

    public Speed getSpeed() {
        return (Speed) Sleek.getInstance().getModuleManager().getModuleByName("Speed");
    }

}
