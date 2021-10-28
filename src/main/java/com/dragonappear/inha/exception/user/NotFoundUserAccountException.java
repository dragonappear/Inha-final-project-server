package com.dragonappear.inha.exception.user;

public class NotFoundUserAccountException extends RuntimeException{
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
