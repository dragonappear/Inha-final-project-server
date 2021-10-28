package com.dragonappear.inha.exception.user;

public class NotFoundUserEmailException extends RuntimeException {

    public NotFoundUserEmailException() {
        super();
    }

    public NotFoundUserEmailException(String message) {
        super(message);
    }

    public NotFoundUserEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundUserEmailException(Throwable cause) {
        super(cause);
    }
}
