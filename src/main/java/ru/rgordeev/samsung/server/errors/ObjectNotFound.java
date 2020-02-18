package ru.rgordeev.samsung.server.errors;

public class ObjectNotFound extends RuntimeException {
    public ObjectNotFound() {
        super();
    }

    public ObjectNotFound(String message) {
        super(message);
    }

    public ObjectNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFound(Throwable cause) {
        super(cause);
    }

    protected ObjectNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
