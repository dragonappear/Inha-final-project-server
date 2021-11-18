package com.dragonappear.inha.api.controller.buying.dto;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InstantPaymentApiDto extends PaymentApiDto {
    @NotNull
    private Long sellingId;

    public Payment toEntity(User user, Auctionitem auctionitem, Money point) {
        return new Payment(this.getPgName(), this.getImpId(), this.getMerchantId()
                , new Money(this.getPaymentPrice()), point, user, auctionitem, this.getAddressId());
    }
}