package com.dragonappear.inha.api.controller.buying.dto;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
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
    private Long auctionitemId;
    private Long addressId;

    @Builder
    public PaymentDto(String pgName, String impId, String merchantId, BigDecimal paymentPrice, BigDecimal point, Long buyerId, Long auctionitemId, Long addressId) {
        this.pgName = pgName;
        this.impId = impId;
        this.merchantId = merchantId;
        this.paymentPrice = paymentPrice;
        this.point = point;
        this.buyerId = buyerId;
        this.auctionitemId = auctionitemId;
        this.addressId = addressId;
    }

    public Payment toEntity(User user, Auctionitem auctionitem, Money point) {
        return Payment.builder()
                .paymentPrice(new Money(this.getPaymentPrice()))
                .user(user)
                .auctionitem(auctionitem)
                .impId(this.getImpId())
                .merchantId(this.merchantId)
                .point(point)
                .pgName(this.pgName)
                .addressId(this.addressId)
                .build();
    }
}