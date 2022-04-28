package today.sleek.client.utils.font;

import net.minecraft.util.ResourceLocation;
import today.sleek.Sleek;
import today.sleek.client.modules.impl.visuals.ClickGUI;
import today.sleek.client.utils.Util;

import java.awt.*;
import java.util.Objects;

public class Fonts extends Util {

    // Arial Start
    public static final MCFontRenderer Arial50 = new MCFontRenderer(new Font("Arial", Font.BOLD,50),true,true);
    public static final MCFontRenderer Arial45 = new MCFontRenderer(new Font("Arial", Font.BOLD,45),true,true);
    public static final MCFontRenderer Arial40 = new MCFontRenderer(new Font("Arial", Font.BOLD,40),true,true);
    public static final MCFontRenderer Arial35 = new MCFontRenderer(new Font("Arial", Font.BOLD,45),true,true);
    public static final MCFontRenderer Arial30 = new MCFontRenderer(new Font("Arial", Font.BOLD,30),true,true);
    public static final MCFontRenderer Arial25 = new MCFontRenderer(new Font("Arial", Font.BOLD,25),true,true);
    public static final MCFontRenderer Arial20 = new MCFontRenderer(new Font("Arial", Font.BOLD,20),true,true);
    public static final MCFontRenderer Arial20Plain = new MCFontRenderer(new Font("Arial", Font.PLAIN,20),true,true);
    public static final MCFontRenderer Arial18 = new MCFontRenderer(new Font("Arial", Font.PLAIN,20),true,true);
    public static final MCFontRenderer Arial15 = new MCFontRenderer(new Font("Arial", Font.PLAIN,16),true,true);
    public static final MCFontRenderer Arial14 = new MCFontRenderer(new Font("Arial", Font.PLAIN,14),true,true);
    public static final MCFontRenderer Arial12 = new MCFontRenderer(new Font("Arial", Font.PLAIN,12),true,true);
    public static final MCFontRenderer Arial9Plain = new MCFontRenderer(new Font("Arial", Font.PLAIN,8),true,true);
    public static final MCFontRenderer Arial8 = new MCFontRenderer(new Font("Arial", Font.PLAIN,8),true,true);
    // Arial End

    // Verdana Start
    public static final MCFontRenderer Verdana = new MCFontRenderer(new Font("Verdana", Font.PLAIN,18),true,true);
    public static final MCFontRenderer Verdana12 = new MCFontRenderer(new Font("Verdana", Font.PLAIN,12),true,true);
    // Verdana End

    // Courier New Start
    public static final MCFontRenderer CourierNew30 = new MCFontRenderer(new Font("Courier New", Font.BOLD,30),true,true);
    // Courier New Start


    public static MCFontRenderer HUD = clickGuiFont();


    public static final MCFontRenderer SEGOE18 = new MCFontRenderer(new Font("Tahoma", Font.PLAIN,18),true,true);
    public static final MCFontRenderer SEGOE12 = new MCFontRenderer(new Font("Tahoma", Font.PLAIN,12),true,true);

    public static final MCFontRenderer Tahoma13 = new MCFontRenderer(new Font("Tahoma", Font.PLAIN,13),true,true);


    public static final MCFontRenderer NotifIcon = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/notif-icon.ttf"),20,0), true, true);
    public static final MCFontRenderer UbuntuLight = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/Ubuntu-Light.ttf"),12,0), true, true);
    public static final MCFontRenderer UbuntuMedium = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/Ubuntu-Medium.ttf"),18, 0), true, true);
    public static final MCFontRenderer Yantramanav_Regular = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/Yantramanav-Regular.ttf"),18, 0), true, true);

    public static final MCFontRenderer SFRegular = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/sfregular.ttf"),18, 0), true, true);
    public static final MCFontRenderer SFRegular40 = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/sfregular.ttf"),40, 0), true, true);






    private static Font fontFromTTF(ResourceLocation fontLocation, float fontSize, int fontType) {
        System.out.println("Loading font: " + fontLocation.toString() + " with size: " + fontSize + " and type: " + fontType);
        Font output = null;
        try {
            output = Font.createFont(fontType, mc.getResourceManager().getResource(fontLocation).getInputStream());
            output = output.deriveFont(fontSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public static final MCFontRenderer clickGuiVerdana = new MCFontRenderer(new Font("Verdana", Font.PLAIN,18),true,true);
    public static final MCFontRenderer clickGuiLucidaSans = new MCFontRenderer(new Font("Lucida Sans", Font.PLAIN,18),true,true);
    //public static final MCFontRenderer RBRegular = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/rbreg.ttf"),18, 0), true, true);
    public static final MCFontRenderer clickGuiRFRegular = new MCFontRenderer(fontFromTTF(new ResourceLocation("sleek/fonts/sfregular.ttf"),18, 0), true, true);
    public static MCFontRenderer clickGuiFont() {
        try {
             if (Objects.equals(((ClickGUI) Sleek.getInstance().getModuleManager().getModuleByName("Click GUI")).fontmode.getValue(), "Verdana")) {
                return clickGuiVerdana;
            } else if (Objects.equals(((ClickGUI) Sleek.getInstance().getModuleManager().getModuleByName("Click GUI")).fontmode.getValue(), "Lucida Sans")) {
                return clickGuiLucidaSans;
            } else if (Objects.equals(((ClickGUI) Sleek.getInstance().getModuleManager().getModuleByName("Click GUI")).fontmode.getValue(), "SF Regular")) {
                 return clickGuiRFRegular;
            }
            return clickGuiVerdana;
        } catch (Exception e) {
            return clickGuiVerdana;
        }

    }

}
