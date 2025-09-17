package cc.sunshine.eventbus;

import cc.sunshine.eventbus.interfaces.IHandler;
import cc.sunshine.eventbus.interfaces.Listener;
import cc.sunshine.eventbus.interfaces.SubscribeEvent;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventBus {
    private final Map<IHandler, Map<Type, List<Listener<? extends Event>>>> listeners = new HashMap<>();

    public <T extends Event> void publishEvent(T event) {
        new HashMap<>(listeners).forEach((handler, map) -> {
            if(!handler.listening())
                return;


            map.forEach((eventType, listeners) -> {
                if(event.getClass() != eventType)
                    return;

                listeners.forEach(listener -> {
                    Listener<T> castedListener = (Listener<T>) listener;
                    castedListener.call(event);
                });
            });
        });
    }

    public void registerHandler(IHandler handler) {
        Class<?> handlerClass = handler.getClass();

        try {
            for (Field field : handlerClass.getDeclaredFields()) {
                if(!field.isAnnotationPresent(SubscribeEvent.class)) {
                    continue;
                }

                if(!field.trySetAccessible()) {
                    throw new RuntimeException("Couldn't set field accessible: " + handler.getClass() + "#" + field.getName());
                }

                Listener<? extends Event> listener = (Listener<? extends Event>) field.get(handler);
                Type eventType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];

                listeners.putIfAbsent(handler, new HashMap<>());
                listeners.get(handler).putIfAbsent(eventType, new ArrayList<>());
                listeners.get(handler).get(eventType).add(listener);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
