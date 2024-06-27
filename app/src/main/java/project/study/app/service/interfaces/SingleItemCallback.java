package project.study.app.service.interfaces;

public interface SingleItemCallback<T> {
    void onSuccess(T item);
    void onError(Exception e);
}
