package com.dragonappear.inha.domain.payment;


import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("instant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class InstantPayment extends Payment{

    @Builder
    public InstantPayment(String pgName, String impId, String merchantId, Money paymentPrice, Money point, User user, Auctionitem auctionitem, Long addressId) {
        super(pgName, impId, merchantId, paymentPrice, point, user, auctionitem, addressId);
    }
}
