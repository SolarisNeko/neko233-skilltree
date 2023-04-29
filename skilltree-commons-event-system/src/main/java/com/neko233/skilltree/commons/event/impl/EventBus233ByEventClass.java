package com.neko233.skilltree.commons.event.impl;

import com.neko233.skilltree.commons.event.EventBus233;
import com.neko233.skilltree.commons.event.annotation.EventConsumer;
import com.neko233.skilltree.commons.event.subscriber.Subscriber;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event Class 同时作为 subscriber, 调用其内部的 onClassName
 *
 * @author SolarisNeko
 */
@Slf4j
public class EventBus233ByEventClass implements EventBus233<Object> {

    private final Map<String, List<Subscriber>> subscribersMap = new ConcurrentHashMap<>();
    private final Map<String, List<Subscriber>> onceSubSubscriber = new ConcurrentHashMap<>();

    @Override
    public void register(String eventName, Object subscriber, boolean isOnce) {
        if (subscriber instanceof Class) {
            throw new IllegalArgumentException("you can not register class to a subscriber");
        }
        if (subscriber == null) {
            return;
        }
        EventConsumer annotation = subscriber.getClass().getAnnotation(EventConsumer.class);

        List<Class<?>> eventClassList = new ArrayList<>(Arrays.asList(annotation.value()));
        for (Class<?> eventClass : eventClassList) {
            eventName = eventName == null ? eventClass.getName() : eventName;

            List<Subscriber> subscriberList = subscribersMap.getOrDefault(eventName, new ArrayList<>());

            Subscriber consumer = new Subscriber(eventName, subscriber, eventClass);
            subscriberList.add(consumer);
            if (isOnce) {
                onceSubSubscriber.put(eventName, subscriberList);
                continue;
            }
            subscribersMap.put(eventName, subscriberList);
        }
    }


    @Override
    public void unregister(String eventName, Object subscriber) {
        if (subscriber == null) {
            return;
        }
        for (Class<?> eventClass : getDeclaredEventClasses(subscriber.getClass())) {
            List<Subscriber> subscribers = subscribersMap.getOrDefault(eventName, new ArrayList<>());
            subscribers.removeIf(s -> s.getSubscriber() == subscriber);
            subscribersMap.put(eventName, subscribers);
        }
    }

    @Override
    public void post(String eventName, Object event) {
        eventName = eventName == null ? event.getClass().getName() : eventName;

        // forever
        List<Subscriber> subscribers = subscribersMap.getOrDefault(eventName, new ArrayList<>());
        for (Subscriber subscriber : subscribers) {
            safeCall(subscriber, event);
        }

        // once
        List<Subscriber> onceSubscribers = onceSubSubscriber.getOrDefault(eventName, new ArrayList<>());
        for (Subscriber subscriber : onceSubscribers) {
            if (subscriber.isAccept(event)) {
                continue;
            }
            safeCall(subscriber, event);
        }
        onceSubSubscriber.remove(eventName);

    }


    private static void safeCall(Subscriber subscriber, Object event) {
        try {
            subscriber.call(event);
        } catch (Exception e) {
            log.error("[EventBus233ByEventClass] event call error", e);
        }
    }


}
