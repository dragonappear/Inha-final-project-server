package com.dragonappear.inha.domain.auctionitem;

import com.dragonappear.inha.domain.auctionitem.value.AuctionitemStatus;
import com.dragonappear.inha.domain.item.Item;
import com.dragonappear.inha.domain.value.Money;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;

@DiscriminatorValue("INSTANT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class InstantAuctionitem extends Auctionitem{
    @Column(nullable = false,updatable = false)
    private LocalDateTime startDate;
    @Column(nullable = false,updatable = false)
    private LocalDateTime endDate;


    /**
     * 생성자 함수
     */

    public InstantAuctionitem(Item item, Money price) {
        super(item, price);
        updateStartDateAndEndDate();
    }

    /**
     * 비즈니스 함수
     */
    private void updateStartDateAndEndDate() {
        this.startDate = now();
        this.endDate = now().plusDays(30);
    }
}
