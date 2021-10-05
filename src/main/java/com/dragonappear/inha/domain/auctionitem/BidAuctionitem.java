package com.dragonappear.inha.domain.auctionitem;

import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@DiscriminatorValue("BID")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BidAuctionitem extends Auctionitem{
    @Column(nullable = false,updatable = false)
    private LocalDateTime startDate;
    @Column(nullable = false,updatable = false)
    private LocalDateTime endDate;

    public BidAuctionitem(Item item, Long price, AuctionitemStatus auctionitemStatus, LocalDateTime startDate, LocalDateTime endDate) {
        super(item, price, auctionitemStatus);
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
