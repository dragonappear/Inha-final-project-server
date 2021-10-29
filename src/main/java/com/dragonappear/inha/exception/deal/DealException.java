package com.dragonappear.inha.exception.deal;

import com.dragonappear.inha.api.controller.buying.dto.PaymentDto;
import com.dragonappear.inha.api.service.buying.iamport.dto.CancelDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DealException extends RuntimeException {
    private CancelDto cancelDto;
    private PaymentDto paymentDto;

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
    public DealException(String message , CancelDto cancelDto, PaymentDto paymentDto) {
        super(message);
        this.cancelDto = cancelDto;
        this.paymentDto = paymentDto;
    }
}
