package project.study.app.service.interfaces;

/**
 * A callback interface to notify the result of an operation.
 */
public interface Callback {
    /**
     * Notifies the operation was successful.
     */
    void onSuccess();
    /**
     * Notifies the operation failed.
     *
     * @param e The exception that caused the failure.
     */
    void onError(Exception e);
}