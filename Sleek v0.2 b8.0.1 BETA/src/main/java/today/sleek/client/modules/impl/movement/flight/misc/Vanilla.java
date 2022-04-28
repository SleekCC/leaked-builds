package today.sleek.client.modules.impl.movement.flight.misc;

import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.flight.FlightMode;
import today.sleek.client.utils.player.PlayerUtil;

public class Vanilla extends FlightMode {
    public Vanilla() {
        super("Vanilla");
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        double motionY = 0;

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            motionY = getFlight().getSpeed().getValue() / 2;
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            motionY = -(getFlight().getSpeed().getValue() / 2);
        }

        mc.thePlayer.motionY = motionY;
        PlayerUtil.setMotion(getFlight().getSpeed().getValue().floatValue());
    }
}
