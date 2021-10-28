package com.dragonappear.inha.api.controller.buying.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class PaymentDto {
    private String pgName;
    private String impId;
    private String merchantId;
    private BigDecimal paymentPrice;
    private BigDecimal point;
    private Long buyerId;
    private Long addressId;


    public PaymentDto(String pgName, String impId, String merchantId, BigDecimal paymentPrice
            , BigDecimal point, Long buyerId, Long addressId) {
        this.pgName = pgName;
        this.impId = impId;
        this.merchantId = merchantId;
        this.paymentPrice = paymentPrice;
        this.point = point;
        this.buyerId = buyerId;
        this.addressId = addressId;
    }
}
