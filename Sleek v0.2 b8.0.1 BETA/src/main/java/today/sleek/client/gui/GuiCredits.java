package today.sleek.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.glsl.GLSLSandboxShader;
import today.sleek.client.utils.render.ColorPalette;
import today.sleek.client.utils.render.ColorUtils;
import today.sleek.client.utils.render.RenderUtil;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GuiCredits extends GuiScreen {
    private final GuiScreen previousScreen;

    private GLSLSandboxShader backgroundShader;
    private long initTime = System.currentTimeMillis();
    private final int j = Math.round(height / 1.5F);

    /**
     * The x position of this control.
     */
    public int xPosition;

    /**
     * The y position of this control.
     */
    public int yPosition;

    public GuiCredits(GuiScreen previousScreen) {
        try {
            this.backgroundShader = new GLSLSandboxShader("/menu.fsh");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.previousScreen = previousScreen;
    }

    public void initGui() {
        /*
        try {
            this.backgroundShader = new GLSLSandboxShader("")
        } catch (IOException e){
            throw new IllegalStateException("Failed To Load Main Menu Shader");
        }
         */
        int j = height / 4 + 45;
        this.buttonList.add(new GuiButton(0, width / 2 - 70, j + 150, 150, 20, I18n.format("Go Back")));

        initTime = System.currentTimeMillis();
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(previousScreen);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        GlStateManager.enableAlpha();
        GlStateManager.disableCull();
        this.backgroundShader.useShader(width, height, mouseX, mouseY, (System.currentTimeMillis() - initTime) / 1000f);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(-1f, -1f);
        GL11.glVertex2f(-1f, 1f);
        GL11.glVertex2f(1f, 1f);
        GL11.glVertex2f(1f, -1f);

        GL11.glEnd();

        // Unbind shader
        GL20.glUseProgram(0);
        double boxWidth = 400;
        double boxHeight = 200;
        double boxX = this.width / 2 - boxWidth / 2;
        double boxY = this.height / 2 - boxHeight / 2;
        double boxRadius = 5;
        /*
        RenderUtil.drawRoundedRect(this.xPosition + this.width / 2F - 220f, this.yPosition+1, (300 - this.width / 2F)-1, 20,5, new Color(0, 0, 0, 150).getRGB());
                RenderUtil.drawOutlinedRoundedRect(this.xPosition + this.width / 2F - 220f, this.yPosition+1, (300 - this.width / 2F)-1, 20,5,1, ColorUtils.getIntGradientOffset(new Color(255, 60, 234), new Color(27, 179, 255), (Math.abs(((System.currentTimeMillis()) / 20)) / 100D) + 9F / mc.fontRendererObj.FONT_HEIGHT * 9.95));
         */
        RenderUtil.drawRoundedRect(boxX,
                boxY,
                boxWidth,
                boxHeight,
                boxRadius,
                new Color(0, 0, 0, 80).getRGB());
        RenderUtil.drawOutlinedRoundedRect(boxX,
                boxY,
                boxWidth,
                boxHeight,
                boxRadius,
                2,
                ColorUtils.getIntGradientOffset(new Color(255, 60, 234),
                        new Color(27, 179, 255),
                        (Math.abs(((System.currentTimeMillis()) / 20)) / 100D) + 9F / mc.fontRendererObj.FONT_HEIGHT * 9.95));
//        RenderUtil.drawRoundedRect(        boxX, boxY,  boxWidth,  boxHeight,5, 0x80000000);
//        RenderUtil.drawOutlinedRoundedRect(boxX, boxY,  boxWidth , boxHeight,5,2,
        Fonts.Arial40.drawCenteredString("§lS§fleek Credits", (width) / 2f + 4, this.height / 4 +35f, ColorPalette.BLUE.getColor().getRGB()); // -1 = white

//        Fonts.Verdana.drawString(devinfo, (width - Fonts.Arial30.getStringWidth(devinfo)) + 110, height - 10, -1);


        List<String> devInfo = Arrays.asList(
                "We are a development team of retarded people.",
                "",
                "Divine - lead Developer",
                "Kansio - lead developer",
                "Reset - raping main menu",
                "Qoft - a couple features",
                "Nullswap - hypixel and funcraft bypasses",
                "Dort - some bypasses (not dev)",
                "Vince - cool guy (not dev)",
                "Wykt - clickgui base (not dev)"
        );
//        for (String s : devInfo) {
//            devInfo.set(devInfo.indexOf(s), s.toLowerCase());
//        }
        devInfo.sort(Comparator.comparingInt(dev ->
                mc.fontRendererObj.getStringWidth((String) dev)
        ).reversed());

        for (int i = 0; i < devInfo.size(); i++) {
            String x = devInfo.get(i);
            Fonts.Arial25.drawCenteredString(x.toLowerCase(), (width) / 2f + 4, ((this.height / 2 + (i * 25)) / 1.8) + 50, ColorUtils.getIntGradientOffset(new Color(255, 255, 255), new Color(172, 172, 172), (Math.abs(((System.currentTimeMillis()) / 20)) / 100D) + 9F / mc.fontRendererObj.FONT_HEIGHT * 9.95));
        }


        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}