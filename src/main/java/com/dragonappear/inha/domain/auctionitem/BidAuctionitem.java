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

@DiscriminatorValue("BID")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BidAuctionitem extends Auctionitem{

    /**
     * 생성자 함수
     */
    public BidAuctionitem(Item item, Money price,LocalDateTime endDate) {
        super(item, price);
        this.startDate = now();
        if(endDate.isBefore(now())){
            throw new IllegalStateException("마감시간 입력이 잘못되었습니다");
        }else{
            this.endDate = endDate;
        }
    }

    /**
     * 비즈니스로직
     */
}
