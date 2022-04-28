package today.sleek.client.modules.impl.visuals;

import com.google.common.eventbus.Subscribe;
import today.sleek.base.event.impl.BlurEvent;
import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.base.value.value.StringValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.modules.impl.visuals.hud.ArrayListMode;
import today.sleek.client.modules.impl.visuals.hud.InfoMode;
import today.sleek.client.modules.impl.visuals.hud.WaterMarkMode;
import today.sleek.client.utils.java.ReflectUtils;
import today.sleek.client.utils.render.RenderUtils;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ModuleData(name = "HUD", category = ModuleCategory.VISUALS, description = "The HUD... nothing special")
public class HUD extends Module {
    // WaterMark Mode
    private final List<? extends WaterMarkMode> watermarkmodes = ReflectUtils.getReflects(this.getClass().getPackage().getName() + ".hud", WaterMarkMode.class).stream().map(aClass -> {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }).sorted(Comparator.comparing(watermarkMode -> watermarkMode != null ? watermarkMode.getName() : null)).collect(Collectors.toList());
    private final ModeValue watermarkmode = new ModeValue("Watermark Mode", this, watermarkmodes.stream().map(WaterMarkMode::getName).collect(Collectors.toList()).toArray(new String[]{}));
    private WaterMarkMode currentwatermarkmode = watermarkmodes.stream().anyMatch(watermarkMode -> watermarkMode.getName().equalsIgnoreCase(watermarkmode.getValue())) ? watermarkmodes.stream().filter(watermarkMode -> watermarkMode.getName().equalsIgnoreCase(watermarkmode.getValue())).findAny().get() : null;
    // ArrayList Mode
    private final List<? extends ArrayListMode> arraylistmodes = ReflectUtils.getReflects(this.getClass().getPackage().getName() + ".hud", ArrayListMode.class).stream().map(aClass -> {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }).sorted(Comparator.comparing(arraylistMode -> arraylistMode != null ? arraylistMode.getName() : null)).collect(Collectors.toList());
    private final ModeValue arraylistmode = new ModeValue("Arraylist Mode", this, arraylistmodes.stream().map(ArrayListMode::getName).collect(Collectors.toList()).toArray(new String[]{}));
    private ArrayListMode currentarraylistmode = arraylistmodes.stream().anyMatch(arraylistMode -> arraylistMode.getName().equalsIgnoreCase(arraylistmode.getValue())) ? arraylistmodes.stream().filter(arraylistMode -> arraylistMode.getName().equalsIgnoreCase(arraylistmode.getValue())).findAny().get() : null;
    // Info Mode
    private final List<? extends InfoMode> infomodes = ReflectUtils.getReflects(this.getClass().getPackage().getName() + ".hud", InfoMode.class).stream().map(aClass -> {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }).sorted(Comparator.comparing(infoMode -> infoMode != null ? infoMode.getName() : null)).collect(Collectors.toList());
    private final ModeValue infomode = new ModeValue("Info Mode", this, infomodes.stream().map(InfoMode::getName).collect(Collectors.toList()).toArray(new String[]{}));
    private InfoMode currentinfomode = infomodes.stream().anyMatch(infoMode -> infoMode.getName().equalsIgnoreCase(infomode.getValue())) ? infomodes.stream().filter(infoMode -> infoMode.getName().equalsIgnoreCase(infomode.getValue())).findAny().get() : null;
    private final ModeValue colorMode = new ModeValue("Color Mode", this, "Sleek", "Rainbow", "Astolfo", "Nitrogen", "Gradient", "Wave", "Static");
    //wave
    private final NumberValue topRed = new NumberValue("Top Red", this, 255, 0, 255, 1, colorMode, "Gradient");
    private final NumberValue topGreen = new NumberValue("Top Green", this, 255, 0, 255, 1, colorMode, "Gradient");
    private final NumberValue topBlue = new NumberValue("Top Blue", this, 255, 0, 255, 1, colorMode, "Gradient");
    private final NumberValue bottomRed = new NumberValue("Bottom Red", this, 255, 0, 255, 1, colorMode, "Gradient");
    private final NumberValue bottomGreen = new NumberValue("Bottom Green", this, 255, 0, 255, 1, colorMode, "Gradient");
    private final NumberValue bottomBlue = new NumberValue("Bottom Blue", this, 255, 0, 255, 1, colorMode, "Gradient");
    //wave and static
    private final NumberValue staticRed = new NumberValue("Red", this, 255, 0, 255, 1, colorMode, "Wave", "Static");
    private final NumberValue staticGreen = new NumberValue("Green", this, 255, 0, 255, 1, colorMode, "Wave", "Static");
    private final NumberValue staticBlue = new NumberValue("Blue", this, 255, 0, 255, 1, colorMode, "Wave", "Static");

