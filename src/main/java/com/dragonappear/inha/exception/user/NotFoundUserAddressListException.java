package com.dragonappear.inha.exception.user;

import com.dragonappear.inha.exception.NotFoundCustomException;

public class NotFoundUserAddressListException extends NotFoundCustomException {
    public NotFoundUserAddressListException() {
        super();
    }

    public NotFoundUserAddressListException(String message) {
        super(message);
    }

    public NotFoundUserAddressListException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundUserAddressListException(Throwable cause) {
        super(cause);
    }
}
