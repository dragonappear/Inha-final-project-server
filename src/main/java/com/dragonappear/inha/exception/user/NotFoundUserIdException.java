package com.dragonappear.inha.exception.user;

public class NotFoundUserIdException extends RuntimeException {
    public NotFoundUserIdException() {
        super();
    }

    public NotFoundUserIdException(String message) {
        super(message);
    }

    public NotFoundUserIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundUserIdException(Throwable cause) {
        super(cause);
    }
}
