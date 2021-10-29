package com.dragonappear.inha.exception.buying;

import com.dragonappear.inha.api.service.buying.iamport.dto.CancelDto;
import lombok.Getter;

@Getter
public class PaymentException extends BuyingException{
    private CancelDto cancelDto;

    public PaymentException() {
        super();
    }

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentException(Throwable cause) {
        super(cause);
    }

    public PaymentException(String message , CancelDto cancelDto) {
        super(message);
        this.cancelDto = cancelDto;
    }
}
