package net.minecraft.client.gui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.kansio.client.Client;
import me.kansio.client.gui.MainMenu;
import me.kansio.client.utils.network.HttpUtil;
import negroidslayer.NegroidFarm;
import org.lwjgl.input.Keyboard;
import java.io.IOException;

public class GuiMainMenu extends GuiScreen {
    private GuiTextField username;


    @Override
    protected void actionPerformed(GuiButton button) {
        try {
            if (button.id == 0) {
                Client.getInstance().setUid(username.getText());
                String serv = HttpUtil.get("http://zerotwoclient.xyz:13337/api/v1/getuser?uid=" + Client.getInstance().getUid());
                JsonObject json = new JsonParser().parse(serv).getAsJsonObject();
                if (json.get("uid").getAsString().equals(Client.getInstance().getUid())) {
                    if (json.get("hwid").getAsString().equals(NegroidFarm.guisdafghiusfgfsdhusdfghifsdhuidsfhuifdshuifsdhiudsfhiusfdhsdiuffsdhiudhsifusdfhiufsdhiufsdhiusdfhiufsdhiufsdhiu())) {;
                        Client.getInstance().onStart();
                        Client.getInstance().setUsername(json.get("username").getAsString());
                        Client.getInstance().setDiscordTag(json.get("discordTag").getAsString());
                        Client.getInstance().setRank(json.get("rank").getAsString());
                        mc.displayGuiScreen(new MainMenu());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawScreen(int x, int y2, float z) {
        final FontRenderer font = mc.fontRendererObj;

        drawDefaultBackground();
        username.drawTextBox();
        //drawString(mc.fontRendererObj, "Client has been skidded by vncat", mc.fontRendererObj.getStringWidth("Client has been skidded by vncat") / 2, height - 60, ColorPalette.AMBER.getColor().getRGB());
        this.drawCenteredString(font, "Login", (int) (width / 2F), 20 + 60, -1);

        if (username.getText().isEmpty()) {
            font.drawStringWithShadow("UID", width / 2F - 96, 66 + 60, -7829368);
        }
        super.drawScreen(x, y2, z);
    }

    @Override
    public void initGui() {
        int var3 = height / 4 + 24;
        buttonList.add(new GuiButton(0, width / 2 - 100, 84 + 60, "Login"));
        username = new GuiTextField(var3, mc.fontRendererObj, width / 2 - 100, 60 + 60, 200, 20);
        username.setFocused(true);
        Keyboard.enableRepeatEvents(true);
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
