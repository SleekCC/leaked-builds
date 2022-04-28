package today.sleek.base.value;

import today.sleek.base.value.value.BooleanValue;
import today.sleek.base.value.value.ModeValue;
import today.sleek.client.modules.impl.Module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Value<Type> {
    private final String name;
    protected List<String> modes = new ArrayList<>();
    private final Module owner;
    protected Value<?> parent;
    protected Type value;

    public Value(String name, Module owner, Type value) {
        this.name = name;
        this.owner = owner;
        this.value = value;
    }

    public Value(String name, Module owner, Type value, ModeValue parent, String... modes) {
        this(name, owner, value);
        this.parent = parent;
        Collections.addAll(this.modes, modes);
    }

    public Value(String name, Module owner, Type value, BooleanValue parent) {
        this(name, owner, value);
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Module getOwner() {
        return owner;
    }

    public Type getValue() {
        return value;
    }

    public void setValueAutoSave(Type value) {
        this.value = value;
    }

    public void setValue(Type value) {
        this.value = value;
    }

    public Value getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public String getValueAsString() {
        return String.valueOf(value);
    }

    @SuppressWarnings("all")
    public List<String> getModes() {
        return this.modes;
    }
}
