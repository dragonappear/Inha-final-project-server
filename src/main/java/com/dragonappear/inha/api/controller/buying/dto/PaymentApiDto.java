package com.dragonappear.inha.api.controller.buying.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentApiDto {
    @NotNull
    private String pgName;
    @NotNull
    private String impId;
    @NotNull
    private String merchantId;
    @NotNull
    private BigDecimal paymentPrice;
    @NotNull
    private BigDecimal point;
    @NotNull
    private Long buyerId;
    @NotNull
    private Long addressId;

}
