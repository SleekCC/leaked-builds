package today.sleek.client.gui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import negroidslayer.NegroidFarm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import sun.misc.Unsafe;
import today.sleek.Sleek;
import today.sleek.base.protection.ProtectionUtil;
import today.sleek.client.gui.MainMenu;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.font.MCFontRenderer;
import today.sleek.client.utils.glsl.GLSLSandboxShader;
import today.sleek.client.utils.network.HttpUtil;
import today.sleek.client.utils.render.ColorPalette;

import javax.net.ssl.*;
import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class GuiMainMenu extends GuiScreen {
    private GuiTextField username;
    private GLSLSandboxShader backgroundShader;
    private long initTime = System.currentTimeMillis();

    public GuiMainMenu() {
        try {
            this.backgroundShader = new GLSLSandboxShader("/background.fsh");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        try {
            if (ProtectionUtil.husdhuisgfhusgdrhuifosdguhisfgdhuisfgdhsifgduhsufgidsfdhguisfgdhuoisfguhdiosgfoduhisfghudiugfsidshofugid()) {
                System.out.println("Debugger found, exitting.");
                JOptionPane.showMessageDialog(null, "Please disable any debuggers before running Sleek!", "Error!", JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
                return;
            }

            //tamper protection
            if (!ProtectionUtil.gsudfgyfuisadgfdsouaiygsdeugdsoygfsdhohiusdfhuisdghiudgshiufssfdhiushudsdfuhfdshufdshuisfdhsfdhiusfdhuifsdhuifsdhuisfdhiufsdhiufsdhiusfdhuisfdhuifsdhuifsdhuifsdhiufsdiuhfsdhiufdshuisfdhui()) {
                JOptionPane.showMessageDialog(null, "This version seems to be outdated. Please re-download!", "Error!", JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
                return;
            }
            if (button.id == 0) {
                org.apache.logging.log4j.Logger logger = LogManager.getLogger();
                logger.info("got button shit");
                Map<String, String> header = new HashMap<>();
                HashMap<String, String> map = new HashMap<>();
                map.put("Client-Token", "s59gtK6FCntT6tafCNbyMpQ2");
                Sleek.getInstance().setUid(username.getText());
                logger.info("set uid");
//
//                disableSslVerification();
//
//                logger.info("disabled ssl fuckery");

                System.out.println("requesting");

                String serv = HttpUtil.get("https://api.sleek.today/api/user?hwid=" + NegroidFarm.guisdafghiusfgfsdhusdfghifsdhuidsfhuifdshuifsdhiudsfhiusfdhsdiuffsdhiudhsifusdfhiufsdhiufsdhiusdfhiufsdhiufsdhiu(), map);
                System.out.println("got server shit");
                logger.info(serv);
                JsonObject json = new JsonParser().parse(serv).getAsJsonArray().get(0).getAsJsonObject();
                logger.info("ran json test");
                if (json.get("uid").getAsString().equals(Sleek.getInstance().getUid())) {
                    logger.info("uid pass");
                    if (json.get("hwid").getAsString().equals(NegroidFarm.guisdafghiusfgfsdhusdfghifsdhuidsfhuifdshuifsdhiudsfhiusfdhsdiuffsdhiudhsifusdfhiufsdhiufsdhiusdfhiufsdhiufsdhiu())) {
                        logger.info("run start next");
                        Sleek.getInstance().onStart();
                        logger.info("ran start");
                        mc.displayGuiScreen(new MainMenu());
                        logger.info("main menu");
                        Sleek.getInstance().setUsername(json.get("username").getAsString());
                        Sleek.getInstance().setDiscordTag(String.format("%s#%s", new JsonParser().parse(HttpUtil.get("https://api.sleek.today/api/getdiscordinfo?id=" + json.get("discordID").getAsString())).getAsJsonObject().get("username"), new JsonParser().parse(HttpUtil.get("https://api.sleek.today/api/getdiscordinfo?id=" + json.get("discordID").getAsString())).getAsJsonObject().get("discriminator")));
                        Sleek.getInstance().setRank(json.get("rank").getAsString());
                        logger.info("finish");
                        logger.info("finish");
                    }
                } else {
                    logger.info(serv);
                    System.out.println(serv);
                }
            }
        } catch (Exception e) {

            Logger logger = LogManager.getLogger("launch error");
            logger.error("Launch-Error", e);
        }
        if (ProtectionUtil.husdhuisgfhusgdrhuifosdguhisfgdhuisfgdhsifgduhsufgidsfdhguisfgdhuoisfguhdiosgfoduhisfghudiugfsidshofugid()) {
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                Unsafe unsafe = (Unsafe) f.get(null);
                unsafe.putAddress(0, 0);
            } catch (Exception e) {

            }
        }
    }

    public static boolean isAppInFullScreen() {
        WinDef.HWND foregroundWindow = User32.INSTANCE.GetForegroundWindow();
        WinDef.RECT foregroundRectangle = new WinDef.RECT();
        WinDef.RECT desktopWindowRectangle = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(foregroundWindow, foregroundRectangle);
        WinDef.HWND desktopWindow = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowRect(desktopWindow, desktopWindowRectangle);
        return foregroundRectangle.toString().equals(desktopWindowRectangle.toString());
    }

    @Override
    public void drawScreen(int x, int y2, float z) {
        MCFontRenderer font = Fonts.clickGuiFont();
        GlStateManager.enableAlpha();
        GlStateManager.disableCull();
        this.backgroundShader.useShader(width, height, x, y2, (System.currentTimeMillis() - initTime) / 1000f);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(-1f, -1f);
        GL11.glVertex2f(-1f, 1f);
        GL11.glVertex2f(1f, 1f);
        GL11.glVertex2f(1f, -1f);

        GL11.glEnd();

        // Unbind shader
        GL20.glUseProgram(0);
        username.drawTextBox();
//        drawString(mc.fontRendererObj, "Client has been skidded by vncat", mc.fontRendererObj.getStringWidth("Client has been skidded by vncat") / 2, height - 60, ColorPalette.AMBER.getColor().getRGB());

        Fonts.Arial45.drawCenteredString("§lS", width / 2F - 24, height / 4F - 24, ColorPalette.BLUE.getColor().getRGB());
        Fonts.Arial40.drawCenteredString("leek", width / 2F + 4, height / 4F - 22.5f, -1); // -1 = white
        Fonts.Arial40.drawCenteredString("§lLog", width / 2F - 13, height / 4F - 3.5f, -1); // -1 = white
        Fonts.Arial40.drawCenteredString("in", width / 2F + 17, height / 4F - 2, ColorPalette.BLUE.getColor().getRGB());
        if (username.getText().isEmpty() && !username.isFocused()) {
            font.drawStringWithShadow("UID", width / 2F - 75, height / 2F - 13, -7829368);
        }
        super.drawScreen(x, y2, z);

    }

    @Override
    public void initGui() {
//        mc.displayGuiScreen(new MainMenu());
        int var3 = height / 4 + 24;
        buttonList.add(new GuiButton(0, width / 2 - 50, height / 2 + 10, 100, 20, "Login to Sleek"));
        username = new GuiTextField(var3, mc.fontRendererObj, width / 2 - 80, height / 2 - 20, 150, 20);
        Keyboard.enableRepeatEvents(true);
        initTime = System.currentTimeMillis();
    }

    private void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void keyTyped(char character, int key) {
        username.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x, int y2, int button) {
        try {
            super.mouseClicked(x, y2, button);
        } catch (IOException e) {
            e.printStackTrace();
        }
        username.mouseClicked(x, y2, button);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        username.updateCursorCounter();
    }
}