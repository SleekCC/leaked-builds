package today.sleek.client.gui.click;

import net.minecraft.client.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import today.sleek.Sleek;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.client.gui.click.components.tab.main.MainButtonTab;
import today.sleek.client.gui.click.tab.Tab;
import today.sleek.client.gui.click.tab.TabConfiguration;
import today.sleek.client.gui.click.tab.cheat.TabDefaultCheat;
import today.sleek.client.gui.click.utility.ExpandAnimation;
import today.sleek.client.gui.click.utils.ColorCreator;
import today.sleek.client.gui.click.utils.Draw;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.math.Stopwatch;

import java.awt.*;
import java.util.*;

public class Interface
{
    private Screen screen;
    private double positionX;
    private double positionY;
    private double width;
    private double height;
    static Minecraft mc;
    private double currentFrameMouseX;
    private double currentFrameMouseY;
    private double prevFrameMouseX;
    private double prevFrameMouseY;
    private boolean moving;
    private boolean closing;
    private final ArrayList<today.sleek.client.gui.click.components.Component> components;
    private final ArrayList<Tab> tabs;
    private Tab currentTab;
    private ExpandAnimation expandAnimation;
    private double alphaModifer;
    private Stopwatch alphaStopwatch;
    private Stopwatch tickStopwatch;
    
    public Interface(final Screen screen) {
        this.width = 300.0;
        this.height = 250.0;
        this.moving = false;
        this.closing = false;
        this.components = new ArrayList<>();
        this.tabs = new ArrayList<>();
        this.expandAnimation = new ExpandAnimation(0.0f, 0.0f);
        this.tickStopwatch = new Stopwatch();
        this.screen = screen;
        this.positionX = (screen.getResolution().getScaledWidth() >> 1) - this.width / 2.0;
        this.positionY = (screen.getResolution().getScaledHeight() >> 1) - this.height / 2.0;
        this.alphaModifer = 0.0;
        this.alphaStopwatch = new Stopwatch();
        this.tabs.add(new TabDefaultCheat(this, ModuleCategory.COMBAT));
        this.tabs.add(new TabDefaultCheat(this, ModuleCategory.MOVEMENT));
        this.tabs.add(new TabDefaultCheat(this, ModuleCategory.VISUALS));
        this.tabs.add(new TabDefaultCheat(this, ModuleCategory.PLAYER));
        this.tabs.add(new TabDefaultCheat(this, ModuleCategory.EXPLOIT));
        this.tabs.add(new TabConfiguration(this));
        final double inc = (this.height - 32.0) / 7.0;
        this.components.add(new MainButtonTab(this, this.tabs.get(0), new ResourceLocation("client/gui/icon/interface/combat_icon.png"), 4.0, 32.0 + inc * 0.0, 20.0, inc, button -> this.currentTab = this.tabs.get(0)));
        this.components.add(new MainButtonTab(this, this.tabs.get(1), new ResourceLocation("client/gui/icon/interface/movement_icon.png"), 4.0, 32.0 + inc * 1.0, 20.0, inc, button -> this.currentTab = this.tabs.get(1)));
        this.components.add(new MainButtonTab(this, this.tabs.get(2), new ResourceLocation("client/gui/icon/interface/visuals_icon.png"), 4.0, 32.0 + inc * 2.0, 20.0, inc, button -> this.currentTab = this.tabs.get(2)));
        this.components.add(new MainButtonTab(this, this.tabs.get(3), new ResourceLocation("client/gui/icon/interface/player_icon.png"), 4.0, 32.0 + inc * 3.0, 20.0, inc, button -> this.currentTab = this.tabs.get(3)));
        this.components.add(new MainButtonTab(this, this.tabs.get(4), new ResourceLocation("client/gui/icon/interface/misc_icon.png"), 4.0, 32.0 + inc * 4.0, 20.0, inc, button -> this.currentTab = this.tabs.get(4)));
        this.components.add(new MainButtonTab(this, this.tabs.get(5), new ResourceLocation("client/gui/icon/interface/configs_icon.png"), 4.0, 32.0 + inc * 5.0, 20.0, inc, button -> this.currentTab = this.tabs.get(5)));
    }
    
    void initializeInterface() {
        this.moving = false;
        this.closing = false;
        this.expandAnimation.setX(0.0f);
        this.expandAnimation.setY(0.0f);
        this.tickStopwatch.resetTime();
    }

    public static Color categoryColor(ModuleCategory cheatCategory) {
        if (cheatCategory == ModuleCategory.VISUALS) {
            return new Color(125, 16,255);
        } else if (cheatCategory == ModuleCategory.MOVEMENT) {
            return new Color(107, 255, 113);
        } else if (cheatCategory == ModuleCategory.COMBAT) {
            return new Color(255, 63, 65);
        } else if (cheatCategory == ModuleCategory.EXPLOIT) {
            return new Color(255, 253, 61);
        } else if (cheatCategory == ModuleCategory.PLAYER) {
            return new Color(255, 187, 79);
        } else {
            return new Color(255, 255, 255);
        }
        //return

    }
    
