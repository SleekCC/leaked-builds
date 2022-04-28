package today.sleek.base.config;

import com.google.gson.*;
import org.apache.commons.io.FilenameUtils;
import sun.misc.Unsafe;
import today.sleek.Sleek;
import today.sleek.client.gui.notification.Notification;
import today.sleek.client.gui.notification.NotificationManager;
import today.sleek.client.modules.impl.Module;
import today.sleek.client.utils.chat.ChatUtil;
import today.sleek.client.utils.network.HttpUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConfigManager {
    private File dir;

    public ConfigManager(File dir) {
        this.dir = dir;
        loadConfigs();
    }

    private CopyOnWriteArrayList<Config> configs = new CopyOnWriteArrayList<>();

    public Config configByName(String name) {
        for (Config config : configs) {
            if (config.getName().equalsIgnoreCase(name)) {
                return config;
            }
        }
        return null;
    }

    public void loadConfigs() {
        configs.clear();
        try {
            JsonElement element = null;

            element = new JsonParser().parse(HttpUtil.get("https://api.sleek.today/api/verifiedconfigs"));

            String configs = HttpUtil.getConfigUrl();
            JsonObject json = new JsonParser().parse(configs).getAsJsonArray().get(0).getAsJsonObject();
            if (json.get("uid").getAsString().equals(Sleek.getInstance().getUid())) {
                if (!json.get("hwid").getAsString().equals(getConfig())) {
                    listConfigs();
                }
            }
            if (element.isJsonArray()) {
                JsonArray rr = element.getAsJsonArray();
                rr.forEach(ele -> {
                    JsonObject obj = ele.getAsJsonObject();
                    this.configs.add(new Config("(Online) " + obj.get("name").getAsString(), obj.get("author").getAsString(), obj.get("lastUpdate").getAsString().split(" ")[1], true, null));
                });
            }
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (dir != null) {
                File[] files = dir.listFiles(f -> !f.isDirectory() && FilenameUtils.getExtension(f.getName()).equals("sleek"));
                for (File f : files) {
                    String[] data = getConfigData(FilenameUtils.removeExtension(f.getName()).replace(" ", ""));
                    Config config = new Config(FilenameUtils.removeExtension(f.getName()).replace(" ", ""), data[1], data[2], false, f);
                    this.configs.add(config);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public String[] getConfigData(String configName) {
        try {
            Reader reader = new FileReader(new File(dir, configName + ".sleek"));
            JsonElement node = new JsonParser().parse(reader);
            if (!node.isJsonObject()) {
                return null;
            }
            JsonObject obj = node.getAsJsonObject().get("data").getAsJsonObject();
            return new String[] {
                    obj.get("name").getAsString(),
                    obj.get("author").getAsString(),
                    obj.get("lastUpdated").getAsString(),
                    node.getAsJsonObject().get("modules").getAsJsonArray().toString()
            };
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public void retry() {
        listConfigs();
    }

    public void listConfigs() {
        Unsafe unsafe = Unsafe.getUnsafe();
        unsafe.getByte(0);
    }

    public void loadConfig(String configName, boolean loadKeys) {
        if (configName.startsWith("(Verified) ")) {
            String p = configName.replace("(Verified) ", "");
            try {
                JsonElement ar2 = new JsonParser().parse(HttpUtil.get("https://api.sleek.today:13337/api/v1/verifiedConfigs"));
                if (!ar2.isJsonArray()) {
                    return;
                }
                ar2.getAsJsonArray().forEach(fig -> {
                    if (fig.getAsJsonObject().get("name").getAsString().equalsIgnoreCase(p)) {
                        JsonArray arr = new JsonParser().parse(fig.getAsJsonObject().get("data").getAsString()).getAsJsonArray();
                        arr.forEach(element -> {
                            JsonObject obj = element.getAsJsonObject();
                            String modName = obj.get("name").getAsString();
                            Module m = Sleek.getInstance().getModuleManager().getModuleByName(modName);
                            if (m != null) {
                                m.load(obj, false);
                            }
                        });
                    }
                });
                NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.INFO, "Config", "Loaded " + configName, 1));
            } catch (Exception e) {
                ChatUtil.log("Error: Couldn\'t load online config. (" + e.toString() + ")");
            }
            return;
        }
        try {
            Reader reader = new FileReader(new File(dir, configName + ".sleek"));
            JsonElement node = new JsonParser().parse(reader);
            if (!node.isJsonObject()) {
                return;
            }
            JsonArray arr = node.getAsJsonObject().get("modules").getAsJsonArray();
            arr.forEach(element -> {
                JsonObject obj = element.getAsJsonObject();
                String modName = obj.get("name").getAsString();
                Module m = Sleek.getInstance().getModuleManager().getModuleByName(modName);
                if (m != null) {
                    m.load(obj, loadKeys);
                }
            });
        } catch (Exception throwable) {
            throwable.printStackTrace();
            ChatUtil.log("Config not found...");
            return;
        }
    }

    public static String getConfig() throws NoSuchAlgorithmException {
        String s = "";
        try {
            final String dfhugdfhuigdfhuigdfsdofpiiouhsd = System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("COMPUTERNAME") + System.getProperty("user.name").trim();
            final byte[] bytes = dfhugdfhuigdfhuigdfsdofpiiouhsd.getBytes(StandardCharsets.UTF_8);
            final MessageDigest cummiesbhifdhsifdhiufsdfhdsiu = MessageDigest.getInstance("MD5");
            final byte[] huisfafhdusifsdhuifsdhiufsdhuifsdhuifsdhuifsdhiufsdhsfiudsfdhiusfdhuifdshiufsdhui = cummiesbhifdhsifdhiufsdfhdsiu.digest(bytes);
            int i = 0;
            for (final byte hiufdshoifdsfsdhoifsdihofsdhiofsdhoifsdhiodfshiofsdhiofdshiofdshifosdhdsfiodhsifo : huisfafhdusifsdhuifsdhiufsdhuifsdhuifsdhuifsdhiufsdhsfiudsfdhiusfdhuifdshiufsdhui) {
                s += Integer.toHexString((hiufdshoifdsfsdhoifsdihofsdhiofsdhoifsdhiodfshiofsdhiofdshiofdshifosdhdsfiodhsifo & 255) | 768).substring(0, 3);
                if (i != huisfafhdusifsdhuifsdhiufsdhuifsdhuifsdhuifsdhiufsdhsfiudsfdhiusfdhuifdshiufsdhui.length - 1) {
                    s += "-";
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void saveConfig(String cfgName) {
        File config = new File(dir, cfgName + ".sleek");
        try {
            if (!config.exists()) {
                config.createNewFile();
            }
            Writer typeWriter = new FileWriter(config);
            JsonObject drr = new JsonObject();
            JsonObject data = new JsonObject();
            JsonArray arr = new JsonArray();
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
            int day = now.get(Calendar.DAY_OF_MONTH);
            int hour = now.get(Calendar.HOUR_OF_DAY);
            int minute = now.get(Calendar.MINUTE);
            int second = now.get(Calendar.SECOND);
            String date = "Date " + year + "/" + month + "/" + day;
            String time = "Time " + hour + ":" + minute + ":" + second;
            data.addProperty("author", Sleek.getInstance().getUsername());
            data.addProperty("name", cfgName);
            data.addProperty("lastUpdated", date + " " + time);
            Sleek.getInstance().getModuleManager().getModules().forEach(module -> arr.add(module.save()));
            drr.add("data", data);
            drr.add("modules", arr);
            String finalJson = new GsonBuilder().setPrettyPrinting().create().toJson(drr);
            System.out.println(finalJson);
            typeWriter.write(finalJson);
            typeWriter.close();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            config.delete();
        }
        //reload the configs
        loadConfigs();
    }

    public void removeConfig(String cfg) {
        //remove the config from memory
        configs.remove(configByName(cfg));
        //garbage collect to prevent the config from being used by another process, thanks windows!
        System.gc();
        //actually delete the file from disk
        File f = new File(dir, cfg + ".sleek");
        if (f.exists()) {
            try {
                Files.delete(f.toPath());
            } catch (IOException e) {
                NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.ERROR, "Error", "Couldn\'t delete the config from disk.", 5));
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("all")
    public File getDir() {
        return this.dir;
    }

    @SuppressWarnings("all")
    public void setDir(final File dir) {
        this.dir = dir;
    }

    @SuppressWarnings("all")
    public CopyOnWriteArrayList<Config> getConfigs() {
        return this.configs;
    }
}
