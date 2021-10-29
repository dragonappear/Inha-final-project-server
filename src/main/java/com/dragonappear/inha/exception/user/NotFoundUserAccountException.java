package com.dragonappear.inha.exception.user;

import com.dragonappear.inha.exception.NotFoundCustomException;

public class NotFoundUserAccountException extends NotFoundCustomException {
    public NotFoundUserAccountException() {
        super();
    }

    public NotFoundUserAccountException(String message) {
        super(message);
    }

    public NotFoundUserAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundUserAccountException(Throwable cause) {
        super(cause);
    }
}
