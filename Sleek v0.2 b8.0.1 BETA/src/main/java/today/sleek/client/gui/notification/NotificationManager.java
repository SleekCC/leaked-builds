package today.sleek.client.gui.notification;

import com.google.common.eventbus.Subscribe;
import today.sleek.Sleek;
import today.sleek.base.event.impl.RenderOverlayEvent;
import today.sleek.client.modules.impl.visuals.HUD;

import java.util.concurrent.LinkedBlockingQueue;

public class NotificationManager {
    private static NotificationManager notificationManager = new NotificationManager();
    private LinkedBlockingQueue<Notification> pendingNotifications = new LinkedBlockingQueue<>();
    private Notification currentNotification = null;

    public NotificationManager() {
        Sleek.getInstance().getEventBus().register(this);
    }

    public void show(Notification notification) {
        pendingNotifications.add(notification);
    }

    public void update() {
        if (currentNotification != null && !currentNotification.isShown()) {
            currentNotification = null;
        }
        if (currentNotification == null && !pendingNotifications.isEmpty()) {
            currentNotification = pendingNotifications.poll();
            currentNotification.show();
        }
    }

    @Subscribe
    public void render(RenderOverlayEvent event) {
        if (!HUD.notifications && Sleek.getInstance().getModuleManager().getModuleByName("Hud").isToggled()) return;
        update();
        if (currentNotification != null) currentNotification.render();
    }

    @SuppressWarnings("all")
    public static NotificationManager getNotificationManager() {
        return NotificationManager.notificationManager;
    }

    @SuppressWarnings("all")
    public LinkedBlockingQueue<Notification> getPendingNotifications() {
        return this.pendingNotifications;
    }

    @SuppressWarnings("all")
    public Notification getCurrentNotification() {
        return this.currentNotification;
    }
}
