package com.dragonappear.inha.exception.user;

import com.dragonappear.inha.exception.NotFoundCustomException;

public class NotFoundUserAddressException extends NotFoundCustomException {
    public NotFoundUserAddressException() {
        super();
    }

    public NotFoundUserAddressException(String message) {
        super(message);
    }

    public NotFoundUserAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundUserAddressException(Throwable cause) {
        super(cause);
    }
}
