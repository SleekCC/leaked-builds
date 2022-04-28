package today.sleek.base.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;


/**
 * @author Kansio
 * Makes google event bus print exceptions in console!
 */
public class SleekEventBus extends EventBus implements SubscriberExceptionHandler {

    public SleekEventBus() {
    }

    public SleekEventBus(String identifier) {
        super(identifier);
    }

    @Override
    public void handleException(Throwable throwable, SubscriberExceptionContext subscriberExceptionContext) {
        if (throwable instanceof RuntimeException) {
            throwable.printStackTrace();
            throw (RuntimeException) throwable;
        }
    }
}