package com.dragonappear.inha.exception.user;

public class NotFoundUserPointListException extends RuntimeException {
    public NotFoundUserPointListException() {
        super();
    }

    public NotFoundUserPointListException(String message) {
        super(message);
    }

    public NotFoundUserPointListException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundUserPointListException(Throwable cause) {
        super(cause);
    }
}