    public ModeValue line = new ModeValue("Line", this, "None", "Top", "Wrapped", "Side");

    public final NumberValue<Integer> bgalpha = new NumberValue<>("Alpha", this, 80, 1, 200, 1);
    public BooleanValue font = new BooleanValue("Font", this, false);
    public BooleanValue noti = new BooleanValue("Notifications", this, true);
    public BooleanValue hideRender = new BooleanValue("Hide Render", this, true);
    public StringValue clientName = new StringValue("Client Name", this, "Sleek");
    public StringValue listSuffix = new StringValue("Module Suffix", this, " [%s]");
    public NumberValue arrayListY = new NumberValue("ArrayList Y", this, 4, 1, 200, 1);
    public NumberValue arrayListX = new NumberValue("ArrayList X", this, 4, 1, 200, 1, arraylistmode, "Left");
    private final ModeValue scoreboardLocation = new ModeValue("Scoreboard", this, "Right", "Left");
    private final NumberValue<Double> scoreboardPos = new NumberValue<>("Scoreboard Y", this, 0.0, -500.0, 500.0, 1.0);
    public static boolean notifications;

    @Override
    public void onEnable() {
        currentwatermarkmode = watermarkmodes.stream().anyMatch(watermarkMode -> watermarkMode.getName().equalsIgnoreCase(watermarkmode.getValue())) ? watermarkmodes.stream().filter(watermarkMode -> watermarkMode.getName().equalsIgnoreCase(watermarkmode.getValue())).findAny().get() : null;
        currentarraylistmode = arraylistmodes.stream().anyMatch(arraylistMode -> arraylistMode.getName().equalsIgnoreCase(arraylistmode.getValue())) ? arraylistmodes.stream().filter(arraylistMode -> arraylistMode.getName().equalsIgnoreCase(arraylistmode.getValue())).findAny().get() : null;
        currentinfomode = infomodes.stream().anyMatch(infoMode -> infoMode.getName().equalsIgnoreCase(infomode.getValue())) ? infomodes.stream().filter(infoMode -> infoMode.getName().equalsIgnoreCase(infomode.getValue())).findAny().get() : null;
        currentwatermarkmode.onEnable();
        currentarraylistmode.onEnable();
        currentinfomode.onEnable();
    }

    @Override
    public void onDisable() {
        currentwatermarkmode.onDisable();
        currentarraylistmode.onDisable();
        currentinfomode.onDisable();
    }

