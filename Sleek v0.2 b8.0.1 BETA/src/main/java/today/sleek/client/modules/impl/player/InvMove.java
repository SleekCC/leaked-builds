package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import org.lwjgl.input.Keyboard;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;

@ModuleData(
        name = "Inventory Move",
        category = ModuleCategory.PLAYER,
        description = "Move whilst having an inventory open"
)
public class InvMove extends Module {

    public ModeValue mode = new ModeValue("Mode", this, "Vanilla", "Verus");
    public BooleanValue noclose = new BooleanValue("No Close", this, true);

    private final KeyBinding[] keyBindings = new KeyBinding[]{
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindJump,
            mc.gameSettings.keyBindSprint
    };

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (mode.getValue().equals("Vanilla")) {
            if (noclose.getValue()) {
                if (event.getPacket() instanceof S2EPacketCloseWindow && (mc.currentScreen instanceof GuiInventory)) {
                    event.setCancelled(true);
                }
                if (mc.currentScreen == null || mc.currentScreen instanceof GuiChat) return;
            }

            for (KeyBinding keyBinding : keyBindings) {
                keyBinding.pressed = Keyboard.isKeyDown(keyBinding.getKeyCode());
            }
        }

    }

}
