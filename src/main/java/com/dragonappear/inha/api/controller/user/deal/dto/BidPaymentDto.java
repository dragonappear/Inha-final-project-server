package com.dragonappear.inha.api.controller.user.deal.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class BidPaymentDto extends PaymentDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    public BidPaymentDto(String pgName, String impId, String merchantId, BigDecimal paymentPrice,
                         BigDecimal point, Long buyerId, Long auctionitemId, Long addressId, LocalDateTime endDate) {
        super(pgName, impId, merchantId, paymentPrice, point, buyerId, auctionitemId, addressId);
        this.endDate = endDate;
    }
}
