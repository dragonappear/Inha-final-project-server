package com.dragonappear.inha.api.controller.buying.dto;

import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.payment.Payment;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BidPaymentApiDto extends PaymentApiDto {
    @NotNull
    private Long itemId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    public Payment toEntity(User user, Item item, Money money) {
        return Payment.builder()
                .paymentPrice(new Money(this.getPaymentPrice()))
                .user(user)
                .impId(this.getImpId())
                .merchantId(this.getMerchantId())
                .point(new Money(this.getPoint()))
                .pgName(this.getPgName())
                .addressId(this.getAddressId())
                .item(item)
                .build();
    }
}
