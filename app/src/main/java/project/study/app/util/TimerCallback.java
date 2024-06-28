package project.study.app.util;

public interface TimerCallback {
    void onTick(long millisUntilFinished);
    void onFinish();
}
