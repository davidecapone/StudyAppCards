package project.study.app.service;

public interface Callback {
    void onSuccess();
    void onError(Exception e);
}