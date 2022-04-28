package today.sleek.client.gui.legacy.clickgui.frame.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import today.sleek.Sleek;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.client.gui.legacy.clickgui.frame.Values;
import today.sleek.client.gui.legacy.clickgui.utils.render.animation.easings.Animate;
import today.sleek.client.gui.legacy.clickgui.utils.render.animation.easings.Easing;
import today.sleek.client.modules.impl.visuals.ClickGUI;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.font.MCFontRenderer;
import today.sleek.client.utils.render.ColorUtils;
import today.sleek.client.utils.render.RenderUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class FrameCategory implements Values {

    // Stuff
    private int x, y, xDrag, yDrag;
    private int width, height;

    private int offset; // Used to scroll

    private boolean drag;

    private final ModuleCategory category;

    private final ArrayList<FrameModule> modules;

    // Smooth animation
    private final Animate animation;

    // Asking x and y so categories are not on themself
    public FrameCategory(ModuleCategory category, int x, int y) {
        this.category = category;
        this.modules = new ArrayList<>();
        this.animation = new Animate().setEase(Easing.CUBIC_OUT).setSpeed(250).setMin(0).setMax(defaultWidth / 2F);

        this.x = x;
        this.y = y;
        this.xDrag = 0;
        this.yDrag = 0;
        this.offset = 0;

        this.drag = false;

        this.width = defaultWidth;
        this.height = defaultHeight;

        Sleek.getInstance().getModuleManager().getModulesFromCategory(category).forEach(module -> this.modules.add(new FrameModule(module, this, 0, 0)));
    }

    public void initGui() {
        this.animation.setSpeed(20000000).reset();
    }

    public void drawScreen(int mouseX, int mouseY) {
        MCFontRenderer font = Fonts.clickGuiFont();
        AtomicInteger offCat = new AtomicInteger();
        this.modules.forEach(module -> offCat.addAndGet(module.getOffset()));

        // Calculate height
        height = Math.min(categoryNameHeight + offCat.get(), defaultHeight);

        if (Mouse.hasWheel() && RenderUtils.hover(x, y, mouseX, mouseY, defaultWidth, height)) {
            int wheel = Mouse.getDWheel();
            if (wheel > 0 && offset - (moduleHeight - 1) > 0) {
                offset -= moduleHeight;
            } else if (wheel < 0 && offset + (moduleHeight - 1) <= offCat.get() - height + categoryNameHeight) {
                offset += moduleHeight;
            }
        }


        // Drawing category base
        Gui.drawRect(getX(), getY(), getX() + width, getY() + getHeight(), mainColor);
        // Drawing category name section
        Gui.drawRect(getX(), getY(), getX() + width, getY() + categoryNameHeight-3, headerColor);
        Gui.drawRect(getX(), getY()+16, getX() + width, (getY() + categoryNameHeight)-2, ColorUtils.getIntGradientOffset(new Color(255, 60, 234), new Color(27, 179, 255), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + 9 / 9 * 9.95));

        // Outline category base
        {
            Gui.drawRect(getX() - outlineWidth, getY(), getX(), getY() + getHeight()-3, darkerMainColor);
            Gui.drawRect(getX() + width, getY(), getX() + width + outlineWidth, getY() + getHeight(), darkerMainColor);
            Gui.drawRect(getX() - outlineWidth, y + getHeight(), getX() + width + outlineWidth, getY() + getHeight() + outlineWidth, darkerMainColor);
        }

        // Drag ClickGUI
        if (drag) {
            setX(this.xDrag + mouseX);
            setY(this.yDrag + mouseY);
        }

        // Drawing category name
        if (((ClickGUI) Sleek.getInstance().getModuleManager().getModuleByName("Click GUI")).fonttoggle.getValue()) {
            font.drawStringWithShadow(category.getName(), x + 3, (int) (y + ((categoryNameHeight / 2F) - 12 / 2F)) +1, -1);
        } else {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(category.getName(), x + 3, (int) (y + ((categoryNameHeight / 2F) - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2F)), -1);
        }

        GL11.glPushMatrix();
        GL11.glEnable(3089);
        RenderUtils.prepareScissorBox(getX() + (width / 2F) - animation.update().getValue(), getY() + categoryNameHeight, x + (width / 2F) + animation.getValue(), y + getHeight());


        // Drawing modules
        int i = 0;
        for (FrameModule module : this.modules) {
            module.setX(x);
            int thing = (y + categoryNameHeight + i - offset);
            module.setY(thing);
            module.drawScreen(mouseX, mouseY);
//            // Outline modules
//            Gui.drawRect(x, thing, x + width, y + categoryNameHeight + i - offset + 0.75, new Color(64,64,64).getRGB());
            i += module.getOffset();
        }

        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        // I really need to explain? yes.
        for (FrameModule module : this.modules) {
            if (module.mouseClicked(mouseX, mouseY, mouseButton)) {
                setDrag(false);
                return;
            }
        }

        if (RenderUtils.hover(x, y, mouseX, mouseY, width, height) && mouseButton == 0) {
            setDrag(true);
            setXDrag(getX() - mouseX);
            setYDrag(getY() - mouseY);
        } else
            setDrag(false);
    }

    @SuppressWarnings("unused")
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.drag = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setXDrag(int xDrag) {
        this.xDrag = xDrag;
    }

    public void setYDrag(int yDrag) {
        this.yDrag = yDrag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void keyTyped(char key, int keyCode) {
        modules.forEach(frameModule -> {
            frameModule.keyTyped(key, keyCode);
        });
    }
}
