package today.sleek.base.event.impl;

import today.sleek.base.event.Event;

public class ChatEvent extends Event {

    private final String message;


    public ChatEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
