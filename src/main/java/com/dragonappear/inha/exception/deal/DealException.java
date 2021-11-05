package com.dragonappear.inha.exception.deal;

import com.dragonappear.inha.api.controller.buying.dto.PaymentApiDto;
import com.dragonappear.inha.api.service.buying.iamport.dto.CancelDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DealException extends RuntimeException {
    private CancelDto cancelDto;
    private PaymentApiDto paymentApiDto;

    public DealException() {
        super();
    }

    public DealException(String message) {
        super(message);
    }

    public DealException(String message, Throwable cause) {
        super(message, cause);
    }

    public DealException(Throwable cause) {
        super(cause);
    }

    public DealException(String message , CancelDto cancelDto) {
        super(message);
        this.cancelDto = cancelDto;
    }

    @Builder
    public DealException(String message , CancelDto cancelDto, PaymentApiDto paymentApiDto) {
        super(message);
        this.cancelDto = cancelDto;
        this.paymentApiDto = paymentApiDto;
    }
}
