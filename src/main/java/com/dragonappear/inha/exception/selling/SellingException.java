package com.dragonappear.inha.exception.selling;

public class SellingException extends RuntimeException {
    public SellingException() {
        super();
    }

    public SellingException(String message) {
        super(message);
    }

    public SellingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SellingException(Throwable cause) {
        super(cause);
    }
}
