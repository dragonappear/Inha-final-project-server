package com.dragonappear.inha.exception.buying;

public class BuyingException extends RuntimeException{

    public BuyingException() {
        super();
    }

    public BuyingException(String message) {
        super(message);
    }

    public BuyingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuyingException(Throwable cause) {
        super(cause);
    }
}
