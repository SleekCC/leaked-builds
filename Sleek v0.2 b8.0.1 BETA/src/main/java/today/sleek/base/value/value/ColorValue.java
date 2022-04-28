package today.sleek.base.value.value;

import today.sleek.base.value.Value;
import today.sleek.client.modules.impl.Module;

import java.awt.*;
import java.util.List;

/**
 * @author Kansio
 */
public class ColorValue extends Value<Color> {

    private Color value;

    public ColorValue(String name, Module owner, Color value) {
        super(name, owner, value);
    }

    public ColorValue(String name, Module owner, Color value, ModeValue parent, String... modes) {
        super(name, owner, value, parent, modes);
    }

    public ColorValue(String name, Module owner, Color value, BooleanValue parent) {
        super(name, owner, value, parent);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Module getOwner() {
        return super.getOwner();
    }

    @Override
    public Color getValue() {
        return super.getValue();
    }

    @Override
    public void setValueAutoSave(Color value) {
        super.setValueAutoSave(value);
    }

    @Override
    public void setValue(Color value) {
        super.setValue(value);
    }

    @Override
    public Value getParent() {
        return super.getParent();
    }

    @Override
    public boolean hasParent() {
        return super.hasParent();
    }

    @Override
    public String getValueAsString() {
        return super.getValueAsString();
    }

    @Override
    public List<String> getModes() {
        return super.getModes();
    }
}
