package com.dragonappear.inha.exception;

public class NotFoundCustomException extends RuntimeException {
    public NotFoundCustomException() {
        super();
    }

    public NotFoundCustomException(String message) {
        super(message);
    }

    public NotFoundCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundCustomException(Throwable cause) {
        super(cause);
    }
}
