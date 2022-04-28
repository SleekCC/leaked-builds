package today.sleek.client.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.base.event.impl.UpdateEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.render.ColorUtils;
import today.sleek.client.utils.render.RenderUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleData(name = "Staff Detector", category = ModuleCategory.EXPLOIT, description = "Checks if there\'s a staff member in your game")
public class StaffDetect extends Module {
    private BooleanValue announce = new BooleanValue("Announce", this, true);
    private BooleanValue automaticallyLeave = new BooleanValue("Automatically Leave", this, true);
    private BooleanValue notifyWhenLeave = new BooleanValue("Notify When Leave", this, true);
    private ArrayList<String> staffInMatch = new ArrayList<>();
    private List<String> verusKnownStaff = Arrays.asList("iDhoom", "Jinaaan", "AntiGamingChair", "iiDaykel", "_JustMix", "Eissaa", "1Mhmmd", "mohmad_q8", "1Brhom", "Aliiyah", "AssassinTime", "PerfectRod_", "Ahmmd", "xImTaiG_", "xIBerryPlayz", "comsterr", "1Sweet", "1Hussain", "Ev2n", "1M7mdz", "iMehdi_", "EyesO_Diamond", "xMz7", "1Daykel", "Aboz3bl", "Nwwf", "Luvaa", "Boviix", "Muntadher", "BinRashood", "ixBander", "ZANAD", "WalriderTime", "Thenvra", "CutieRana", "MK_F16", "saad6", "iiRaivy", "Onyc_", "iMrOt", "baderr", "M7mmd", "leeleeeleeeleee", "Bastic", "iikimo", "Creegam", "iS3od_", "xanaxjuice", "M4rwaan", "iiM7mdZ_", "1M7mmD", "Yaazzeed", "1LaB", "3Mmr", "Aymann_", "ebararh", "1Narwhql", "Y2men", "1Cloud_", "Faariss", "ImAhMeeD", "i_LrX", "Refolt", "MightyM7MD", "Dizibre", "KaaReeeM", "AssassinLove", "RADVN", "DetectiveFahad", "Sadlly", "ritclaw", "TheDaddyJames", "Stay1High", "420syr1a", "_N3", "Zqvies", "needhiim", "FaRidok", "Requieem", "1RealFadi", "1ELY_", "xL2d", "zMhnD", "Tibbz_BGamer", "1Tz3bo", "Abdulaziz187", "1Mshari", "1Pepe_", "N29r", "PT7", "D1ev", "ImXann", "m7mdxjw", "27bk", "1Neres", "A4kat1", "1_ST", "FastRank", "arbawii", "JustDrink_", "Tibbz_BGamer_", "xIMonster_Rj", "SalemBayern_", "iSolom", "0hPqnos", "smckingweed", "1ForAGer", "MrProfessor_T", "mzh", "0Da3s", "iRxv", "123k1k", "ogloo_", "Banderr", "w7r", "S3rvox", "PotatoSoublaki", "3AmOdi_", "lt1x", "Fta7", "Rwxa1", "withoutyouu", "vdhvm", "ilybb0", "_Vxpe", "GoDstRyze", "1M0ha", "KingHOYT", "502z", "_R3", "Jrx7", "TheDrag_Xx", "Tostiebramkaas", "iA11", "Ravnly", "svazSauD", "vxom", "1ASSASSINATION_", "BlackOurs", "1GuardiaN", "Sanfoor_J", "iAhmedGG", "Ba1z", "yQuack", "Ba6ee5man", "DemonsBra2", "Khanful", "91l7", "DaBabyFan", "0nlyHazey", "ShesFallingAPart", "DetectiveFoxTY", "1LoST_", "iQlD", "ImMajesty", "Blood_Artz", "xa7a", "OREOBZ", "lud4o", "i_Cj", "AbuA7md506", "AbduIlah", "rkqx", "OzDark_", "1A7MD_PvP", "Veshan", "Demon_001", "ToFy_", "DarkA5_", "FexoraNEP", "1Adam1_", "xsatoo", "OldAlone", "vinnythebot", "1Ab0oDx", "1A7mad1", "1Loga_", "0h_Roby", "Raceth", "BigZ3tr", "1RE3", "Mark_Gamer_YT_", "megtitimade1", "yosife_7Y", "N_AR_K", "QxRIsBack", "wzii", "0PvP_", "BaSiL_123", "RealA7md", "whyamisoinlove", "alshe5_steve", "itzZa1D", "AfootDiamond117", "be6sho", "samorayxs", "mokgii", "swazKweng", "rivez", "ImM7MAD", "1Sweatly", "1Levaai", "Mondoros", "Mervy_", "JustKreem", "wl3d", "1Sinqx", "c22l", "_Cignus_", "mliodse", "_Surfers_", "1Mqzn_", "Mythiques", "manuelmaster", "hussien_gg", "1_aq", "SekErkekEvladi", "uHyp_v2", "SpecialAdam_", "Ihaveatrashaim", "Sp0tzy_", "Enormities", "SirMedo_", "G3rryx", "xiiRadi", "M8DaM", "IR3DX", "stepmixer", "Z4_R0", "MeeDoo_", "uh8e", "Just7MO", "xXBXLTXx", "vSL0W_", "Fluege", "s2lm", "Lunching", "xLePerfect", "D1ZZY0NE", "LRYD", "Tabby_Bhau", "Violeet", "3au", "MY_PRO_ITS_MAX", "aXav", "Competely", "SurFzSb3", "3rodi", "INFAMOUSEEE", "RealWayne", "sh5boo6", "EZKarDIOPalma", "hr5_", "Draggn_", "TheOnlyM7MAD");
    private int amount;