    @Subscribe
    public void onRenderOverlay(RenderOverlayEvent event) {
        try {
            currentwatermarkmode = watermarkmodes.stream().anyMatch(watermarkMode -> watermarkMode.getName().equalsIgnoreCase(watermarkmode.getValue())) ? watermarkmodes.stream().filter(watermarkMode -> watermarkMode.getName().equalsIgnoreCase(watermarkmode.getValue())).findAny().get() : null;
            currentarraylistmode = arraylistmodes.stream().anyMatch(arraylistMode -> arraylistMode.getName().equalsIgnoreCase(arraylistmode.getValue())) ? arraylistmodes.stream().filter(arraylistMode -> arraylistMode.getName().equalsIgnoreCase(arraylistmode.getValue())).findAny().get() : null;
            currentinfomode = infomodes.stream().anyMatch(infoMode -> infoMode.getName().equalsIgnoreCase(infomode.getValue())) ? infomodes.stream().filter(infoMode -> infoMode.getName().equalsIgnoreCase(infomode.getValue())).findAny().get() : null;
            assert currentwatermarkmode != null;
            currentwatermarkmode.onRenderOverlay(event);
            currentarraylistmode.onRenderOverlay(event);
            currentinfomode.onRenderOverlay(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onBlur(BlurEvent event) {
        RenderUtils.drawRoundedRect(30, 30, 50, 50, 2, new Color(0, 0, 0, 90).getRGB());
    }

    @SuppressWarnings("all")
    public List<? extends WaterMarkMode> getWatermarkmodes() {
        return this.watermarkmodes;
    }

    @SuppressWarnings("all")
    public ModeValue getWatermarkmode() {
        return this.watermarkmode;
    }

    @SuppressWarnings("all")
    public WaterMarkMode getCurrentwatermarkmode() {
        return this.currentwatermarkmode;
    }

    @SuppressWarnings("all")
    public List<? extends ArrayListMode> getArraylistmodes() {
        return this.arraylistmodes;
    }

    @SuppressWarnings("all")
    public ModeValue getArraylistmode() {
        return this.arraylistmode;
    }

    @SuppressWarnings("all")
    public ArrayListMode getCurrentarraylistmode() {
        return this.currentarraylistmode;
    }

    @SuppressWarnings("all")
    public List<? extends InfoMode> getInfomodes() {
        return this.infomodes;
    }

    @SuppressWarnings("all")
    public ModeValue getInfomode() {
        return this.infomode;
    }

    @SuppressWarnings("all")
    public InfoMode getCurrentinfomode() {
        return this.currentinfomode;
    }

    @SuppressWarnings("all")
    public ModeValue getColorMode() {
        return this.colorMode;
    }

    @SuppressWarnings("all")
    public NumberValue getTopRed() {
        return this.topRed;
    }

    @SuppressWarnings("all")
    public NumberValue getTopGreen() {
        return this.topGreen;
    }

    @SuppressWarnings("all")
    public NumberValue getTopBlue() {
        return this.topBlue;
    }

    @SuppressWarnings("all")
    public NumberValue getBottomRed() {
        return this.bottomRed;
    }

    @SuppressWarnings("all")
    public NumberValue getBottomGreen() {
        return this.bottomGreen;
    }

    @SuppressWarnings("all")
    public NumberValue getBottomBlue() {
        return this.bottomBlue;
    }

    @SuppressWarnings("all")
    public NumberValue getStaticRed() {
        return this.staticRed;
    }

    @SuppressWarnings("all")
    public NumberValue getStaticGreen() {
        return this.staticGreen;
    }

    @SuppressWarnings("all")
    public NumberValue getStaticBlue() {
        return this.staticBlue;
    }

    @SuppressWarnings("all")
    public ModeValue getLine() {
        return this.line;
    }

    @SuppressWarnings("all")
    public NumberValue<Integer> getBgalpha() {
        return this.bgalpha;
    }

    @SuppressWarnings("all")
    public BooleanValue getFont() {
        return this.font;
    }

    @SuppressWarnings("all")
    public BooleanValue getNoti() {
        return this.noti;
    }

    @SuppressWarnings("all")
    public BooleanValue getHideRender() {
        return this.hideRender;
    }

    public StringValue getClientName() {
        return this.clientName;
    }

    @SuppressWarnings("all")
    public StringValue getListSuffix() {
        return this.listSuffix;
    }

    @SuppressWarnings("all")
    public NumberValue getArrayListY() {
        return this.arrayListY;
    }

    @SuppressWarnings("all")
    public ModeValue getScoreboardLocation() {
        return this.scoreboardLocation;
    }

    @SuppressWarnings("all")
    public NumberValue<Double> getScoreboardPos() {
        return this.scoreboardPos;
    }
}
