package cc.sunshine.eventbus.interfaces;

public interface IHandler {
    default boolean listening() {
        return true;
    }
}
