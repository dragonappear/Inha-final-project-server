package com.dragonappear.inha.exception.user;

public class NotFoundUserAddressListException extends RuntimeException{
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
