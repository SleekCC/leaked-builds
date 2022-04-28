package today.sleek.client.modules.impl;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import today.sleek.Sleek;
import today.sleek.base.modules.ModuleCategory;
import today.sleek.base.modules.ModuleData;
import today.sleek.base.value.Value;
import today.sleek.base.value.value.*;
import today.sleek.client.modules.impl.visuals.ClickGUI;
import today.sleek.client.modules.impl.visuals.HUD;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Module {
    protected static final Minecraft mc = Minecraft.getMinecraft();
    public String fontmode;
    private String name;
    private boolean toggled;
    private int keyBind;
    private String suffix = "";
    private ModuleCategory category;
    private List<SubSettings> subSettings = new ArrayList<>();
    private boolean hidden = false;
    private String description;

    public Module() {
        name = getClass().getAnnotation(ModuleData.class).name();
        keyBind = getClass().getAnnotation(ModuleData.class).bind();
        category = getClass().getAnnotation(ModuleData.class).category();
        description = getClass().getAnnotation(ModuleData.class).description();
    }

    public Module(String name, int keyBind, ModuleCategory category) {
        this.category = category;
        this.keyBind = keyBind;
        this.name = name;
    }

    public Module(String name, ModuleCategory category) {
        this(name, Keyboard.KEY_NONE, category);
    }

    public String getFormattedSuffix() {
        if (getSuffix().equalsIgnoreCase("")) return "";
        HUD hud = (HUD) Sleek.getInstance().getModuleManager().getModuleByName("HUD");
        String suffix;
        if (getSuffix().startsWith(" ")) suffix = getSuffix().replaceFirst(" ", "");
        else suffix = getSuffix();
        String formatted = hud.getListSuffix().getValue().replaceAll("%s", suffix);
        return formatted;
    }

    public void toggle() {
        toggled = !toggled;
        if (toggled) {
            Sleek.getInstance().getEventBus().register(this);
            onEnable();
        } else {
            Sleek.getInstance().getEventBus().unregister(this);
            onDisable();
        }
        if (!(this instanceof ClickGUI)) onToggled();
    }

    public String getSuffix() {
        return suffix;
    }

    public void onToggled() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void registerSubSettings(SubSettings... subSettings) {
        Collections.addAll(this.subSettings, subSettings);
    }

    public void register(Value... properties) {
        //Collections.addAll(Client.getInstance().getValueManager().getObjects(), properties);
    }

    public void unRegister(Value... properties) {
        Collections.addAll(Sleek.getInstance().getValueManager().getObjects(), properties);
        Sleek.getInstance().getValueManager().getObjects().removeAll(Arrays.asList(properties));
    }

    public List<Value> getValues() {
        return Sleek.getInstance().getValueManager().getValuesFromOwner(this);
    }

    public void load(JsonObject obj, boolean loadKey) {
        obj.entrySet().forEach(data -> {
            switch (data.getKey()) {
                case "name": {
                    break;
                }
                case "hidden": {
                    this.hidden = data.getValue().getAsBoolean();
                    break;
                }
                case "keybind": {
                    if (loadKey) {
                        this.keyBind = data.getValue().getAsInt();
                    }
                    break;
                }
                case "enabled": {
                    if (!(isToggled() && data.getValue().getAsBoolean()) && !(!isToggled() && !data.getValue().getAsBoolean()))
                        setToggled(data.getValue().getAsBoolean());
                    break;
                }
            }
            Value val = Sleek.getInstance().getValueManager().getValueFromOwner(this, data.getKey());
            if (val != null) {
                if (val instanceof BooleanValue) {
                    val.setValue(data.getValue().getAsBoolean());
                } else if (val instanceof NumberValue) {
                    if (((NumberValue<?>) val).getIncrement() instanceof Double) {
                        val.setValue(data.getValue().getAsDouble());
                    }
                    if (((NumberValue<?>) val).getIncrement() instanceof Float) {
                        val.setValue((float) data.getValue().getAsDouble());
                    }
                    if (((NumberValue<?>) val).getIncrement() instanceof Long) {
                        val.setValue((long) data.getValue().getAsDouble());
                    }
                    if (((NumberValue<?>) val).getIncrement() instanceof Integer) {
                        val.setValue((int) data.getValue().getAsDouble());
                    }
                } else if (val instanceof ModeValue) {
                    val.setValue(data.getValue().getAsString());
                } else if (val instanceof StringValue) {
                    val.setValue(data.getValue().getAsString());
                } else if (val instanceof NumberValue) {
                    val.setValue(val.getValue());
                }
            }
        });
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        if (toggled) {
            Sleek.getInstance().getEventBus().register(this);
            onEnable();
        } else {
            Sleek.getInstance().getEventBus().unregister(this);
            onDisable();
        }
        if (!(this instanceof ClickGUI)) onToggled();
    }

    public JsonObject save() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("keybind", this.keyBind);
        json.addProperty("hidden", this.hidden);
        json.addProperty("enabled", this.toggled);
        getValues().forEach(value -> json.addProperty(value.getName(), value.getValue().toString()));
        return json;
    }

    public JsonObject saveKeybind() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("keybind", this.keyBind);
        json.addProperty("keybindName", Keyboard.getKeyName(this.keyBind));
        return json;
    }

    public void setKeyBind(int keyBind, boolean save) {
        System.out.println("Saved KeyBinds " + this.name);
        this.keyBind = keyBind;
        Sleek.getInstance().getKeybindManager().save();
    }

    @SuppressWarnings("all")
    public String getFontmode() {
        return this.fontmode;
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public boolean isToggled() {
        return this.toggled;
    }

    @SuppressWarnings("all")
    public int getKeyBind() {
        return this.keyBind;
    }

    @SuppressWarnings("all")
    public ModuleCategory getCategory() {
        return this.category;
    }

    @SuppressWarnings("all")
    public List<SubSettings> getSubSettings() {
        return this.subSettings;
    }

    @SuppressWarnings("all")
    public void setFontmode(final String fontmode) {
        this.fontmode = fontmode;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public void setKeyBind(final int keyBind) {
        this.keyBind = keyBind;
    }

    @SuppressWarnings("all")
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    @SuppressWarnings("all")
    public void setCategory(final ModuleCategory category) {
        this.category = category;
    }

    @SuppressWarnings("all")
    public void setSubSettings(final List<SubSettings> subSettings) {
        this.subSettings = subSettings;
    }

    public String getDescription() {
        return description;
    }
}