    @Subscribe
    public void onTick(UpdateEvent event) {
        if (mc.thePlayer.ticksExisted > 5) {
            for (EntityPlayer player : mc.theWorld.playerEntities) {
                if (staffInMatch.contains(player.getName())) {
                    continue;
                }
                for (String staff : verusKnownStaff) {
                    if (player.getName().contains(staff)) {
                        staffInMatch.add(player.getName());
                        amount = staffInMatch.size();
                        ChatUtil.logNoPrefix("§4§l[Staff Detect]: §c" + staff + " §fis in your game!");
                        if (announce.getValue()) mc.thePlayer.sendChatMessage("[Sleek Staff Detector] Found a staff member in the lobby: " + staff);
                        if (automaticallyLeave.getValue()) {
                            mc.thePlayer.sendChatMessage("/lobby");
                            if (notifyWhenLeave.getValue()) {
                                ChatUtil.log("   ");
                                ChatUtil.log("§4§l[Staff Detect] §fAutomatically left the game.");
                                ChatUtil.log("   ");
                            }
                        }
                    }
                }
            }
        } else {
            staffInMatch.clear();
            amount = 0;
        }
    }

    @Subscribe
    public void onRender(RenderOverlayEvent event) {
        ScaledResolution sr = RenderUtils.getResolution();
        if (staffInMatch.size() != 0 && amount != 0) {
            RenderUtils.drawRoundedRect(sr.getScaledWidth() / 2 - 80, sr.getScaledHeight() / 2 + 130, 167, 40, 5, new Color(0, 0, 0, 100).getRGB());
            RenderUtils.drawRect(sr.getScaledWidth() / 2 - 80, sr.getScaledHeight() / 2 + 129, 167, 3, ColorUtils.getGradientOffset(new Color(255, 0, 0), new Color(104, 0, 0), (Math.abs(((System.currentTimeMillis()) / 10)) / 50.0) + 9 / mc.fontRendererObj.FONT_HEIGHT * 9.95).getRGB());
            if (amount > 1) {
                mc.fontRendererObj.drawStringWithShadow("§4§lWarning:", sr.getScaledWidth() / 2 - 76, sr.getScaledHeight() / 2 + 140, -1);
                mc.fontRendererObj.drawStringWithShadow("§f" + amount + " §cStaff members §fwere detected!", sr.getScaledWidth() / 2 - 75, sr.getScaledHeight() / 2+155, -1);
            } else {
                mc.fontRendererObj.drawStringWithShadow("§4§lWarning:", sr.getScaledWidth() / 2 - 70, sr.getScaledHeight() / 2 + 136, -1);
                mc.fontRendererObj.drawStringWithShadow("§fA §cStaff member §fwas detected!", sr.getScaledWidth() / 2 - 69, sr.getScaledHeight() / 2+150, -1);
            }
        }
    }

    @SuppressWarnings("all")
    public ArrayList<String> getStaffInMatch() {
        return this.staffInMatch;
    }
}
