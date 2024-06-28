package project.study.app.util;


public interface Timer {
    void start(long durationInMillis, long intervalInMillis, TimerCallback callback);
}

