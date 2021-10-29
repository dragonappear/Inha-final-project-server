package com.dragonappear.inha.exception.user;

import com.dragonappear.inha.exception.NotFoundCustomException;

public class NotFoundUserEmailException extends NotFoundCustomException {

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
