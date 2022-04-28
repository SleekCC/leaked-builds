package today.sleek.client.gui.legacy.clickgui.frame.components;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import today.sleek.Sleek;
import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.base.value.value.NumberValue;
import today.sleek.base.value.value.StringValue;
import today.sleek.client.gui.legacy.clickgui.frame.Values;
import today.sleek.client.gui.legacy.clickgui.frame.components.impl.BoolSetting;
import today.sleek.client.gui.legacy.clickgui.frame.components.impl.EnumSetting;
import today.sleek.client.gui.legacy.clickgui.frame.components.impl.SlideSetting;
import today.sleek.client.gui.legacy.clickgui.frame.components.impl.StringSetting;
import today.sleek.client.gui.legacy.clickgui.utils.render.animation.easings.Animate;
import today.sleek.client.gui.legacy.clickgui.utils.render.animation.easings.Easing;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.modules.impl.visuals.ClickGUI;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.font.Fonts;
import today.sleek.client.utils.font.MCFontRenderer;
import today.sleek.client.utils.render.ColorPalette;
import today.sleek.client.utils.render.ColorUtils;
import today.sleek.client.utils.render.RenderUtils;

import java.awt.*;
import java.util.ArrayList;

public class FrameModule implements Values {
    private final Module module;
    private final ArrayList<Component> components;

    private final FrameCategory owner;

    private final Animate moduleAnimation;

    private int x, y;
    private int offset;

    private boolean opened;
    private boolean listening;

    public FrameModule(Module module, FrameCategory owner, int x, int y) {
        this.module = module;
        this.components = new ArrayList<>();
        this.owner = owner;
        this.moduleAnimation = new Animate();
        moduleAnimation.setMin(0).setMax(255).setReversed(!module.isToggled()).setEase(Easing.LINEAR);
        this.opened = false;
        this.listening = false;
        this.x = x;
        this.y = y;


        /*/if(module.())
        {/*/


        Sleek.getInstance().getValueManager().getValuesFromOwner(module).forEach(setting ->
        {
            if (setting instanceof BooleanValue) {
                this.components.add(new BoolSetting(0, 0, this, setting));
            }
            if (setting instanceof ModeValue) {
                this.components.add(new EnumSetting(0, 0, this, setting));
            }
            if (setting instanceof NumberValue) {
                this.components.add(new SlideSetting(0, 0, this, setting));
            }
            if (setting instanceof StringValue) {
                this.components.add(new StringSetting(0, 0, this, setting));
            }
        });
        //}
    }

    public void drawScreen(int mouseX, int mouseY) {
        MCFontRenderer font = Fonts.clickGuiFont();
        moduleAnimation.setReversed(!module.isToggled());
        moduleAnimation.setSpeed(((ClickGUI) Sleek.getInstance().getModuleManager().getModuleByName("Click GUI")).animspeed.getValue() * 10).update();
        int colorUsed;
        if (((ClickGUI) Sleek.getInstance().getModuleManager().getModuleByName("Click GUI")).rainbow.getValue()) {
            colorUsed = ColorUtils.getIntGradientOffset(new Color(255, 60, 234), new Color(27, 179, 255), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + 1 * 9.95);
        } else {
            colorUsed = new Color(139, 139, 139, 76).getRGB();
        }


        if(RenderUtils.hover(x, y, mouseX, mouseY, defaultWidth, moduleHeight)) {
            GuiScreen.drawRect(x,y, x + defaultWidth, y + moduleHeight, new Color(ColorPalette.LIGHT_BLUE.getColor().getRed(), ColorPalette.LIGHT_BLUE.getColor().getGreen(), ColorPalette.LIGHT_BLUE.getColor().getBlue(), 120).getRGB());
        }

        if (module.isToggled() || (moduleAnimation.isReversed() && moduleAnimation.getValue() != 0)) {
            GuiScreen.drawRect(x, y, x + defaultWidth, y + moduleHeight, ColorUtils.setAlpha(new Color(colorUsed), (int) moduleAnimation.getValue()).darker().getRGB());
        }
        try {
            if (((ClickGUI) Sleek.getInstance().getModuleManager().getModuleByName("Click GUI")).fonttoggle.getValue()) {
                font.drawString(listening ? "Press new keybind" : module.getName(), x + 3, y + (moduleHeight / 2F - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2F))+1.2, 0xFFFFFFFF);
            } else {
                Minecraft.getMinecraft().fontRendererObj.drawString(listening ? "Press new keybind" : module.getName(), x + 3, y + (moduleHeight / 2F - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2F)), 0xFFFFFFFF, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Minecraft.getMinecraft().fontRendererObj.drawString(listening ? "Press new keybind" : module.getName(), x + 3, y + (moduleHeight / 2F - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2F)),  0xFFFFFFFF, true);
        }
        if (!module.getValues().isEmpty()) {
            if (((ClickGUI) Sleek.getInstance().getModuleManager().getModuleByName("Click GUI")).fonttoggle.getValue()) {
                font.drawString(opened ? "-" : "+", (x + owner.getWidth()) - 9, y + (moduleHeight / 2F - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2F))+1.2,  0xFFFFFFFF, true);
            } else {
                Minecraft.getMinecraft().fontRendererObj.drawString(opened ? "-" : "+", (x + owner.getWidth()) - 9, y + (moduleHeight / 2F - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2F)),  0xFFFFFFFF, true);

            }
        }
