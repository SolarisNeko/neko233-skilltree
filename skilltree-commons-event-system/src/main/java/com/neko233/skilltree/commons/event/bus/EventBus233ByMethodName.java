package com.neko233.skilltree.commons.event.bus;

import com.neko233.skilltree.commons.event.EventBus233;
import com.neko233.skilltree.commons.event.subscriber.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SolarisNeko
 */
public class EventBus233ByMethodName implements EventBus233 {

    private final Map<Class<?>, List<Subscriber>> subscribersMap = new HashMap<>();

    @Override
    public void register(Object subscriber) {
        for (Class<?> eventClass : getDeclaredEventClasses(subscriber.getClass())) {
            List<Subscriber> subscribers = subscribersMap.getOrDefault(eventClass, new ArrayList<>());
            subscribers.add(new Subscriber(subscriber, eventClass));
            subscribersMap.put(eventClass, subscribers);
        }
    }

    @Override
    public void unregister(Object subscriber) {
        for (Class<?> eventClass : getDeclaredEventClasses(subscriber.getClass())) {
            List<Subscriber> subscribers = subscribersMap.getOrDefault(eventClass, new ArrayList<>());
            subscribers.removeIf(s -> s.getSubscriber() == subscriber);
            subscribersMap.put(eventClass, subscribers);
        }
    }

    @Override
    public void post(Object event) {
        List<Subscriber> subscribers = subscribersMap.getOrDefault(event.getClass(), new ArrayList<>());
        for (Subscriber subscriber : subscribers) {
            subscriber.call(event);
        }
    }


}
