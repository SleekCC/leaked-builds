import net.minecraft.client.main.Main;
import org.apache.logging.log4j.LogManager;

import java.util.Arrays;

public class Start {

    public static void main(String[] args) {
        LogManager.getLogger().info("took");
        Main.main(concat(new String[]{"--version", "1.8", "--accessToken", "0", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}"}, args));
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
