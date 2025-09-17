package cc.sunshine.eventbus.interfaces;

import cc.sunshine.eventbus.Event;

public interface Listener<T extends Event> {
    void call(T event);
}
