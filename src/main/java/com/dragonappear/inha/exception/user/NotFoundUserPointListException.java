package com.dragonappear.inha.exception.user;

import com.dragonappear.inha.exception.NotFoundCustomException;

public class NotFoundUserPointListException extends NotFoundCustomException {
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
