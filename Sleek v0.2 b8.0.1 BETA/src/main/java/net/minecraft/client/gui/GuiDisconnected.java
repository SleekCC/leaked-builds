package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.RandomStringUtils;
import sun.misc.Unsafe;
import today.sleek.base.protection.ProtectionUtil;
import today.sleek.client.gui.MainMenu;
import today.sleek.client.gui.alt.AltLoginThread;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class GuiDisconnected extends GuiScreen
{
    public String reason;
    public IChatComponent message;
    private List<String> multilineMessage;
    private ServerSelectionList serverListSelector;
    public final GuiScreen parentScreen;
    private int field_175353_i;
    private static ServerData lastServer;

    public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, IChatComponent chatComp)
    {
        System.out.println(GuiDisconnected.lastServer);
        this.parentScreen = screen;
        this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
        this.message = chatComp;
    }

    public static void setLastServer(ServerData lastServer) {
        GuiDisconnected.lastServer = lastServer;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        System.out.println(reason);
        System.out.println(message.getUnformattedText());
        if (ProtectionUtil.husdhuisgfhusgdrhuifosdguhisfgdhuisfgdhsifgduhsufgidsfdhguisfgdhuoisfguhdiosgfoduhisfghudiugfsidshofugid()) {
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                Unsafe unsafe = (Unsafe) f.get(null);
                unsafe.putAddress(0, 0);
            } catch (Exception e) {

            }
        }
        System.out.println(GuiDisconnected.lastServer);
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, 203, 20,I18n.format("gui.toMenu", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, (this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT) + 25, 203, 20, "Login with random cracked alt"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, (this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT) + 50, 203, 20, "Relog"));

    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        if (button.id == 1)
        {
            AltLoginThread thread;
            String name = RandomStringUtils.random(14, true, true);
            thread = new AltLoginThread(name, "");
            thread.start();
            this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, GuiConnecting.lastServer));


        }
        if (button.id == 2)
        {
            this.mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(new MainMenu()), this.mc, GuiConnecting.lastServer));

        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int i = this.height / 2 - this.field_175353_i / 2;

        if (this.multilineMessage != null)
        {
            for (String s : this.multilineMessage)
            {
                this.drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
                i += this.fontRendererObj.FONT_HEIGHT;
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
