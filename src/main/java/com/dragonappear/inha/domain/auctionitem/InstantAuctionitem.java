package com.dragonappear.inha.domain.auctionitem;

import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("INSTANT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class InstantAuctionitem extends Auctionitem{

    public InstantAuctionitem(Item item, Long price, AuctionitemStatus auctionitemStatus) {
        super(item, price, auctionitemStatus);
    }
}