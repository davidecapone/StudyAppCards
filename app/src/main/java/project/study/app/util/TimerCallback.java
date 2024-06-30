package project.study.app.util;

/**
 * An interface for a callback that can be called when a timer ticks or finishes.
 */
public interface TimerCallback {

    /**
     * Called when the timer ticks.
     *
     * @param millisUntilFinished
     */
    void onTick(long millisUntilFinished);

    /**
     * Called when the timer finishes.
     */
    void onFinish();
}
