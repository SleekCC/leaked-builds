package today.sleek.client.modules.impl.movement;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import today.sleek.Sleek;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.modules.impl.world.Scaffold;

@ModuleData(name = "Sprint", category = ModuleCategory.MOVEMENT, description = "Automatically sprints")
public class Sprint extends Module {

    private ModeValue mode = new ModeValue("Mode", this, "Legit", "Omni");
    private boolean skip;
    private final BooleanValue keepSprint = new BooleanValue("Keep Sprint", this, true); //Handled in NetHandlerPlayerClient at "processEntityAction" and EntityPlayerSP at "setSprinting"

    @Subscribe
    public void onUpdate(UpdateEvent event) {

        Scaffold scaffold = Sleek.getInstance().getModuleManager().getModuleByClass(Scaffold.class);

        if (scaffold.isToggled() && !scaffold.getSprint().getValue()) {
            if (mc.thePlayer.isSprinting()) {
                mc.thePlayer.setSprinting(false);
            }
            return;
        }

        if (mc.thePlayer.isSneaking()) return;
        switch (mode.getValue()) {
            case "Legit":
                if (!skip) {
                    mc.thePlayer.setSprinting(!mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isSneaking() && mc.thePlayer.getFoodStats().getFoodLevel() > 5 && mc.gameSettings.keyBindForward.pressed);
                } else {
                    skip = false;
                }
                break;
            case "Omni":
                if (mc.thePlayer.isMoving() && !mc.thePlayer.isSprinting()) mc.thePlayer.setSprinting(true);
                break;
        }
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (event.getPacketDirection().name().equalsIgnoreCase("INBOUND") && !(event.getPacket() instanceof C03PacketPlayer))
            if (keepSprint.getValue() && event.getPacket() instanceof C0BPacketEntityAction) {
                C0BPacketEntityAction packet = event.getPacket();
                if (((C0BPacketEntityAction) event.getPacket()).getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
                    event.setCancelled(true);
                }
            }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.thePlayer.setSprinting(false);
    }

    @SuppressWarnings("all")
    public BooleanValue getKeepSprint() {
        return this.keepSprint;
    }
}
