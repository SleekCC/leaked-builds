package today.sleek.client.gui;

import today.sleek.client.gui.alt.GuiAltManager;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.glsl.GLSLSandboxShader;
import today.sleek.client.utils.render.ColorPalette;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.IOException;

public class MainMenu extends GuiScreen {

    private static final ResourceLocation BACKGROUND = new ResourceLocation("sleek/images/background.png");
    private GLSLSandboxShader backgroundShader;
    private long initTime = System.currentTimeMillis();
    private final int j = Math.round(height / 1.5F);

    public MainMenu() {
        try {
            this.backgroundShader = new GLSLSandboxShader("/menu.fsh");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        int i = 24;
        this.buttonList.add(new GuiButton(0, width / 2 - 50, j - 25, 100, 20, I18n.format("menu.singleplayer")));
        this.buttonList.add(new GuiButton(1, width / 2 - 50, j + i - 25, 100, 20, I18n.format("menu.multiplayer")));
        this.buttonList.add(new GuiButton(2, width / 2 - 50, j + i * 2 - 25, 100, 20, "Alt Manager"));
        this.buttonList.add(new GuiButton(3, width / 2 - 50, j + i * 2, 100, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(4, width / 2 - 50, j + i * 2 + 25, 100, 20, I18n.format("menu.quit")));
        this.buttonList.add(new GuiButton(5, width / 2 - 50, j + i * 2 + 50, 100, 20, "Credits"));

        initTime = System.currentTimeMillis();
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiAltManager(this));
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 4:
                this.mc.shutdown();
                break;
            case 5:
                this.mc.displayGuiScreen(new GuiCredits(this));
                break;
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
        Fonts.SFRegular40.drawCenteredString("§lS§fleek", width / 2, height / 4 - 24, ColorPalette.BLUE.getColor().getRGB());
//        Fonts.Arial40.drawCenteredString("", width / 2 +8, height / 4 -22.5f, -1); // -1 = white
//        Fonts.Verdana.drawString(devinfo, (width - Fonts.Arial30.getStringWidth(devinfo)) + 110, height - 10, -1);
        String devinfo = "Made with <3 by Reset, Kansio, nullswap, Divine and qoft";
        Fonts.SFRegular.drawCenteredString(devinfo, width - 140, height - 10, -1);
        Fonts.SFRegular.drawString("Sleek 0.2 b8.0.1 (032522)", 0, height - 10, -1);
        String text = "hi";
//        Fonts.Verdana.drawString(devinfo, (width - Fonts.Arial30.getStringWidth(devinfo)) + 135, height - 10, -1);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}