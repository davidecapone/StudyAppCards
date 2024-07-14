package project.study.app.service.interfaces;

/**
 * A callback interface to notify the result of an operation that returns a single item.
 *
 * @param <T> The type of the item.
 */
public interface SingleItemCallback<T> {

    /**
     * Notifies the operation was successful.
     *
     * @param item The item.
     */

    void onSuccess(T item);
    /**
     * Notifies the operation failed.
     *
     * @param e The exception that caused the failure.
     */
    void onError(Exception e);
}
