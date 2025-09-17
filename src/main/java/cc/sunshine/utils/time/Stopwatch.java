package cc.sunshine.utils.time;

public final class Stopwatch {
    private long ms = System.currentTimeMillis();

    public boolean reached(int ms) {
        return this.ms + ms <= System.currentTimeMillis();
    }

    public void reset() {
        this.ms = System.currentTimeMillis();
    }
}
