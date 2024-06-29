package project.study.app.util;

/**
 * An interface for a timer that can be started with a duration, interval, and callback.
 */
public interface Timer {

    /**
     * Start the timer with the given duration, interval, and callback.
     *
     * @param durationInMillis the total duration of the timer
     * @param intervalInMillis the interval at which the timer should tick
     * @param callback the callback to be called when the timer ticks or finishes
     */
    void start(long durationInMillis, long intervalInMillis, TimerCallback callback);
}

