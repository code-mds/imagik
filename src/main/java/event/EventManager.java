package event;

import com.google.common.eventbus.EventBus;

public class EventManager {
    private static EventManager eventManager;

    private EventBus bus;

    private EventManager() {
        bus = new EventBus();
    }

    public static EventManager getInstance() {
        if(eventManager == null)
            eventManager = new EventManager();

        return eventManager;
    }

    public void register(EventSubscriber eventSubscriber) {
        bus.register(eventSubscriber);
    }
    public void post(EventBase event) {
        bus.post(event);
    }

}
