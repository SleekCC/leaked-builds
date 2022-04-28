package today.sleek.client.modules.impl.movement.speed.misc;

import today.sleek.base.event.impl.MoveEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.player.TimerUtil;
import viamcp.ViaMCP;

public class HycraftTimer extends SpeedMode {

    public HycraftTimer() {
        super("Hycraft (Timer)");
    }

    @Override
    public void onEnable() {
        if (ViaMCP.getInstance().getVersion() != 755) {
            ChatUtil.log("§cYou must use 1.17 with viaversion to use this mode, also make sure you have disabler toggled with the mode Hycraft Timer.");
            getSpeed().toggle();
            return;
        }

        TimerUtil.setTimer(getSpeed().getSpeed().getValue().floatValue());
        mc.gameSettings.keyBindJump.pressed = true;
    }

    @Override
    public void onMove(MoveEvent event) {

    }

    @Override
    public void onDisable() {
        TimerUtil.Reset();

        mc.gameSettings.keyBindJump.pressed = false;
    }
}
