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
public class InstantPaymentDto extends PaymentDto{
    private Long sellingId;

    @Builder
    public InstantPaymentDto(String pgName, String impId, String merchantId, BigDecimal paymentPrice, BigDecimal point, Long buyerId, Long addressId, Long sellingId) {
        super(pgName, impId, merchantId, paymentPrice, point, buyerId, addressId);
        this.sellingId = sellingId;
    }

    public Payment toEntity(User user, Auctionitem auctionitem, Money point) {
        return new Payment(this.getPgName(), this.getImpId(), this.getMerchantId()
                , new Money(this.getPaymentPrice()), point, user, auctionitem, this.getAddressId());
    }
}