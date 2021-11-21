package com.dragonappear.inha.domain.selling;

import com.dragonappear.inha.domain.auctionitem.Auctionitem;
import com.dragonappear.inha.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@DiscriminatorValue(value = "bid")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BidSelling extends Selling{
    @Column(updatable = false)
    private LocalDateTime endDate;

    @Builder
    public BidSelling(User seller, Auctionitem auctionitem, LocalDateTime endDate) {
        super(seller, auctionitem);
        this.endDate = endDate;
    }
}
