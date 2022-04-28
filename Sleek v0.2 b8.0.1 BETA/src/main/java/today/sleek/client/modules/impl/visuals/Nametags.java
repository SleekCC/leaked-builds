package today.sleek.client.modules.impl.visuals;


import com.google.common.eventbus.Subscribe;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import today.sleek.Sleek;
import today.sleek.base.event.impl.Render3DEvent;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.render.RenderUtil;

import java.awt.*;
import java.util.Objects;

@ModuleData(
        name = "Nametags",
        category = ModuleCategory.VISUALS,
        description = "Shows the nametags of the player"
)

public class Nametags extends Module {

    public BooleanValue health = new BooleanValue("Health", this, true);
    private NumberValue<Double> scaling = new NumberValue<>("Size", this, 2d, 2d, 10d, 0.25);
    public BooleanValue invisibles = new BooleanValue("Invisibles",this,  false);
    public BooleanValue ping = new BooleanValue("Ping", this,true);
    public BooleanValue rect = new BooleanValue("Rectangle",this, true);
    public BooleanValue outline = new BooleanValue("Outline", this, false);
    public BooleanValue sneak = new BooleanValue("SneakColor",this, false);
    public BooleanValue whiter = new BooleanValue("White",this,  false);
    public NumberValue<Double> scaleing = new NumberValue("Scaling", this, 0.1d, 0.1d, 5d, 0.1d);
    public NumberValue<Double> factor = new NumberValue("Factor", this, 1d, 1d, 10d, 0.1d);
    public BooleanValue smartScale = new BooleanValue("SmartScale", this, true);



    @Subscribe
    public void onRender3D(Render3DEvent event) {
        try {
            for (EntityPlayer player : Nametags.mc.theWorld.playerEntities) {
                if (!player.isEntityAlive() || player.isInvisible() && !this.invisibles.getValue()) {
                    continue;
                }
                // check if the player is the same as the rendering player
                if (player == mc.thePlayer) {
                    continue;
                }
                double x = this.interpolate(player.lastTickPosX, player.posX, event.getPartialTicks()) - Nametags.mc.getRenderManager().renderPosX;
                double y = this.interpolate(player.lastTickPosY, player.posY, event.getPartialTicks()) - Nametags.mc.getRenderManager().renderPosY;
                double z = this.interpolate(player.lastTickPosZ, player.posZ, event.getPartialTicks()) - Nametags.mc.getRenderManager().renderPosZ;
                this.renderNameTag(player, x, y -0.2, z, event.getPartialTicks());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void renderNameTag(EntityPlayer player, double x, double y, double z, float delta) {
        double tempY = y;
        tempY += player.isSneaking() ? 0.5 : 0.7;
        Entity camera = mc.getRenderViewEntity();
        assert (camera != null);
        double originalPositionX = camera.posX;
        double originalPositionY = camera.posY;
        double originalPositionZ = camera.posZ;
        camera.posX = this.interpolate(camera.prevPosX, camera.posX, delta);
        camera.posY = this.interpolate(camera.prevPosY, camera.posY, delta);
        camera.posZ = this.interpolate(camera.prevPosZ, camera.posZ, delta);
        String displayTag = this.getDisplayTag(player);
        double distance = camera.getDistance(x + Nametags.mc.getRenderManager().viewerPosX, y + Nametags.mc.getRenderManager().viewerPosY, z + Nametags.mc.getRenderManager().viewerPosZ);
        int width = mc.fontRendererObj.getStringWidth(displayTag) /2;
        double scale = (this.scaling.getValue() * (distance * this.factor.getValue())) / 1100d;
        if (this.scaling.getValue() == null) {
            scale = this.scaling.getValue() / 100.0;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float) x, (float) tempY + 1.4f, (float) z);
        GlStateManager.rotate(-Nametags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(Nametags.mc.getRenderManager().playerViewX, Nametags.mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        GlStateManager.disableBlend();
        mc.fontRendererObj.drawStringWithShadow(displayTag, -width, -(15), this.getDisplayColour(player));
        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }

    private String getDisplayTag(EntityPlayer player) {
        String name = player.getDisplayName().getFormattedText();
        if (name.contains(mc.getSession().getUsername())) {
            name = "You";
        }
        if (!this.health.getValue()) {
            return name;
        }
        float health = (float) round(player.getHealth(), 1);
        return "§7[§f" + Math.round(mc.thePlayer.getDistanceToEntity(player)) + "§7]" + " "+ (Sleek.getInstance().getFriendManager().isFriend(player.getName()) ? "§b" : "§c") + player.getName() + " §7[§f" + health + "§c\u2764§7]";
    }

    private int getDisplayColour(EntityPlayer player) {
        int colour = -5592406;
        if (this.whiter.getValue()) {
            colour = -1;
        }
        if (Sleek.getInstance().getFriendManager().isFriend(player.getName())) {
            return -11157267;
        }
        if (player.isInvisible()) {
            colour = -1113785;
        } else if (player.isSneaking() && this.sneak.getValue()) {
            colour = -6481515;
        }
        return colour;
    }

    private double interpolate(double previous, double current, float delta) {
        return previous + (current - previous) * (double) delta;
    }

    private static double round (float value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}

