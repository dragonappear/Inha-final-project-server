package com.dragonappear.inha.exception.user;

import com.dragonappear.inha.exception.NotFoundCustomException;

public class NotFoundUserIdException extends NotFoundCustomException {
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
