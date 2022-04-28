package today.sleek.base.value.value;

import today.sleek.base.value.Value;

import java.util.ArrayList;
import java.util.Collections;

public class SubSettings {
    private ArrayList<Value<?>> subSettings = new ArrayList<>();
    private String name;

    public SubSettings(String name, Value<?>... values) {
        this.name = name;
        Collections.addAll(subSettings, values);
    }

    @SuppressWarnings("all")
    public ArrayList<Value<?>> getSubSettings() {
        return this.subSettings;
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }
}
