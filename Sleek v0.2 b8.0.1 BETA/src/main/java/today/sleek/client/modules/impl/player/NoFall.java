package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import today.sleek.Sleek;
import today.sleek.base.event.impl.BlockCollisionEvent;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.modules.impl.movement.Flight;

@ModuleData(
        name = "No Fall",
        category = ModuleCategory.PLAYER,
        description = "Disables fall damage"
)
public class NoFall extends Module {

    private ModeValue mode = new ModeValue("Mode", this, "Packet", "Spoof", "Collide");

    @Subscribe
    public void onCollide(BlockCollisionEvent event) {
        switch (mode.getValue()) {
            case "Collide": {
                Flight flight = (Flight) Sleek.getInstance().getModuleManager().getModuleByName("Flight");

                if (flight.isToggled()) return;
                if (mc.gameSettings.keyBindSneak.pressed) return;

                if (mc.thePlayer.fallDistance > 2.5) {
                    event.setAxisAlignedBB(new AxisAlignedBB(-2, -1, -2, 2, 1, 2).offset(event.getX(), event.getY(), event.getZ()));
                }
                break;
            }
        }
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (event.isPre() && mc.thePlayer.fallDistance > 2F) {
            switch (mode.getValueAsString()) {
                case "Spoof": {
                    event.setOnGround(true);
                    break;
                }
            }
        }
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        switch (mode.getValueAsString()) {
            case "Packet": {
                if (mc.thePlayer.fallDistance > 2f) {
                    if (event.getPacket() instanceof C03PacketPlayer) {
                        C03PacketPlayer c03 = event.getPacket();
                        c03.onGround = true;
                    }
                }
                break;
            }
        }
    }

    @Override
    public String getSuffix() {
        return " " + mode.getValueAsString();
    }
}
