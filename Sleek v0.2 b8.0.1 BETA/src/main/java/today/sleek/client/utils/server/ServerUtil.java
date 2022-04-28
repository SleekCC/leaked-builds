package today.sleek.client.utils.server;

import net.minecraft.client.multiplayer.ServerData;
import today.sleek.client.utils.Util;

/**
 * @author Kansio
 */
public class ServerUtil extends Util {

    public static boolean onServer(final String server) {
        final ServerData serverData = mc.getCurrentServerData();
        return serverData != null && serverData.serverIP.toLowerCase().contains(server);
    }

    public static String getServer() {
        final ServerData serverData = mc.getCurrentServerData();

        if (mc.isIntegratedServerRunning()) {
            return "Singleplayer";
        }

        if (serverData == null) {
            return "Menu";
        }

        return serverData.serverIP;
    }

    public static boolean onHypixel() {
        final ServerData serverData = mc.getCurrentServerData();

        if (serverData == null)
            return false;

        return serverData.serverIP.endsWith("hypixel.net") || serverData.serverIP.endsWith("hypixel.net:25565") || serverData.serverIP.equals("104.17.71.15") || serverData.serverIP.equals("104.17.71.15:25565");
    }

}
