package ch.imagik.event;

public class EventManager {
    private static EventManager eventManager;

    @SuppressWarnings("UnstableApiUsage")
    private final com.google.common.eventbus.EventBus bus;

    @SuppressWarnings("UnstableApiUsage")
    private EventManager() {
        bus = new com.google.common.eventbus.EventBus();
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