    public void drawInterface(final double mouseX, final double mouseY) {
        this.currentFrameMouseX = mouseX;
        this.currentFrameMouseY = mouseY;
        if (this.moving) {
            final double differenceX = this.currentFrameMouseX - this.prevFrameMouseX;
            final double differenceY = this.currentFrameMouseY - this.prevFrameMouseY;
            if ((this.positionX + differenceX > 0.0 || differenceX > 0.0) && (this.positionX + this.width + differenceX < this.screen.getResolution().getScaledWidth() || differenceX < 0.0)) {
                this.positionX += differenceX;
            }
            if ((this.positionY + differenceY > 0.0 || differenceY > 0.0) && (this.positionY + this.height + differenceY < this.screen.getResolution().getScaledHeight() || differenceY < 0.0)) {
                this.positionY += differenceY;
            }
        }
        if (this.closing) {
            this.expandAnimation.expand(0.0f, 0.0f, 0.2685f, 0.2385f);
            if (this.alphaStopwatch.timeElapsed(20)) {
                this.alphaModifer -= 0.1;
                if (this.alphaModifer <= 0.0) {
                    this.alphaModifer = 0.0;
                }
                this.alphaStopwatch.resetTime();
            }
        }
        else {
            if (this.alphaStopwatch.timeElapsed(20)) {
                this.alphaModifer += 0.1;
                if (this.alphaModifer > 1.0) {
                    this.alphaModifer = 1.0;
                }
                this.alphaStopwatch.resetTime();
            }
            this.expandAnimation.expand((float)this.width * 2.0f, (float)this.height * 4.0f, 0.04385f, 0.06385f);
        }
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        final double scissorX = this.positionX + this.width / 2.0 - this.expandAnimation.getX();
        final double scissorY = this.screen.getResolution().getScaledHeight() - (this.positionY + this.height + 10.0) + this.height / 2.0 + 5.0 - this.expandAnimation.getY() / 2.0f;
        GL11.glScissor((int)scissorX * this.screen.getResolution().getScaleFactor(), (int)scissorY * this.screen.getResolution().getScaleFactor(), (int)(this.expandAnimation.getX() * 2.0f) * this.screen.getResolution().getScaleFactor(), (int)this.expandAnimation.getY() * this.screen.getResolution().getScaleFactor());
        Draw.drawRectangle(this.positionX - 0.5, this.positionY - 0.5, this.positionX + this.width + 0.5, this.positionY + this.height + 0.5, this.getColor(255, 255, 255, 255));
        Draw.drawRectangle(this.positionX - 1.0, this.positionY - 1.0, this.positionX + this.width + 1.0, this.positionY + this.height + 1.0, this.getColor(255, 255, 255, 255));
        Draw.drawRectangle(this.positionX - 1.5, this.positionY - 1.5, this.positionX + this.width + 1.5, this.positionY + this.height + 1.5, new Color(40, 39, 41, 255).getRGB());

        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        //Draw.drawImg(new ResourceLocation("client/gui/logo/32x32.png"), this.positionX - 4, this.positionY - 2, 32.0, 32.0);
        Draw.drawRectangle(this.positionX, this.positionY + 24.0, this.positionX + 24.0, this.positionY + 25.0, this.getColor(0, 0, 0, 50));
        Draw.drawRectangle(this.positionX + 24.0, this.positionY, this.positionX + 25.5, this.positionY + this.height, this.getColor(0, 0, 0, 50));
        Draw.drawRectangle(this.positionX + 24.0, this.positionY, this.positionX + 25.0, this.positionY + this.height, this.getColor(0, 0, 0, 50));
        Draw.drawRectangle(this.positionX + 24.0, this.positionY, this.positionX + 24.5, this.positionY + this.height, this.getColor(0, 0, 0, 50));
        for (final today.sleek.client.gui.click.components.Component component2 : this.components) {
            if (component2 instanceof MainButtonTab) {
                final MainButtonTab tabButton = (MainButtonTab)component2;
                if (tabButton.getTab() != this.currentTab) {
                    continue;
                }
                int y = 0;
                //Draw.drawRectangle(this.positionX - 2.0, this.positionY + tabButton.positionY - 4.0, this.positionX + 25.0, this.positionY + tabButton.positionY + 21.0, new Color(118, 118, 118, 255).getRGB());
                Draw.drawRectangle(this.positionX - 2.0, this.positionY + tabButton.positionY - 4.0, this.positionX, this.positionY + tabButton.positionY + 21.0, new Color(255, 63, 65, 255).getRGB());
            }
        }
        this.components.forEach(component -> component.drawComponent(this.positionX + component.positionX, this.positionY + component.positionY));
        if (this.currentTab == null) {
            Fonts.Arial15.drawString("§7Logged in as: §b" + Sleek.getInstance().getUsername() + " §7(uid: " + Sleek.getInstance().getUid() + ")", this.positionX + 30.0, this.positionY + 6.0, this.getColor(215, 215, 215));
            Fonts.Arial15.drawString("§7User Type: " + Sleek.getInstance().getRank().getColor() + Sleek.getInstance().getRank().getDisplayName(), this.positionX + 30.0, this.positionY + 16.0, this.getColor(215, 215, 215));
        }
        else {
            this.currentTab.components.forEach(component -> component.drawComponent(this.positionX + component.positionX, this.positionY + component.positionY));
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        if (this.tickStopwatch.timeElapsed(50)) {
            if (this.currentTab != null) {
                this.currentTab.onTick();
            }
            this.tickStopwatch.resetTime();
        }
        this.prevFrameMouseX = this.currentFrameMouseX;
        this.prevFrameMouseY = this.currentFrameMouseY;
    }
    
    void mouseButtonClicked(final int button) {
        if (this.isMouseInBounds(this.positionX, this.positionX + 24.0, this.positionY, this.positionY + 24.0)) {
            this.currentTab = null;
            return;
        }
        for (final today.sleek.client.gui.click.components.Component component : this.components) {
            if (component.mouseButtonClicked(button)) {
                return;
            }
        }
        if (this.currentTab != null) {
            for (final today.sleek.client.gui.click.components.Component component : this.currentTab.components) {
                if (component.mouseButtonClicked(button)) {
                    return;
                }
            }
        }
        this.moving = true;
    }
    
    void mouseButtonReleased(final int state) {
        if (this.moving) {
            this.moving = false;
            return;
        }
        for (final today.sleek.client.gui.click.components.Component component : this.components) {
            component.mouseReleased();
        }
        if (this.currentTab != null) {
            for (final today.sleek.client.gui.click.components.Component component : this.currentTab.components) {
                component.mouseReleased();
            }
        }
    }
    
    void mouseScrolled(final int scrollDirection) {
        for (final today.sleek.client.gui.click.components.Component component : this.components) {
            component.mouseScrolled(scrollDirection);
        }
        if (this.currentTab != null) {
            for (final today.sleek.client.gui.click.components.Component component : this.currentTab.components) {
                component.mouseScrolled(scrollDirection);
            }
        }
    }
    
    public boolean keyTyped(final char typedChar, final int keyCode) {
        for (final today.sleek.client.gui.click.components.Component component : this.components) {
            if (component.keyTyped(typedChar, keyCode)) {
                return true;
            }
        }
        if (this.currentTab != null) {
            for (final today.sleek.client.gui.click.components.Component component : this.currentTab.components) {
                if (component.keyTyped(typedChar, keyCode)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isMouseInBounds(double x1, double x2, double y1, double y2) {
        if (x1 > x2) {
            final double temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 > y2) {
            final double temp = y1;
            y1 = y2;
            y2 = temp;
        }
        return this.currentFrameMouseX > x1 && this.currentFrameMouseX < x2 && this.currentFrameMouseY > y1 && this.currentFrameMouseY < y2;
    }
    
    public int getColor(final int red, final int green, final int blue) {
        return ColorCreator.create(red, green, blue, (int)(255.0 * this.alphaModifer));
    }
    
    public int getColor(final int red, final int green, final int blue, final int alpha) {
        return ColorCreator.create(red, green, blue, (int)(alpha * this.alphaModifer));
    }
    
    public double getPositionX() {
        return this.positionX;
    }
    
    public double getPositionY() {
        return this.positionY;
    }
    
    public double getWidth() {
        return this.width - 24.0;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public boolean isClosing() {
        return this.closing;
    }
    
    public void setClosing(final boolean closing) {
        this.closing = closing;
        if (closing) {
            this.components.forEach(today.sleek.client.gui.click.components.Component::onGuiClose);
            if (this.currentTab != null) {
                this.currentTab.components.forEach(today.sleek.client.gui.click.components.Component::onGuiClose);
            }
        }
    }
    
    public Tab getCurrentTab() {
        return this.currentTab;
    }
    
    public void setWidth(final double width) {
        this.width = width + 24.0;
    }
    
    public double getCurrentFrameMouseX() {
        return this.currentFrameMouseX;
    }
    
    public double getCurrentFrameMouseY() {
        return this.currentFrameMouseY;
    }
}
