package project.study.app.service;

public interface SingleItemCallback<T> {
    void onSuccess(T item);
    void onError(Exception e);
}
