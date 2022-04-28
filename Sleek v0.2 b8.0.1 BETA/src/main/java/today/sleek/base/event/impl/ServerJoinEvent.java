package today.sleek.base.event.impl;

import today.sleek.base.event.Event;

public class ServerJoinEvent extends Event {
    private String serverIP;
    private String ign;

    public ServerJoinEvent(String serverIP, String ign) {
        this.serverIP = serverIP;
        this.ign = ign;
    }

    @SuppressWarnings("all")
    public String getServerIP() {
        return this.serverIP;
    }

    @SuppressWarnings("all")
    public String getIgn() {
        return this.ign;
    }
}
