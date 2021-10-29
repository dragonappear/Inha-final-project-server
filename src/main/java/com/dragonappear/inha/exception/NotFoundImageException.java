package com.dragonappear.inha.exception;

public class NotFoundImageException extends NotFoundCustomException {
    public NotFoundImageException() {
        super();
    }

    public NotFoundImageException(String message) {
        super(message);
    }

    public NotFoundImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundImageException(Throwable cause) {
        super(cause);
    }
}
