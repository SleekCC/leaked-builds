package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import today.sleek.Sleek;
import today.sleek.base.event.impl.PacketEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.modules.impl.player.hackerdetect.checks.Check;
import today.sleek.client.utils.chat.ChatUtil;

import java.util.HashMap;

@ModuleData(name = "Hacker Detect", category = ModuleCategory.PLAYER, description = "Detects Cheaters Useing Client Side AC")
public class HackerDetect extends Module {
    public ModeValue theme = new ModeValue("Theme", this, "Sleek", "Verus", "AGC", "Ghostly");
    private static HackerDetect instance;
    private double cageYValue;
    private HashMap<EntityPlayer, Integer> violations = new HashMap<>();

    public HackerDetect() {
        instance = this;
    }

    public void onEnable() {
        switch (theme.getValue()) {
        case "Sleek": 
            ChatUtil.logNoPrefix("§7[§b§lSleekAC§7] §bAlerts Enabled");
            break;
        case "Verus": 
            ChatUtil.logNoPrefix("§9You are now viewing alerts");
            break;
        case "AGC": 
            ChatUtil.logNoPrefix("§9You are now viewing alerts");
            break;
        case "Ghostly": 
            ChatUtil.logNoPrefix("§9You are now viewing alerts");
            break;
        }
    }

    public void onDisable() {
        switch (theme.getValue()) {
        case "Sleek": 
            ChatUtil.logNoPrefix("§7[§b§lSleekAC§7] §bAlerts Disabled");
            break;
        case "Verus": 
            ChatUtil.logNoPrefix("§9You are no longer viewing alerts");
            break;
        case "AGC": 
            ChatUtil.logNoPrefix("§9You are no longer viewing alerts");
            break;
        case "Ghostly": 
            ChatUtil.logNoPrefix("§9You are no longer viewing alerts");
            break;
        }
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.ticksExisted < 5) violations.clear();
        for (Check c : Sleek.getInstance().getCheckManager().getChecks()) {
            c.onUpdate();
        }
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        for (Check c : Sleek.getInstance().getCheckManager().getChecks()) {
            c.onPacket(event);
        }
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = event.getPacket();
            String msg = packet.getChatComponent().getFormattedText();
            if (msg.contains("Cages open in:")) {
                for (Check c : Sleek.getInstance().getCheckManager().getChecks()) {
                    c.onBlocksMCGameStartTick();
                }
            }
        }
    }

    @SuppressWarnings("all")
    public static HackerDetect getInstance() {
        return HackerDetect.instance;
    }

    @SuppressWarnings("all")
    public double getCageYValue() {
        return this.cageYValue;
    }

    @SuppressWarnings("all")
    public void setCageYValue(final double cageYValue) {
        this.cageYValue = cageYValue;
    }

    @SuppressWarnings("all")
    public HashMap<EntityPlayer, Integer> getViolations() {
        return this.violations;
    }
}
