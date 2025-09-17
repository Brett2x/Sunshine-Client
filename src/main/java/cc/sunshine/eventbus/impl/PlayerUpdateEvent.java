package cc.sunshine.eventbus.impl;

import cc.sunshine.eventbus.Event;

public final class PlayerUpdateEvent extends Event {

    private final State state;

    public PlayerUpdateEvent(final State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public enum State {
        PRE, POST
    }

}
