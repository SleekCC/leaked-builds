package today.sleek.base.config;

import today.sleek.client.gui.notification.Notification;
import today.sleek.client.gui.notification.NotificationManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private String name;
    private String author;
    private String lastUpdated;
    private File file;
    private boolean isOnline;

    public Config(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public Config(String name, String author, String lastUpdated, boolean online, File file) {
        this.author = author;
        this.lastUpdated = lastUpdated;
        this.name = name;
        this.file = file;
        this.isOnline = online;
    }

    public void rename(String newName) {
        try {
            Path original = Paths.get(file.getCanonicalPath());
            Path to = Paths.get(file.getPath());
            Files.move(original, to);
        } catch (Exception e) {
            NotificationManager.getNotificationManager().show(new Notification(Notification.NotificationType.ERROR, "Error!", "Couldn\'t rename config!", 1));
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public String getAuthor() {
        return this.author;
    }

    @SuppressWarnings("all")
    public void setAuthor(final String author) {
        this.author = author;
    }

    @SuppressWarnings("all")
    public String getLastUpdated() {
        return this.lastUpdated;
    }

    @SuppressWarnings("all")
    public void setLastUpdated(final String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @SuppressWarnings("all")
    public File getFile() {
        return this.file;
    }

    @SuppressWarnings("all")
    public void setFile(final File file) {
        this.file = file;
    }

    @SuppressWarnings("all")
    public boolean isOnline() {
        return this.isOnline;
    }

    @SuppressWarnings("all")
    public void setOnline(final boolean isOnline) {
        this.isOnline = isOnline;
    }
}
