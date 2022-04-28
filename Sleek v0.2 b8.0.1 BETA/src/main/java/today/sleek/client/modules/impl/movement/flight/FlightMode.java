package today.sleek.client.modules.impl.movement.flight;

import today.sleek.Sleek;
import today.sleek.client.modules.impl.movement.Flight;
import today.sleek.base.event.impl.BlockCollisionEvent;
import today.sleek.base.event.impl.MoveEvent;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.Flight;
import today.sleek.client.utils.Util;

public abstract class FlightMode extends Util {

    private final String name;

    public FlightMode(String name) {
        this.name = name;
    }

    public void onUpdate(UpdateEvent event) {}
    public void onMove(MoveEvent event) {}
    public void onPacket(PacketEvent event) {}
    public void onCollide(BlockCollisionEvent event) {}
    public void onEnable() {}
    public void onDisable() {}

    public String getName() {
        return name;
    }

    public Flight getFlight() {
        return (Flight) Sleek.getInstance().getModuleManager().getModuleByName("Flight");
    }

}
