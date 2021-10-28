package com.dragonappear.inha.domain.payment;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.user.User;
import com.dragonappear.inha.domain.value.Money;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@DiscriminatorValue("bid")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BidPayment extends Payment{
    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public BidPayment(String pgName, String impId, String merchantId, Money paymentPrice, Money point, User user,Long addressId, Item item) {
        super(pgName, impId, merchantId, paymentPrice, point, user, null, addressId);
        this.item = item;
    }

    private void updateItem(Item item) {
        this.item = item;
        item.getBidPayments().add(this);
    }
}
