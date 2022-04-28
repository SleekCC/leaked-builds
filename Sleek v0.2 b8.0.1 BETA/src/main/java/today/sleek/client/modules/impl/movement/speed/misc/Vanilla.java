package today.sleek.client.modules.impl.movement.speed.misc;

import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;
import today.sleek.client.utils.player.PlayerUtil;

public class Vanilla extends SpeedMode {

    public Vanilla() {
        super("Vanilla");
    }

    @Override
    public void onUpdate(UpdateEvent event) {

        PlayerUtil.setMotion(getSpeed().getSpeed().getValue());
    }
}
