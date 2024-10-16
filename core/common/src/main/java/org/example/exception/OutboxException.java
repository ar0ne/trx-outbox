package org.example.exception;

public class OutboxException extends RuntimeException {

    public OutboxException() {
        super();
    }

    public OutboxException(String message) {
        super(message);
    }

    public OutboxException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutboxException(Throwable cause) {
        super(cause);
    }

    protected OutboxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
