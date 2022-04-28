package today.sleek.client.utils.network;

import today.sleek.Sleek;

public class UserUtil {

    @Deprecated
    public static String getBuildType(int uid) {
        return uid <= 4 ? "Developer" : "Release";
    }

    public static String getBuildType() {
        return Integer.parseInt(Sleek.getInstance().getUid()) <= 4 ? "Developer" : "Release";
    }

}