//            if (((ClickGUI)Client.getInstance().getModuleManager().getModuleByName("ClickGUI")).fonttoggle.getValue()) {
//                Fonts.Verdana.drawString(opened ? "-" : "+", (x + owner.getWidth()) - 9, y + (moduleHeight / 2F - (9 / 2F)), stringColor, true);
//            } else {
//                Minecraft.getMinecraft().fontRendererObj.drawString(opened ? "-" : "+", (x + owner.getWidth()) - 9, y + (moduleHeight / 2F - (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2F)), stringColor, true);
//            }
        int offset = 0;

        if (opened) {
            for (Component component : this.components) { // using for loop because continue isn't supported on foreach
                //component.getSetting().constantCheck();
                //if(component.getSetting().isHide()) continue;
                boolean shouldBeHidden;
                if (component.getSetting().hasParent() && component.getSetting().getParent() != null) {
                    if ((component.getSetting().getParent() instanceof ModeValue && component.getSetting().getModes().contains(component.getSetting().getParent().getValue()))
                            || (component.getSetting().getParent() instanceof BooleanValue && ((BooleanValue) component.getSetting().getParent()).getValue())) {
                        shouldBeHidden = false;
                    } else {
                        shouldBeHidden = true;
                    }
                } else {
                    shouldBeHidden = false;
                }
                component.setHidden(shouldBeHidden);

                if (component.isHidden()) continue;
                component.setX(x);
                component.setY(y + moduleHeight + offset);

                component.drawScreen(mouseX, mouseY);
                // Draw a line between the components
                Gui.drawRect(x, y + moduleHeight + offset, x + defaultWidth, y + moduleHeight + offset, -1);
                offset += component.getOffset();
            }
        }

        this.setOffset(moduleHeight + offset);
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtils.hover(x, y, mouseX, mouseY, defaultWidth, moduleHeight) && RenderUtils.hover(owner.getX(), owner.getY(), mouseX, mouseY, defaultWidth, owner.getHeight())) {
            switch (mouseButton) {
                case 0:
                    module.toggle();
                    break;
                case 1:
                    if (opened) {
                        opened = false;
                        Sleek.getInstance().getModuleManager().getModuleByName("Click GUI").toggle();
                        Sleek.getInstance().getModuleManager().getModuleByName("Click GUI").toggle();
                    } else {
                        opened = true;
                    }
                    break;
                case 2:
                    listening = true;
                    //TODO: Bind
                    break;
            }
            return true;
        }

        if (RenderUtils.hover(owner.getX(), owner.getY(), mouseX, mouseY, defaultWidth, owner.getHeight()) && opened) {
            for (Component component : this.components) {
                if (component.isHidden()) continue;
                if (component.mouseClicked(mouseX, mouseY, mouseButton))
                    return true;
            }
        }

        return false;
    }

    public int getOffset() {
        offset = 0;
        if (opened) {
            for (Component component : this.components) { // using for loop because continue isn't supported on foreach
                //component.getSetting().constantCheck();
                //if(component.getSetting().isHide()) continue;
                if (component.isHidden()) continue;

                offset += component.getOffset();
            }
        }

        this.setOffset(moduleHeight + offset);
        return offset;
    }


    public void keyTyped(char key, int keycode) {
        for (Component component : this.components) {
            if (component.isHidden()) continue;
            component.keyTyped(key, keycode);

        }

        if (listening) {
            if (keycode == Keyboard.KEY_ESCAPE) {
                module.setKeyBind(0);

            } else {
                module.setKeyBind(keycode, true);
                ChatUtil.log(module.getName() + " is now binded to " + Keyboard.getKeyName(keycode));
                listening = false;
            }
        }
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
