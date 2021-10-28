package com.dragonappear.inha.exception.user;

public class NotFoundUserAddressException extends RuntimeException {
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
