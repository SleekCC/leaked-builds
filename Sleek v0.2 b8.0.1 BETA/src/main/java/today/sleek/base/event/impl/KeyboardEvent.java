package today.sleek.base.event.impl;

import today.sleek.base.event.Event;

public class KeyboardEvent extends Event {
    private final int keyCode;

    public KeyboardEvent(int key) {
        this.keyCode = key;
    }

    @SuppressWarnings("all")
    public int getKeyCode() {
        return this.keyCode;
    }
}
