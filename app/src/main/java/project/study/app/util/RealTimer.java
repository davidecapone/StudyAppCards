package project.study.app.util;

import android.os.CountDownTimer;

public class RealTimer implements Timer {

    private CountDownTimer countDownTimer;

    @Override
    public void start(long durationInMillis, long intervalInMillis, TimerCallback callback) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(durationInMillis, intervalInMillis) {
            @Override
            public void onTick(long millisUntilFinished) {
                callback.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        };

        countDownTimer.start();
    }
}
