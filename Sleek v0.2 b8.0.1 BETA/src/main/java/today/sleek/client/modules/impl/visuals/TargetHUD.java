package today.sleek.client.modules.impl.visuals;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import optifine.MathUtils;
import org.lwjgl.opengl.GL11;
import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.ModeValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.modules.impl.visuals.targethud.TargetHudMode;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.java.ReflectUtils;
import today.sleek.client.utils.render.AnimationUtils;
import today.sleek.client.utils.render.RenderUtil;
import today.sleek.client.utils.render.RenderUtils;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.client.gui.Gui.drawScaledCustomSizeModalRect;

@ModuleData(name = "Target HUD", description = "Shows target information", category = ModuleCategory.VISUALS)
public class TargetHUD extends Module {

    private NumberValue<Integer> x = new NumberValue("X-Pos", this, 200, 0, 800, 1);
    private NumberValue<Integer> y = new NumberValue("Y-Pos", this, 200, 0, 800, 1);

    private final List<? extends TargetHudMode> modes = ReflectUtils.getReflects(this.getClass().getPackage().getName() + ".targethud", TargetHudMode.class).stream().map(aClass -> {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }).sorted(Comparator.comparing(mode -> mode != null ? mode.getName() : null)).collect(Collectors.toList());

    private final ModeValue mode = new ModeValue("Mode", this, modes.stream().map(TargetHudMode::getName).collect(Collectors.toList()).toArray(new String[]{}));
    private TargetHudMode currentMode = modes.stream().anyMatch(infoMode -> infoMode.getName().equalsIgnoreCase(mode.getValue())) ? modes.stream().filter(infoMode -> infoMode.getName().equalsIgnoreCase(mode.getValue())).findAny().get() : null;

    public static double currentHealthWidth = (20 * 6.9);
    public static float animation = 0;

    public void render(RenderOverlayEvent event, EntityLivingBase target) {
        try {
            currentMode = modes.stream().anyMatch(infoMode -> infoMode.getName().equalsIgnoreCase(mode.getValue())) ? modes.stream().filter(infoMode -> infoMode.getName().equalsIgnoreCase(mode.getValue())).findAny().get() : null;

            if (currentMode == null) {
                ChatUtil.log("Mode not found, please select another one.");
                return;
            }

            if (mc.currentScreen != null)
                return;

            currentMode.onRender(event, target, x.getValue(), y.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void render(EntityLivingBase ent, float x, float y) {

        Color color;

        GL11.glPushMatrix();
        String playerName = ent.getName();

        String healthStr = Math.round(ent.getHealth() * 10) / 10d + " hp";
        float width = Math.max(75, mc.fontRendererObj.getStringWidth(playerName) + 25);

        //更改TargetHUD在屏幕坐标的初始位置
        GL11.glTranslatef(x, y, 0);
        RenderUtils.drawRoundedRect(0, 0, 58 + width, 28, 2, RenderUtil.reAlpha(0xff000000, 0.6f), 1, RenderUtil.reAlpha(0xff000000, 0.5f));

        mc.fontRendererObj.drawString(playerName, 30, 3, -1);
        mc.fontRendererObj.drawString(healthStr, (int) (52 + width - mc.fontRendererObj.getStringWidth(healthStr) - 2), 4, 0xffcccccc);

        boolean isNaN = Float.isNaN(ent.getHealth());
        float health = isNaN ? 20 : ent.getHealth();
        float maxHealth = isNaN ? 20 : ent.getMaxHealth();
        float healthPercent = MathUtils.clampValue(health / maxHealth, 0, 1);

        int hue = (int) (healthPercent * 120);
        color = Color.getHSBColor(hue / 360f, 0.7f, 1f);

        //RenderUtil.drawRect(37, 14.5f, 26 + width - 2, 17.5f, RenderUtil.reAlpha(1, 0.35f));

        float barWidth = (26 + width - 2) - 37;
        float drawPercent = 37 + (barWidth / 100) * (healthPercent * 100);

        if (animation <= 0) {
            animation = drawPercent;
        }

        if (ent.hurtTime <= 6) {
            animation = AnimationUtils.getAnimationState(animation, drawPercent, (float) Math.max(10, (Math.abs(animation - drawPercent) * 30) * 0.4));
        }

        RenderUtil.drawRect(30, 15.5f, animation, 4.5f, color.darker().getRGB());
        RenderUtil.drawRect(30, 15.5f, drawPercent, 4.5f, color.getRGB());

        //FontManager.icon10.drawString("s", 30f, 13, ColorUtils.WHITE.c);
        //FontManager.icon10.drawString("r", 30f, 20, ColorUtils.WHITE.c);

        float f3 = 37 + (barWidth / 100f) * (ent.getTotalArmorValue() * 5);
        //RenderUtil.drawRect(37, 21.5f, 40 + width - 2, 24.5f, RenderUtil.reAlpha(1, 0.35f));
        RenderUtil.drawRect(30, 21.5f, f3, 4.5f, 0xff4286f5);

        RenderUtils.rectangleBordered(1.5f, 1.5f, 26.5f, 26.5f, 0.5f, 0x00000000, Color.GREEN.getRGB());
        GlStateManager.resetColor();
        for (NetworkPlayerInfo info : GuiPlayerTabOverlay.field_175252_a.sortedCopy(mc.getNetHandler().getPlayerInfoMap())) {
            if (mc.theWorld.getPlayerEntityByUUID(info.getGameProfile().getId()) == ent) {
                mc.getTextureManager().bindTexture(info.getLocationSkin());
                drawScaledCustomSizeModalRect(2f, 2f, 8.0f, 8.0f, 8, 8, 24, 24, 64.0f, 64.0f);

                //drawScaledCustomSizeModalRect(2f, 2f, 40.0f, 8.0f, 8, 8, 24, 24, 64.0f, 64.0f);

                GlStateManager.bindTexture(0);
                break;
            }
        }
        GL11.glPopMatrix();
    }

    private static int getHealthColor(EntityLivingBase player) {
        float f = player.getHealth();
        float f1 = player.getMaxHealth();
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 0.75F) | 0xFF000000;
    }

    private static void drawFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
        try {
            ResourceLocation skin = target.getLocationSkin();
            mc.getTextureManager().bindTexture(skin);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1, 1, 1, 1);
            drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
            GL11.glDisable(GL11.GL_BLEND);
        } catch (Exception ignored) {
        }
    }
}