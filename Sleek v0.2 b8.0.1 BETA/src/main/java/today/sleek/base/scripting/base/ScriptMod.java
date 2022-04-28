package today.sleek.base.scripting.base;

import jdk.nashorn.api.scripting.JSObject;
import today.sleek.base.event.Event;
import today.sleek.base.scripting.base.lib.Player;

import java.util.HashMap;
import java.util.Map;

public class ScriptMod {

    private final String name;
    private final String description;
    private final HashMap<String, JSObject> callbacks = new HashMap<>();

    public ScriptMod(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void onEvent(String eventName, Event e) {
        JSObject callback = callbacks.get(eventName);
        if (callback != null) {
            callback.call(this, e);
        }
    }

    public void onEnable() {
        onEvent("enable", null);
    }

    public void onLoad() {
        onEvent("load", null);
    }

    public void onDisable() {
        onEvent("disable", null);
    }

    public void on(String eventName, JSObject callback) {
        callbacks.put(eventName, callback);
    }

}

