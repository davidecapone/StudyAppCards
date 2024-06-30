package project.study.app.util;

import android.os.CountDownTimer;

/**
 * A real implementation of the Timer interface that uses the CountDownTimer class.
 */
public class RealTimer implements Timer {

    // The CountDownTimer object that will be used to implement the timer.
    private CountDownTimer countDownTimer;

    /**
     * Start the timer with the given duration, interval, and callback.
     *
     * @param durationInMillis the total duration of the timer
     * @param intervalInMillis the interval at which the timer should tick
     * @param callback the callback to be called when the timer ticks or finishes
     */
    @Override
    public void start(long durationInMillis, long intervalInMillis, TimerCallback callback) {

        if (countDownTimer != null) {

            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(durationInMillis, intervalInMillis) {

            /**
             * Called when the timer ticks.
             *
             * @param millisUntilFinished The amount of time until finished.
             */
            @Override
            public void onTick(long millisUntilFinished) {
                callback.onTick(millisUntilFinished);
            }

            /**
             * Called when the timer finishes.
             */
            @Override
            public void onFinish() {
                callback.onFinish();
            }
        };

        countDownTimer.start();
    }
}
